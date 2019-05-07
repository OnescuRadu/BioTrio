package dk.kea.dat18i.teamsix.biotrio.controllers;

import dk.kea.dat18i.teamsix.biotrio.models.TheaterRoom;
import dk.kea.dat18i.teamsix.biotrio.repositories.TheaterRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TheaterController {

    @Autowired
    private TheaterRoomRepository theaterRepo;

    @GetMapping("/theater-room")
    public String showAllTheaterRoom(Model model) throws Exception {
        List<TheaterRoom> theaterRoomList = theaterRepo.findAllTheaterRoom();
        model.addAttribute("theaterRoom", theaterRoomList);
        return "/theater-room";
    }


    @GetMapping("/delete-theater-room/{id}")
    public String deleteTheaterRoom(@PathVariable("id") int id) throws Exception {
        theaterRepo.deleteTheaterRoom(id);
        return "redirect:/index";
    }

}
