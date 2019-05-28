package dk.kea.dat18i.teamsix.biotrio.controllers;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import dk.kea.dat18i.teamsix.biotrio.models.Booking;
import org.springframework.stereotype.Controller;

@Controller
public class SMSController {

    public void sendBookingSMS(Booking booking)
    {
        Message.creator(
                new PhoneNumber("+45" + booking.getPhone_number()),
                new PhoneNumber("+12024103476"),
                "BioTrio - Cinema\n" +
                        "Your booking has been successfully made: \n" +
                        "Email: " + booking.getEmail() + "\n" +
                        "Phone no.: " + booking.getPhone_number() + "\n" +
                        "Confirmation Code: " + booking.getConfirmation_code()
        ).create();
    }
}
