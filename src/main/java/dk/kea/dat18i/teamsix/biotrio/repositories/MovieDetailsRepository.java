package dk.kea.dat18i.teamsix.biotrio.repositories;

import dk.kea.dat18i.teamsix.biotrio.models.MovieDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the repository for the movie details
 */
@Repository
public class MovieDetailsRepository {

    @Autowired
    private JdbcTemplate jdbc;

    /**
     * Method finds all the movie details in the database
     *
     * @return
     */
    public List<MovieDetails> findAllMovieDetails() {
        String query = "SELECT * from movie_details";

        List<MovieDetails> movieDetailsList = new ArrayList<>();
        SqlRowSet rs = jdbc.queryForRowSet(query);
        return getMovieDetailsList(movieDetailsList, rs);
    }

    /**
     * Method sets the variables inside the given MovieDetails object using the given SqlRowSet
     * This method is used for avoiding writing the same setters in every other method and having duplicate code
     *
     * @param rs represents a RowSet object containing a set of rows
     * @param movieDetails represents the given MovieDetails object
     */
    public void getMovieDetails(SqlRowSet rs, MovieDetails movieDetails) {
        movieDetails.setMovie_details_id(rs.getInt("movie_details_id"));
        movieDetails.setName(rs.getString("name"));
        movieDetails.setGenre(rs.getString("genre"));
        movieDetails.setRelease_date(rs.getDate("release_date").toLocalDate());
        movieDetails.setDuration_minutes(rs.getInt("duration_minutes"));
        movieDetails.setDescription(rs.getString("description"));
        movieDetails.setLanguage(rs.getString("language"));
        movieDetails.setPoster(rs.getString("poster"));
        movieDetails.setTrailer(rs.getString("trailer"));
    }


    /**
     * Method initializes MovieDetails objects using the given RowSet and adds them to a list that is returned in the end
     *
     * @param movieDetailsList represents a list of MovieDetails objects
     * @param rs represents a RowSet object containing a set of rows
     * @return a list of MoviePlan objects
     */
    private List<MovieDetails> getMovieDetailsList(List<MovieDetails> movieDetailsList, SqlRowSet rs) {
        try {

            while (rs.next()) {
                MovieDetails movieDetails = new MovieDetails();
                getMovieDetails(rs, movieDetails);
                movieDetailsList.add(movieDetails);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return movieDetailsList;
    }
}
