package dk.kea.dat18i.teamsix.biotrio.controllers;

import dk.kea.dat18i.teamsix.biotrio.models.Movie;
import dk.kea.dat18i.teamsix.biotrio.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class MovieController {

    @Autowired
    private MovieRepository movieRepo;

    @GetMapping({"/","/index"})
    public String showHome(Model model) throws Exception {
        List<Movie> movieList = movieRepo.findAllMovies();
        model.addAttribute("movies", movieList);
        return "/index";
    }

    @GetMapping("/movies")
    public String showAllMovie(){
        return "/movies";
    }

    @GetMapping("/movie/{id}")
    public String showMovie(@PathVariable("id") int id, Model model) throws Exception {
        Movie movie = movieRepo.findMovie(id);
        model.addAttribute("movie", movie);
        return "/movie";
    }

    @GetMapping("/movies/{genre}")
    public String showMovieByGender(@PathVariable("genre") String genre, Model model) throws Exception {
        List<Movie> movieList = movieRepo.findMovieByGender(genre);
        model.addAttribute("movies", movieList);
        return "/movies";
    }


}
