package dk.kea.dat18i.teamsix.biotrio.repositories;

import dk.kea.dat18i.teamsix.biotrio.models.Movie;
import dk.kea.dat18i.teamsix.biotrio.models.MovieDetails;
import dk.kea.dat18i.teamsix.biotrio.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MovieDetailsRepository {

    @Autowired
    private JdbcTemplate jdbc;

    public List<MovieDetails> findAllMovieDetails() {
        String query = "SELECT * from movie_details";

        List<MovieDetails> movieDetailsList = new ArrayList<>();
        SqlRowSet rs = jdbc.queryForRowSet(query);
        return getMovieDetailsList(movieDetailsList, rs);
    }

    private void getMovieDetail(SqlRowSet rs, MovieDetails movieDetails) {
        movieDetailsSetter(rs, movieDetails);
    }

    static void movieDetailsSetter(SqlRowSet rs, MovieDetails movieDetails) {
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

    private List<MovieDetails> getMovieDetailsList(List<MovieDetails> movieDetailsList, SqlRowSet rs) {
        try {

            while (rs.next()) {
                MovieDetails movieDetails = new MovieDetails();
                getMovieDetail(rs, movieDetails);
                movieDetailsList.add(movieDetails);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return movieDetailsList;
    }
}
