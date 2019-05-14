package dk.kea.dat18i.teamsix.biotrio.controllers;

import dk.kea.dat18i.teamsix.biotrio.models.Booking;
import dk.kea.dat18i.teamsix.biotrio.models.MoviePlan;
import dk.kea.dat18i.teamsix.biotrio.models.Ticket;
import dk.kea.dat18i.teamsix.biotrio.repositories.BookingRepository;
import dk.kea.dat18i.teamsix.biotrio.repositories.MoviePlanRepository;
import dk.kea.dat18i.teamsix.biotrio.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BookingController {

    @Autowired
    private BookingRepository bookingRepo;

    @Autowired
    private MoviePlanRepository movieRepo;

    @Autowired
    private TicketRepository ticketRepo;

    @GetMapping("/bookings")
    public String showAllBooking(Model model) {
        List<Booking> bookingList = bookingRepo.findAllBooking();
        model.addAttribute("bookings", bookingList);
        return "/bookings";
    }

    @PostMapping("/select-seats")
    public String selectSeats(HttpServletRequest request, Model model)
    {
        int movie_plan_id = Integer.parseInt(request.getParameter("movie_plan_id"));
        MoviePlan moviePlan = movieRepo.findMoviePlan(movie_plan_id);
        model.addAttribute(moviePlan);
        int rows = moviePlan.getTheaterRoom().getRows_no();
        int cols = moviePlan.getTheaterRoom().getColumns_no();

        boolean[][] seats = new boolean[rows][cols];

        List<Ticket> reservedSeats = ticketRepo.findSeatsByBooking(movie_plan_id);

        Booking.findBookedSeats(seats, rows,cols, reservedSeats);

        Booking.seeBookedSeats(seats,rows,cols);
        model.addAttribute("seats", seats);
        return "/seats";
    }

    @PostMapping("/booking-confirmation")
    public String saveMoviePlan(@ModelAttribute boolean[][] seats)
    {

        return "redirect:/index";
    }

}
