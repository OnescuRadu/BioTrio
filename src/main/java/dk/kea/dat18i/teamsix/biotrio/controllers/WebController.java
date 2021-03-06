package dk.kea.dat18i.teamsix.biotrio.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class WebController {

    @GetMapping("/about-us")
    public String showAboutUs() {
        return "/about";
    }

    @GetMapping("/contact")
    public String showContact() {
        return "/contact";
    }

    @GetMapping("/control-panel")
    public String showControlPanel() {
        return "/control-panel";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/403")
    public String error403() {
        return "/errors/403";
    }

    @GetMapping("/404")
    public String error404() {
        return "/errors/404";
    }

}
