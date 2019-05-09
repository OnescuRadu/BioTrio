package dk.kea.dat18i.teamsix.biotrio.controllers;

import dk.kea.dat18i.teamsix.biotrio.models.Movie;
import dk.kea.dat18i.teamsix.biotrio.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private MovieRepository movieRepo;

/*    @GetMapping("/search-movie:{search}")
    public String showAllTheaterRoom(@PathVariable("search") String search, Model model) throws Exception {
        List<Movie> movieList = movieRepo.findMovieByName(search);
        model.addAttribute("movies", movieList);
        model.addAttribute("searchtext", search);
        return "/search";
    }*/

    @RequestMapping("/search-movie-post")
        public String searchMovie(@RequestParam String searchString) throws UnsupportedEncodingException {
            String link = "redirect:/search-movie:" + searchString;
            return link;
        }
}
