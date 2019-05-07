package dk.kea.dat18i.teamsix.biotrio;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    @GetMapping("/")
    public String showHome1(){
        return "/index";
    }

    @GetMapping("/home")
    public String showHome2(){
        return "/index";
    }

    @GetMapping("/movie")
    public String showMovie(){
        return "/movie";
    }

    @GetMapping("/movies")
    public String showAllMovies(){
        return "/movies";
    }

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

    @GetMapping("/403")
    public String Error403(){
        return "403";
    }

    @GetMapping("/plan-movie-page")
    public String planMoviePage(){ return "plan-movie-page"; }

    @GetMapping("/add-movie-page")
    public String addMoviePage(){ return "add-movie-page"; }

    @GetMapping("/employee")
    public String employee(){ return "/employee"; }
}
