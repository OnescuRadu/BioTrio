package dk.kea.dat18i.teamsix.biotrio.controllers;

import dk.kea.dat18i.teamsix.biotrio.models.MoviePlan;
import dk.kea.dat18i.teamsix.biotrio.repositories.MoviePlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class MoviePlanController {

    @Autowired
    private MoviePlanRepository moviePlanRepo;

    @GetMapping("/movie-plans")
    public String showMoviePlan(Model model) {
        List<MoviePlan> moviePlanList = moviePlanRepo.findAllMoviePlan();
        model.addAttribute("moviePlan", moviePlanList);
        return "/movie-plan";
    }

    @GetMapping("/delete-movie-plan/{id}")
    public String deleteMoviePlan(@PathVariable("id") int id) {
        moviePlanRepo.deleteMoviePlan(id);
        return "/movie";
    }

    @GetMapping("/add-movie-plan")
    public String addMoviePlan(Model model) {
        List<MoviePlan> moviePlanList = moviePlanRepo.findAllMoviePlan();
        model.addAttribute("moviePlan", moviePlanList);
        return "/plan-movie-page";
    }

    @GetMapping("/edit-movie-plan/{id}")
    public String editMoviePlan(@PathVariable("id") int id, Model model){
        MoviePlan moviePlan = moviePlanRepo.findMoviePlan(id);
        model.addAttribute("moviePlanEdit", moviePlan);
        return "/edit-movie-plan";
    }

}
