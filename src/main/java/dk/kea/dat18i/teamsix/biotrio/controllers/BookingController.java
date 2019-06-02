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

    /**
     * Method retrieves all the bookings from the database and sends them to the view through the model
     *
     * @param model represents the bridge between the controller and the view
     * @return the 'bookings' view
     */
    @GetMapping("/bookings")
    public String showAllBooking(Model model) {
        List<Booking> bookingList = bookingRepo.findAllBooking();
        model.addAttribute("bookings", bookingList);
        return "/bookings/bookings";
    }

    /**
     * Method gets the selected movie plan id and initializes a MoviePlan object using the information from the database with the given id,
     * creates a two dimensional array of boolean type that stores TRUE on the positions that correspond to the already booked seats
     * and sends this information to the view using the model.
     * <p>
     * Eg: If there is a booked seat with the number "05-04" then array[5][4] will be TRUE
     *
     * @param request HttpServletRequest that is used for retrieving data from the view
     * @param model   represents the bridge between the controller and the view
     * @return the 'select-seats' view
     */
    @PostMapping("/select-seats")
    public String selectSeats(HttpServletRequest request, Model model) {
        int movie_plan_id = Integer.parseInt(request.getParameter("movie_plan_id")); //get's the movie plan's id from the view
        MoviePlan moviePlan = movieRepo.findMoviePlan(movie_plan_id); //initializes a MoviePlan object based on the movie plan id

        int rows = moviePlan.getTheaterRoom().getRows_no(); //theater room's number of rows
        int cols = moviePlan.getTheaterRoom().getColumns_no(); //theater room's number of columns

        boolean[][] reservedSeats = new boolean[rows][cols]; //boolean 2D array with the same number of rows and cols as the theater room

        List<Ticket> reservedSeatsList = ticketRepo.findSeatsByBooking(movie_plan_id); //List of ticket objects that are already booked for the given movie plan

        Booking.findBookedSeats(reservedSeats, rows, cols, reservedSeatsList); //populating the boolean 2D array based on the list of booked tickets


        Booking booking = new Booking(); //instantiates a Booking object

        booking.setMoviePlan(moviePlan); //set the MoviePlan object inside the Booking object

        model.addAttribute("newBooking", booking); //Adding the booking object in a model
        model.addAttribute("reservedSeats", reservedSeats); //Adding the populated 2D boolean array in a model
        return "/bookings/select-seats";
    }

    /**
     * Method creates the booking using the provided information and stores it in the database
     *
     * @param booking represents the Booking populated by the view
     * @param model   represents the bridge between the controller and the view
     * @return the 'booking-confirmation' view or the 'error' view if there was a validation problem with the data
     */
    @PostMapping("/create-booking")
    public String saveBooking(@ModelAttribute("newBooking") Booking booking, Model model) {

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
            return "/errors/error";
        }

        //Checking if the selected seats are already booked
        if (!ticketRepo.checkIfSeatsAreAvailable(booking.getTicketList(), booking.getMovie_plan_id())) {
            model.addAttribute("error", "Seats are already booked. Please try again!");
            return "/errors/error";
        }


        booking = bookingRepo.insertBooking(booking); //insert booking and return the object with updated id


        //inserting tickets into the database
        for (Ticket ticket : ticketList) {

            ticket.setBooking_id(booking.getBooking_id());
            ticketRepo.insertTicket(ticket);

        }

        //Sending email
        email.sendBookingConfirmation(booking);

        //Sending sms
        //Method costs money per sms
        sms.sendBookingSMS(booking);

        //Adding the booking object into a model and returning the booking-confirmation template
        model.addAttribute("booking", booking);
        return "/bookings/booking-confirmation";
    }

    /**
     * Method retrieves the id from the link sent by the view and deletes the booking that has the given id.
     *
     * @param id represents the id of the booking that was sent by the view through the link
     * @return redirects to the '/bookings' mapping
     */
    @GetMapping("/delete-booking/{id}")
    public String deleteBooking(@PathVariable("id") int id) {
        bookingRepo.deleteBooking(id);
        return "redirect:/bookings";
    }

    /**
     * Method shows the view
     *
     * @return the 'find-booking-by-customer' view
     */
    @GetMapping("/find-booking-by-customer")
    public String findBookingByCustomer() {
        return "/bookings/find-booking-by-customer";
    }

    /**
     * Method shows the view
     *
     * @return the '/find-booking' view
     */
    @GetMapping("/find-booking")
    public String findBooking() {
        return "/bookings/find-booking";
    }

    /**
     * Method retrieves the phone number and the confirmation code from the link and deletes from the database the booking that has the given phone number and confirmation code
     *
     * @param phoneNumber      represents the phone number
     * @param confirmationCode represents the confirmation code
     * @return redirects to the '/find-booking-by-customer' mapping
     */
    @GetMapping("/delete-booking-by-customer/{phoneNumber}/{confirmationCode}")
    public String deleteBookingByCustomer(@PathVariable("phoneNumber") String phoneNumber, @PathVariable("confirmationCode") String confirmationCode) {

        bookingRepo.deleteBookingByCustomer(phoneNumber, confirmationCode);
        return "redirect:/find-booking-by-customer";
    }

    /**
     * Method retrieves the phone number and the confirmation code from the link and initializes a Booking object that has the given confirmation code and email/phone number,
     * populates it from the database and then sends the created Booking object to the view using a model
     *
     * @param confirmationCode represents the booking's confirmation code
     * @param emailPhone       represents the booking's email or phone number
     * @param model            represents the bridge between the controller and the view
     * @return the 'view-booking' view or the 'error' view if there was no booking found
     */
    @PostMapping("/view-booking-by-customer")
    public String viewBookingByCustomer(@RequestParam("confirmation-code") String confirmationCode, @RequestParam("email-phone") String emailPhone, Model model) {

        Booking booking = bookingRepo.findBookingByCustomer(emailPhone, confirmationCode);

        //If booking object is null (there is no record found in the database) it redirects to the error page with the provided message
        if (booking == null) {
            model.addAttribute("error", "There was no booking found with the provided information. Please try again!");
            return "/errors/error";
        } else {
            model.addAttribute("booking", booking);
            return "/bookings/view-booking";
        }
    }

    /**
     * Method retrieves the confirmation code from the link and initializes a Booking object that has the given confirmation code and email/phone number,
     * populates it from the database and then sends the created Booking object to the view using a model
     *
     * @param confirmationCode represents the booking's confirmation code
     * @param model            represents the bridge between the controller and the view
     * @return the '/view-booking-cp' view
     */
    @PostMapping("/view-booking")
    public String viewBooking(@RequestParam("confirmation-code") String confirmationCode, Model model) {

        Booking booking = bookingRepo.findBookingByConfirmationCode(confirmationCode);

        //If booking object is null (there is no record found in the database) it redirects to the error page with the provided message
        if (booking == null) {
            model.addAttribute("error", "There was no booking found with the provided information. Please try again!");
            return "/errors/error";
        } else {
            model.addAttribute("booking", booking);
            return "/bookings/view-booking-cp";
        }
    }


    /**
     * Method retrieves the id from the link and initializes a Booking object that has the given id and populates it from the database
     * and then sends this object to the view through the model
     *
     * @param id    represents the booking's id
     * @param model represents the bridge between the controller and the view
     * @return the 'edit-booking' view
     */
    @GetMapping("/edit-booking/{id}")
    public String editBooking(@PathVariable("id") int id, Model model) {
        Booking booking = bookingRepo.findBooking(id);
        model.addAttribute("editedBooking", booking);
        return "/bookings/edit-booking";
    }

    /**
     * Method retrieves the Booking object from the model and updates it's information in the database
     *
     * @param booking represents the Booking object
     * @return redirects to the '/bookings' mapping
     */
    @PostMapping("/edit-booking/save")
    public String saveEditedBooking(@ModelAttribute Booking booking) {

        bookingRepo.editBooking(booking);
        return "redirect:/bookings";
    }

}
