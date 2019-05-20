package dk.kea.dat18i.teamsix.biotrio.controllers;

import dk.kea.dat18i.teamsix.biotrio.models.Movie;
import dk.kea.dat18i.teamsix.biotrio.models.MovieDetails;
import dk.kea.dat18i.teamsix.biotrio.models.MoviePlan;
import dk.kea.dat18i.teamsix.biotrio.repositories.MovieDetailsRepository;
import dk.kea.dat18i.teamsix.biotrio.repositories.MoviePlanRepository;
import dk.kea.dat18i.teamsix.biotrio.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@Controller
public class MovieController {

    @Autowired
    private MovieRepository movieRepo;

    @Autowired
    private MoviePlanRepository moviePlanRepo;

    @Autowired
    private MovieDetailsRepository movieDetailsRepo;

    @GetMapping({"/", "/index"})
    public String showHome(Model model) {
        List<Movie> movieList = movieRepo.findAllMovies();
        model.addAttribute("movies", movieList);
        return "index";
    }

    @GetMapping("/movies")
    public String showAllMovie(Model model) {
        List<Movie> movieList = movieRepo.findAllMovies();
        model.addAttribute("movies", movieList);
        String genre = "All";
        model.addAttribute("moviesGenre", genre);
        return "/movies";
    }

    @GetMapping("/movie/{id}")
    public String showMovie(@PathVariable("id") int id, Model model) throws Exception {
        Movie movie = movieRepo.findMovie(id);
        model.addAttribute("movie", movie);
        List<MoviePlan> moviePlanList = moviePlanRepo.findMoviePlanByMovieId(id);
        model.addAttribute("moviePlans", moviePlanList);
        return "/movie";
    }

    @GetMapping("/movies/{genre}")
    public String showMovieByGender(@PathVariable("genre") String genre, Model model) throws Exception {
        List<Movie> movieList = movieRepo.findMovieByGender(genre);
        model.addAttribute("movies", movieList);
        String genreText = "'" + genre.substring(0, 1).toUpperCase() + genre.substring(1) + "'";
        model.addAttribute("moviesGenre", genreText);
        return "movies";
    }

    @GetMapping("/add-movie-page")
    public String addMovie(Model m) {
        m.addAttribute("movieForm", new Movie());
        return "add-movie-page";
    }

    @GetMapping("/add-movie-page-using-details")
    public String addMovieUsingDetails(Model m) {
        m.addAttribute("movie", new Movie());

        List<MovieDetails> movieDetailsList = movieDetailsRepo.findAllMovieDetails();
        m.addAttribute("movieDetailsList", movieDetailsList);
        return "add-movie-page2";
    }

    @PostMapping("/save-movie")
    public String saveMovie(@ModelAttribute Movie movie, @RequestParam("release-date") String releaseDate, @ModelAttribute("a") String type) {
        movie.getMovieDetails().setRelease_date(LocalDate.parse(releaseDate));
        if(type.isEmpty())
            movie.setType(false);
        else
            movie.setType(true);

        Movie movieInserted = movieRepo.saveMovie(movie);
        return "redirect:/see-all-movies";
    }

    @PostMapping("/save-movie-using-details")
    public String saveMovieUsingExistingDetails(@ModelAttribute Movie movie, @ModelAttribute("a") String type) {
        if(type.isEmpty())
            movie.setType(false);
        else
            movie.setType(true);

        movieRepo.insertMovieUsingDetails(movie);
        return "redirect:/see-all-movies";
    }

    @GetMapping("/see-all-movies")
    public String showAllMovieCP(Model m) {
        List<Movie> movies = movieRepo.findAllMovies();
        m.addAttribute("movie", movies);
        return "see-movies";
    }

    @GetMapping("/delete-movies")
    public String deleteMoviesCP(Model m) {
        List<Movie> movies = movieRepo.findAllMovies();
        m.addAttribute("movie", movies);
        return "delete-movie";
    }

    @GetMapping("/delete-movie/{id}")
    public String deleteMovie(@PathVariable("id") int id) {
        movieRepo.deleteMovie(id);
        return "redirect:/see-all-movies";
    }

    @GetMapping("/edit-all-movies")
    public String editAllMovies(Model m) {
        List<Movie> movies = movieRepo.findAllMovies();
        m.addAttribute("movie", movies);
        return "edit-all-movies";
    }

    @GetMapping("/edit-movie/{id}")
    public String editMovie(Model m, @PathVariable("id") int id) {
        Movie movieToEdit = movieRepo.findMovie(id);
        m.addAttribute("movieForm", movieToEdit);
        return "/edit-movie-page";
    }

    @PostMapping("update-movie")
    public String saveEditMovie(@ModelAttribute Movie movie, @RequestParam("release-date") String releaseDate, @ModelAttribute("a") String type){
        movie.getMovieDetails().setRelease_date(LocalDate.parse(releaseDate));
        if(type.isEmpty())
            movie.setType(false);
        else
            movie.setType(true);
        movieRepo.updateMovie(movie);
        return "redirect:/see-all-movies";
    }

}
