package dk.kea.dat18i.teamsix.biotrio.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class WebController {

    @GetMapping("/about-us")
    public String showAboutUs(){
        return "/about";
    }

    @GetMapping("/contact")
    public String showContact(){
        return "/contact";
    }

    @GetMapping("/faq")
    public String showFaq(){
        return "/faq";
    }

    @GetMapping("/control-panel")
    public String showControlPanel(){
        return "/control-panel";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/user")
    public String user(){
        return "user";
    }

    @GetMapping("/admin")
    public String admin(){
        return "admin";
    }

    @GetMapping("/employee")
    public String employee(){
        return "employee";
    }

    @GetMapping("/403")
    public String Error403(){
        return "403";
    }
}
