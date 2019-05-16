package dk.kea.dat18i.teamsix.biotrio.controllers;

import dk.kea.dat18i.teamsix.biotrio.models.Booking;
import dk.kea.dat18i.teamsix.biotrio.models.MoviePlan;
import dk.kea.dat18i.teamsix.biotrio.models.SeatList;
import dk.kea.dat18i.teamsix.biotrio.models.Ticket;
import dk.kea.dat18i.teamsix.biotrio.repositories.BookingRepository;
import dk.kea.dat18i.teamsix.biotrio.repositories.MoviePlanRepository;
import dk.kea.dat18i.teamsix.biotrio.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

        int rows = moviePlan.getTheaterRoom().getRows_no();
        int cols = moviePlan.getTheaterRoom().getColumns_no();

        boolean[][] reservedSeats = new boolean[rows][cols];

        List<Ticket> reservedSeatsList = ticketRepo.findSeatsByBooking(movie_plan_id);

        Booking.findBookedSeats(reservedSeats, rows,cols, reservedSeatsList);

        SeatList selectSeatsList = new SeatList();
        
        model.addAttribute(moviePlan);
        model.addAttribute("reservedSeats", reservedSeats);
        model.addAttribute("seats", selectSeatsList);
        return "/seats";
    }

    @PostMapping("/booking-confirmation")
    public String saveMoviePlan(@ModelAttribute("seats") SeatList seatList)
    {
        System.out.println(seatList);
        return "redirect:/index";
    }

}
