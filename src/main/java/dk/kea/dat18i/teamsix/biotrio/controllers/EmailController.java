package dk.kea.dat18i.teamsix.biotrio.controllers;


import javax.mail.internet.MimeMessage;

import dk.kea.dat18i.teamsix.biotrio.models.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Controller
public class EmailController {

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    //retrieves the data from the contact form and calls sendContactFormEmail() method using the right parameters
    @PostMapping("/send-contact-email")
    public String sendContactEmail(@RequestParam("name") String name, @RequestParam("email") String email,@RequestParam("subject") String subject,@RequestParam("message") String message, Model m) {
        try {
            sendContactFormEmail(name,email,subject,message);
            return "redirect:/index";
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute("exception", e);
            return "/error";
        }
    }

    private void sendContactFormEmail(String name, String email, String subject, String message) {

        //get and fill the template
        final Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("email", email);
        context.setVariable("subject", subject);
        context.setVariable("message", message);

        //set variables for the emails
        String body = templateEngine.process("mails/contact-email", context);
        String sendTo = "biotriocinema@gmail.com";
        String emailSubject = "New message from BioTrio Customer";

        //send the html template
        sendPreparedMail(body, sendTo, emailSubject);
    }

    public void sendBookingConfirmation(Booking booking) {

        //get and fill the template
        final Context context = new Context();
        context.setVariable("booking", booking);


        //set variables for the emails
        String body = templateEngine.process("mails/booking-email", context);
        String emailSubject = "BioTrio Cinema | Booking Confirmation";
        String sendTo = booking.getEmail();

        //send the html template
        sendPreparedMail(body, sendTo, emailSubject);
    }

    private void sendPreparedMail(String body, String sendTo, String subject) {
        try {
            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(sendTo);
            helper.setSubject(subject);
            helper.setText(body, true);
            javaMailSender.send(mail);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}