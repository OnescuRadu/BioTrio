package dk.kea.dat18i.teamsix.biotrio;

import dk.kea.dat18i.teamsix.biotrio.models.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MovieRepository {

    @Autowired
    private JdbcTemplate jdbc;

    public List<Movie> findAllCars() {
        SqlRowSet rs = jdbc.queryForRowSet("SELECT movie_id, type, available, name, genre, release_date, duration_minutes, description, language, poster, trailer\n" +
                "FROM movie \n" +
                "INNER JOIN movie_details \n" +
                "ON (movie.movie_details_id = movie_details.movie_details_id);");

        List<Movie> movieList = new ArrayList<Movie>();
        while (rs.next()) {
            //Movie movie = new Movie();
            //movie.setMovie_id(rs.getInt("id"));

            //movieList.add(movie);
        }
        return movieList;
    }



}
