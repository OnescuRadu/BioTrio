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

    /**
     * Method retrieves all the movies from the database and sends them to the view through the model
     *
     * @param model represents the bridge between the controller and the view
     * @return the 'index' view
     */

    @GetMapping({"/", "/index"})
    public String showHome(Model model) {
        List<Movie> movieList = movieRepo.findAllMovies();
        model.addAttribute("movies", movieList);
        return "index";
    }

    /**
     * Method retrieves all the movies from the database and sends them to the view through the model
     *
     * @param model represents the bridge between the controller and the view
     * @return the 'movies' view
     */

    @GetMapping("/movies")
    public String showAllMovie(Model model) {
        List<Movie> movieList = movieRepo.findAllMovies();
        model.addAttribute("movies", movieList);
        String genre = "All";
        model.addAttribute("moviesGenre", genre);
        return "/movies";
    }

    /**
     * Method retrieves the movie with the given id from the database and sends it to the view through the model
     *
     * @param id represents the given id
     * @param model represents the bridge between the controller and the view
     * @return the 'movie' view
     */

    @GetMapping("/movie/{id}")
    public String showMovie(@PathVariable("id") int id, Model model) {
        Movie movie = movieRepo.findMovie(id);
        model.addAttribute("movie", movie);
        List<MoviePlan> moviePlanList = moviePlanRepo.findMoviePlanByMovieId(id);
        model.addAttribute("moviePlans", moviePlanList);
        return "/movie";
    }

    /**
     * Method retrieves the genre from the link and initializes a list Movie objects that have the given genre
     * populates it from the database and then sends the created Movie object list to the view using a model
     *
     * @param genre represents the movie's genre
     * @param model            represents the bridge between the controller and the view
     * @return the 'movies' view
     */

    @GetMapping("/movies/{genre}")
    public String showMovieByGender(@PathVariable("genre") String genre, Model model) {
        List<Movie> movieList = movieRepo.findMovieByGender(genre);
        model.addAttribute("movies", movieList);
        String genreText = "'" + genre.substring(0, 1).toUpperCase() + genre.substring(1) + "'";
        model.addAttribute("moviesGenre", genreText);
        return "movies";
    }

    /**
     * Method shows the view
     *
     * @param model represents the bridge between the controller and the view
     * @return the 'add-movie-page' view
     */

    @GetMapping("/add-movie-page")
    public String addMovie(Model model) {
        model.addAttribute("movieForm", new Movie());
        return "add-movie-page";
    }

    /**
     * Method shows the view
     *
     * @param model represents the bridge between the controller and the view
     * @return the 'add-movie-page2' view
     */

    @GetMapping("/add-movie-page-using-details")
    public String addMovieUsingDetails(Model model) {
        model.addAttribute("movie", new Movie());

        List<MovieDetails> movieDetailsList = movieDetailsRepo.findAllMovieDetails();
        model.addAttribute("movieDetailsList", movieDetailsList);
        return "add-movie-page2";
    }

    /**
     * Method creates the movie using the provided information and stores it in the database
     *
     * @param movie represents the movie populated by the view
     * @param releaseDate   represents the movie's release date which is converted to LocalDate type
     * @return redirects to the '/see-all-movies' mapping
     */

    @PostMapping("/save-movie")
    public String saveMovie(@ModelAttribute Movie movie, @RequestParam("release-date") String releaseDate) {
        movie.getMovieDetails().setRelease_date(LocalDate.parse(releaseDate));

        movieRepo.saveMovie(movie);
        return "redirect:/see-all-movies";
    }

    /**
     * Method creates the movie using the existing movie details and stores it in the database
     *
     * @param movie represents the movie populated by the view
     * @return redirects to the '/see-all-movies' mapping
     */

    @PostMapping("/save-movie-using-details")
    public String saveMovieUsingExistingDetails(@ModelAttribute Movie movie) {
        movieRepo.insertMovieUsingDetails(movie);
        return "redirect:/see-all-movies";
    }

    /**
     * Method retrieves all the movies from the database and sends them to the view through the model
     *
     * @param model represents the bridge between the controller and the view
     * @return the 'see-movies' view
     */

    @GetMapping("/see-all-movies")
    public String showAllMovieCP(Model model) {
        List<Movie> movies = movieRepo.findAllMovies();
        model.addAttribute("movie", movies);
        return "see-movies";
    }

    /**
     * Method retrieves the id from the link sent by the view and deletes the movie that has the given id.
     *
     * @param id represents the id of the movie that was sent by the view through the link
     * @return redirects to the '/see-all-movies' mapping
     */

    @GetMapping("/delete-movie/{id}")
    public String deleteMovie(@PathVariable("id") int id) {
        movieRepo.deleteMovie(id);
        return "redirect:/see-all-movies";
    }

    /**
     * Method retrieves the id from the link and initializes a Movie object that has the given id and populates it from the database
     * and then sends this object to the view through the model
     *
     * @param id    represents the movie's id
     * @param model represents the bridge between the controller and the view
     * @return the 'edit-movie-page' view
     */

    @GetMapping("/edit-movie/{id}")
    public String editMovie(Model model, @PathVariable("id") int id) {
        Movie movieToEdit = movieRepo.findMovie(id);
        model.addAttribute("movieForm", movieToEdit);
        return "/edit-movie-page";
    }

    /**
     * Method retrieves the Movie object from the model and updates it's information in the database
     *
     * @param movie represents the Movie object
     * @param releaseDate represents the movie's release date which is converted to LocalDate type
     * @return redirects to the '/see-all-movies' mapping
     */

    @PostMapping("update-movie")
    public String saveEditMovie(@ModelAttribute Movie movie, @RequestParam("release-date") String releaseDate){
        movie.getMovieDetails().setRelease_date(LocalDate.parse(releaseDate));

        movieRepo.updateMovie(movie);
        return "redirect:/see-all-movies";
    }

}
