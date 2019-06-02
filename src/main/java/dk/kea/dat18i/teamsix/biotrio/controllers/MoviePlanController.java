package dk.kea.dat18i.teamsix.biotrio.controllers;

import dk.kea.dat18i.teamsix.biotrio.models.Movie;
import dk.kea.dat18i.teamsix.biotrio.models.MoviePlan;
import dk.kea.dat18i.teamsix.biotrio.models.TheaterRoom;
import dk.kea.dat18i.teamsix.biotrio.repositories.MoviePlanRepository;
import dk.kea.dat18i.teamsix.biotrio.repositories.MovieRepository;
import dk.kea.dat18i.teamsix.biotrio.repositories.TheaterRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MoviePlanController {

    @Autowired
    private MoviePlanRepository moviePlanRepo;
    @Autowired
    private MovieRepository movieRepo;
    @Autowired
    private TheaterRoomRepository theaterRoomRepo;

    @GetMapping("/movie-plans")
    public String showMoviePlan(Model model) {
        List<MoviePlan> moviePlanList = moviePlanRepo.findAllMoviePlan();
        model.addAttribute("moviePlans", moviePlanList);
        return "/movie-plans/movie-plan";
    }

    @GetMapping("/delete-movie-plan/{id}")
    public String deleteMoviePlan(@PathVariable("id") int id) {
        moviePlanRepo.deleteMoviePlan(id);
        return "redirect:/movie-plans";
    }

    @GetMapping("/add-movie-plan")
    public String addMoviePlan(Model m)
    {
        m.addAttribute("moviePlan", new MoviePlan());
        List<Movie> movieList = movieRepo.findAllMovies();
        m.addAttribute("movies", movieList);
        List<TheaterRoom> theaterRoomList = theaterRoomRepo.findAllTheaterRoom();
        m.addAttribute("theaterRooms", theaterRoomList);
        return "/movie-plans/add-movie-plan";
    }

    @PostMapping("/add-movie-plan/save")
    public String saveMoviePlan(@ModelAttribute MoviePlan moviePlan, Model model)
    {
        Movie movie = movieRepo.findMovie(moviePlan.getMovie_id()); //initializes the movie object that is going to be planned
        moviePlan.setMovie(movie); //sets the Movie object inside the MoviePlan

        if(moviePlanRepo.checkIfMoviePlanIsAvailable(moviePlan)) //checks if the movie plan is overlapping an existing one
        {
            model.addAttribute("error", "There is already a movie scheduled for that time.");
            return "/errors/error";
        }
        moviePlanRepo.insertMoviePlan(moviePlan); //inserts the movie plan in the database if it does not already exists
        return "redirect:/movie-plans";
    }

    @GetMapping("/edit-movie-plan/{id}")
    public String editMoviePlan(@PathVariable("id") int id, Model model)
    {
        MoviePlan moviePlan = moviePlanRepo.findMoviePlan(id);
        model.addAttribute("moviePlan", moviePlan);
        List<Movie> movieList = movieRepo.findAllMovies();
        model.addAttribute("movies", movieList);
        List<TheaterRoom> theaterRoomList = theaterRoomRepo.findAllTheaterRoom();
        model.addAttribute("theaterRooms", theaterRoomList);
        return "/movie-plans/edit-movie-plan";
    }

    @PostMapping("/edit-movie-plan/save")
    public String editMoviePlanPost(@ModelAttribute MoviePlan moviePlan)
    {
        moviePlanRepo.editMoviePlan(moviePlan);
        return "redirect:/movie-plans";
    }

}
