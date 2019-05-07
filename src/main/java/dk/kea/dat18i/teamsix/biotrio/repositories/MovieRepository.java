package dk.kea.dat18i.teamsix.biotrio.repositories;

import dk.kea.dat18i.teamsix.biotrio.models.Movie;
import dk.kea.dat18i.teamsix.biotrio.models.MovieDetails;
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

    public List<Movie> findAllMovies() throws Exception {
        String query = "SELECT movie_id, movie.movie_details_id, type, available, name, genre, release_date, duration_minutes, description, language, poster, trailer\n" +
                "FROM movie \n" +
                "INNER JOIN movie_details \n" +
                "ON (movie.movie_details_id = movie_details.movie_details_id)\n" +
                "ORDER BY release_date;";

        List<Movie> movieList = new ArrayList<>();
        SqlRowSet rs = jdbc.queryForRowSet(query);
        return getMovieList(movieList, rs);
    }


    public List<Movie> findMovie(int id) throws Exception {
        String query = "SELECT movie_id, movie.movie_details_id, type, available, name, genre, release_date, duration_minutes, description, language, poster, trailer\n" +
                "FROM movie \n" +
                "INNER JOIN movie_details \n" +
                "ON (movie.movie_details_id = movie_details.movie_details_id)\n" +
                "WHERE movie_id = ? ;";
        SqlRowSet rs = jdbc.queryForRowSet(query, id);
        List<Movie> movieList = new ArrayList<>();
        return getMovieList(movieList, rs);
    }

    public List<Movie> findMovieByGender(String gender) throws Exception {
        String query = "SELECT movie_id, movie.movie_details_id, type, available, name, genre, release_date, duration_minutes, description, language, poster, trailer\n" +
                "FROM movie \n" +
                "INNER JOIN movie_details \n" +
                "ON (movie.movie_details_id = movie_details.movie_details_id)\n" +
                "WHERE (movie_details.genre) like '?' ORDER BY release_date;";

        SqlRowSet rs = jdbc.queryForRowSet(query, gender);
        List<Movie> movieList = new ArrayList<>();
        return getMovieList(movieList, rs);
    }

    private List<Movie> getMovieList(List<Movie> movieList, SqlRowSet rs) {
        try {

            while (rs.next()) {
                Movie movie = new Movie();

                movie.setMovie_id(rs.getInt("movie_id"));
                movie.setMovie_details_id(rs.getInt("movie_details_id"));
                movie.setType(rs.getBoolean("type"));
                movie.setAvailable(rs.getBoolean("available"));
                MovieDetails movieDetails = new MovieDetails();
                movieDetails.setMovie_details_id(rs.getInt("movie_details_id"));
                movieDetails.setName(rs.getString("name"));
                movieDetails.setGenre(rs.getString("genre"));
                movieDetails.setRelease_date(rs.getDate("release_date").toLocalDate());
                movieDetails.setDuration_minutes(rs.getInt("duration_minutes"));
                movieDetails.setDescription(rs.getString("description"));
                movieDetails.setLanguage(rs.getString("language"));
                movieDetails.setPoster(rs.getString("poster"));
                movieDetails.setTrailer(rs.getString("trailer"));

                movie.setMovieDetails(movieDetails);

                movieList.add(movie);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return movieList;
    }

}
