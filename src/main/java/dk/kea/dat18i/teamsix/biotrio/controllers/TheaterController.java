package dk.kea.dat18i.teamsix.biotrio.controllers;

import dk.kea.dat18i.teamsix.biotrio.models.TeatherRoom;
import dk.kea.dat18i.teamsix.biotrio.repositories.TeatherRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TeatherController {

    @Autowired
    private TeatherRoomRepository teatherRepo;

    @GetMapping("/teather-room")
    public String showAllTeatherRoom(Model model) throws Exception {
        List<TeatherRoom> teatherRoomList = teatherRepo.findAllTeatherRoom();
        model.addAttribute("teatherRoom", teatherRoomList);
        return "/teather-room";
    }


    @GetMapping("/delete-teather-room/{id}")
    public String deleteTeatherRoom(@PathVariable("id") int id) throws Exception {
        teatherRepo.deleteTeatherRoom(id);
        return "redirect:/index";
    }

}
