package dk.kea.dat18i.teamsix.biotrio.controllers;

import dk.kea.dat18i.teamsix.biotrio.models.User;
import dk.kea.dat18i.teamsix.biotrio.repositories.UserRepository;
import dk.kea.dat18i.teamsix.biotrio.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/users")
    public String showAllUser(Model model) {
        List<User> userList = userRepo.findAllUser();
        model.addAttribute("users", userList);
        return "/users";
    }


    @GetMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userRepo.deleteUser(id);
        return "redirect:/users";
    }

    @GetMapping("/add-user")
    public String addUser(Model m)
    {
        m.addAttribute("newUser", new User());
        return "/add-user";
    }

    @PostMapping("/add-user/save")
    public String saveUser(@ModelAttribute User user)
    {
        userRepo.insertUser(user);
        return "redirect:/users";
    }


    @GetMapping("/edit-user/{id}")
    public String editUserPage(@PathVariable("id") int id, Model model)
    {
        User user = userRepo.findUser(id);
        model.addAttribute("editedUser", user);
        return "/edit-user";
    }

    @PostMapping("/edit-user/save")
    public String editUser(@ModelAttribute User user)
    {
        userRepo.editUser(user);
        return "redirect:/users";
    }

    @GetMapping("/edit-user-password/{id}")
    public String editUserPasswordPage(@PathVariable("id") int id, Model model)
    {
        User user = userRepo.findUser(id);
        model.addAttribute("editedUserPass", user);
        return "/edit-user-password";
    }

    @PostMapping("/edit-user-password/save")
    public String editUserPassword(@ModelAttribute User theaterRoom)
    {
        userRepo.editUserPassword(theaterRoom);
        return "redirect:/users";
    }
}
