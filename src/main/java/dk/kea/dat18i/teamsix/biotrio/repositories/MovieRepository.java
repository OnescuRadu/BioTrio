package dk.kea.dat18i.teamsix.biotrio.repositories;

import dk.kea.dat18i.teamsix.biotrio.models.Movie;
import dk.kea.dat18i.teamsix.biotrio.models.MovieDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MovieRepository {

    @Autowired
    private JdbcTemplate jdbc;

    public List<Movie> findAllMovies() {
        String query = "SELECT movie_id, movie.movie_details_id, type, available, name, genre, release_date, duration_minutes, description, language, poster, trailer\n" +
                "FROM movie \n" +
                "INNER JOIN movie_details \n" +
                "ON (movie.movie_details_id = movie_details.movie_details_id)\n" +
                "ORDER BY release_date;";

        List<Movie> movieList = new ArrayList<>();
        SqlRowSet rs = jdbc.queryForRowSet(query);
        return getMovieList(movieList, rs);
    }


    public Movie findMovie(int id) {
        String query = "SELECT movie_id, movie.movie_details_id, type, available, name, genre, release_date, duration_minutes, description, language, poster, trailer\n" +
                "FROM movie \n" +
                "INNER JOIN movie_details \n" +
                "ON (movie.movie_details_id = movie_details.movie_details_id)\n" +
                "WHERE movie_id = ? ;";
        SqlRowSet rs = jdbc.queryForRowSet(query, id);
        Movie movie = new Movie();
        try {

            while (rs.first()) {

                getMovie(rs, movie);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return movie;
    }

    public List<Movie> findMovieByGender(String gender) {
        String query = "SELECT movie_id, movie.movie_details_id, type, available, name, genre, release_date, duration_minutes, description, language, poster, trailer\n" +
                "FROM movie \n" +
                "INNER JOIN movie_details \n" +
                "ON (movie.movie_details_id = movie_details.movie_details_id)\n" +
                "WHERE (movie_details.genre) like '?' ORDER BY release_date;";

        SqlRowSet rs = jdbc.queryForRowSet(query, gender);
        List<Movie> movieList = new ArrayList<>();
        return getMovieList(movieList, rs);
    }

    public void deleteMovie(int id)
    {
        PreparedStatementCreator psc = connection -> {
            PreparedStatement ps = connection.prepareStatement("DELETE from movie where movie_id = ?");
            ps.setInt(1, id);
            return ps;
        };
        jdbc.update(psc);
    }

    private void getMovie(SqlRowSet rs, Movie movie) {
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
    }

    private List<Movie> getMovieList(List<Movie> movieList, SqlRowSet rs) {
        try {

            while (rs.next()) {
                Movie movie = new Movie();
                getMovie(rs, movie);
                movieList.add(movie);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return movieList;
    }

}
