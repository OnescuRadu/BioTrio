package dk.kea.dat18i.teamsix.biotrio.controllers;

import dk.kea.dat18i.teamsix.biotrio.models.*;
import dk.kea.dat18i.teamsix.biotrio.repositories.BookingRepository;
import dk.kea.dat18i.teamsix.biotrio.repositories.MoviePlanRepository;
import dk.kea.dat18i.teamsix.biotrio.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class BookingController {

    @Autowired
    private BookingRepository bookingRepo;

    @Autowired
    private MoviePlanRepository movieRepo;

    @Autowired
    private TicketRepository ticketRepo;

    @Autowired
    private EmailController email;

    @Autowired
    private SMSController sms;

    @GetMapping("/bookings")
    public String showAllBooking(Model model) {
        List<Booking> bookingList = bookingRepo.findAllBooking();
        model.addAttribute("bookings", bookingList);
        return "/bookings";
    }

    @PostMapping("/select-seats")
    public String selectSeats(HttpServletRequest request, Model model) {
        int movie_plan_id = Integer.parseInt(request.getParameter("movie_plan_id"));
        MoviePlan moviePlan = movieRepo.findMoviePlan(movie_plan_id);

        int rows = moviePlan.getTheaterRoom().getRows_no();
        int cols = moviePlan.getTheaterRoom().getColumns_no();

        boolean[][] reservedSeats = new boolean[rows][cols];

        List<Ticket> reservedSeatsList = ticketRepo.findSeatsByBooking(movie_plan_id);

        Booking.findBookedSeats(reservedSeats, rows, cols, reservedSeatsList);


        Booking booking = new Booking();

        booking.setMoviePlan(moviePlan);

        model.addAttribute("newBooking", booking);
        model.addAttribute("reservedSeats", reservedSeats);
        return "/select-seats";
    }

    @PostMapping("/create-booking")
    public String saveMoviePlan(@ModelAttribute("newBooking") Booking booking, Model model) {

        String[] seats = booking.getSeatList(); //instantiate array of strings and initialize it with the array of strings from the booking object
        Booking.formatSeatList(seats); //format the array of strings so the numbers will have two digits


        MoviePlan moviePlan = movieRepo.findMoviePlan(booking.getMovie_plan_id()); //instantiate and initialize moviePlan object

        booking.setMoviePlan(moviePlan); //sets the moviePlan object inside booking object
        booking.setSeatList(seats); //sets the checked seat list in the booking
        booking.setConfirmation_code(UUID.randomUUID().toString()); //sets a random generated alpha numeric string as confirmation code
        booking.setTotalPrice(booking.getMoviePlan().getPrice() * seats.length); //sets total price based on number of tickets and ticket price

        List<Ticket> ticketList = new ArrayList<>(); //instantiates an arraylist of Ticket object

        for (int i = 0; i < booking.getSeatList().length; i++)   //add every seat from seatList into the List of Ticket
        {
            Ticket ticket = new Ticket();
            ticket.setSeat_number(booking.getSeatList()[i]);
            ticketList.add(ticket);
        }

        booking.setTicketList(ticketList); //sets ticketList inside booking




        //Checking the number of selected seats
        if (seats.length > 4 || seats.length == 0) {
            model.addAttribute("error", "Invalid number of seats. Maximum number is 4. Please try again!");
            return "/error";
        }

        //Checking if the selected seats are already booked
        if(!ticketRepo.checkIfSeatsAreAvailable(booking.getTicketList(), booking.getMovie_plan_id())){
            model.addAttribute("error", "Seats are already booked. Please try again!");
            return "/error";
        }


        booking = bookingRepo.insertBooking(booking); //insert booking and return the object with updated id


        //inserting tickets into the database
        for (int i = 0; i < ticketList.size(); i++) {

            ticketList.get(i).setBooking_id(booking.getBooking_id());
            ticketRepo.insertTicket(ticketList.get(i));

        }

        //Sending email
        email.sendBookingConfirmation(booking);

        //Sending sms
        //Method works but dont use it because it costs money per sms
        //sms.sendBookingSMS(booking);

        //Adding the booking object into a model and returning the booking-confirmation template
        model.addAttribute("booking", booking);
        System.out.println(booking.getMoviePlan().getMovie().getMovieDetails().getPoster());
        return "/booking-confirmation";
    }

    @GetMapping("/delete-booking/{id}")
    public String deleteBooking(@PathVariable("id") int id) {
        bookingRepo.deleteBooking(id);
        return "redirect:/bookings";
    }

    @GetMapping("/find-booking")
    public String findBooking()
    {
        return "/find-booking";
    }

    @PostMapping("/view-booking")
    public String viewBooking(@RequestParam("confirmation-code") String confirmationCode, @RequestParam("email-phone") String emailPhone, Model model)
    {

        Booking booking = bookingRepo.findBookingByConfirmationCode(emailPhone, confirmationCode);

        //If booking object is null (there is no record found in the database) it redirects to the error page with the provided message
       if(booking == null) {
           model.addAttribute("error", "There was no booking found with the provided information. Please try again!");
           return "/error";
       }
        else
        {
            model.addAttribute("booking", booking);
            return "/view-booking";
        }
    }

    @GetMapping("/delete-booking-by-customer/{phoneNumber}/{confirmationCode}")
    public String deleteBooking(@PathVariable("phoneNumber") String phoneNumber, @PathVariable("confirmationCode") String confirmationCode) {

        bookingRepo.deleteBookingByConfirmationCode(phoneNumber, confirmationCode);
        return "redirect:/find-booking";
    }

}
