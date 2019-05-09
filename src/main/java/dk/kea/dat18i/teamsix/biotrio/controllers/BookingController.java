package dk.kea.dat18i.teamsix.biotrio.controllers;

import dk.kea.dat18i.teamsix.biotrio.models.Booking;
import dk.kea.dat18i.teamsix.biotrio.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class BookingController {

    @Autowired
    private BookingRepository bookingRepo;

    @GetMapping("/bookings")
    public String showAllBooking(Model model) {
        List<Booking> bookingList = bookingRepo.findAllBooking();
        model.addAttribute("bookings", bookingList);
        return "/bookings";
    }

    @GetMapping("/booking/{id}")
    public String showBooking(@PathVariable("id") int id, Model model) {
        Booking booking = bookingRepo.findBooking(id);
        model.addAttribute("booking", booking);
        return "/booking";
    }

    @GetMapping("/delete-booking/{id}")
    public String deleteBooking(@PathVariable("id") int id) {
        bookingRepo.deleteBooking(id);
        return "redirect:/booking";
    }

    @GetMapping("/add-booking")
    public String addBooking(Model m)
    {
        m.addAttribute("newBooking", new Booking());
        return "/add-booking";
    }

    @PostMapping("/add-booking/save")
    public String saveBooking(@ModelAttribute Booking booking)
    {
        bookingRepo.insertBooking(booking);
        return "redirect:/booking";
    }


    @GetMapping("/edit-booking/{id}")
    public String editCar(@PathVariable("id") int id, Model model)
    {
        Booking booking = bookingRepo.findBooking(id);
        model.addAttribute("editedBooking", booking);
        return "/edit-booking";
    }

    @PostMapping("/edit-booking/save")
    public String editCarInfo(@ModelAttribute Booking booking)
    {
        bookingRepo.editBooking(booking);
        return "redirect:/booking";
    }
}
