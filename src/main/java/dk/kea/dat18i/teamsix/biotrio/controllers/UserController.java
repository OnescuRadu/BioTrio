package dk.kea.dat18i.teamsix.biotrio.controllers;

import dk.kea.dat18i.teamsix.biotrio.models.TheaterRoom;
import dk.kea.dat18i.teamsix.biotrio.models.User;
import dk.kea.dat18i.teamsix.biotrio.repositories.TheaterRoomRepository;
import dk.kea.dat18i.teamsix.biotrio.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/users")
    public String showAllUsers(Model model) throws Exception {
        List<User> userList = userRepo.findAllUsers();
        model.addAttribute("users", userList);
        return "/users";
    }


    @GetMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable("id") int id) throws Exception {
        userRepo.deleteUser(id);
        return "redirect:/users";
    }
}
