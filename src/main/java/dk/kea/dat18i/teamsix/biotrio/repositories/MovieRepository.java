package dk.kea.dat18i.teamsix.biotrio.repositories;

import dk.kea.dat18i.teamsix.biotrio.models.Movie;
import dk.kea.dat18i.teamsix.biotrio.models.MovieDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MovieRepository {


    @Autowired
    private JdbcTemplate jdbc;

    public List<Movie> findAllMovies() {
        String query = "SELECT movie_id, movie.movie_details_id, type, name, genre, release_date, duration_minutes, description, language, poster, trailer\n" +
                "FROM movie \n" +
                "INNER JOIN movie_details \n" +
                "ON (movie.movie_details_id = movie_details.movie_details_id)\n" +
                "ORDER BY release_date;";

        List<Movie> movieList = new ArrayList<>();
        SqlRowSet rs = jdbc.queryForRowSet(query);
        return getMovieList(movieList, rs);
    }


    public Movie findMovie(int id) {
        String query = "SELECT movie_id, movie.movie_details_id, type, name, genre, release_date, duration_minutes, description, language, poster, trailer\n" +
                "FROM movie \n" +
                "INNER JOIN movie_details \n" +
                "ON (movie.movie_details_id = movie_details.movie_details_id)\n" +
                "WHERE movie_id = ? ;";
        SqlRowSet rs = jdbc.queryForRowSet(query, id);
        Movie movie = new Movie();
        try {

            if (rs.first()) {

                getMovie(rs, movie);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return movie;
    }

    public Movie saveMovie(Movie movie){

        java.sql.Date date = new java.sql.Date(0000-00-00);
        PreparedStatementCreator psc_movie_details = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO movie_details (description, genre, language, name, poster, trailer, duration_minutes, release_date)VALUES( ?, ?, ?, ?, ?, ?, ?, ?)", new String[]{"movie_details_id"});
                ps.setString(1, movie.getMovieDetails().getDescription());
                ps.setString(2, movie.getMovieDetails().getGenre());
                ps.setString(3, movie.getMovieDetails().getLanguage());
                ps.setString(4, movie.getMovieDetails().getName());
                ps.setString(5,movie.getMovieDetails().getPoster());
                ps.setString(6,movie.getMovieDetails().getTrailer());
                ps.setInt(7, movie.getMovieDetails().getDuration_minutes());
                ps.setDate(8, java.sql.Date.valueOf(movie.getMovieDetails().getRelease_date()));
                return ps;
            }
        };

        KeyHolder movie_details_id = new GeneratedKeyHolder();
        jdbc.update(psc_movie_details, movie_details_id);
        movie.getMovieDetails().setMovie_details_id(movie_details_id.getKey().intValue());

        PreparedStatementCreator psc_movie = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO movie VALUES (NULL, ?, ?)", new String[]{"movie_id"});
                ps.setInt(1, movie.getMovieDetails().getMovie_details_id());
                ps.setBoolean(2, movie.getType());
                return ps;
            }
        };

        KeyHolder movie_id = new GeneratedKeyHolder();
        jdbc.update(psc_movie, movie_id);
        movie.setMovie_id(movie_id.getKey().intValue());
        return movie;
    }

    public List<Movie> findMovieByGender(String gender) {
        String query = "SELECT movie_id, movie.movie_details_id, type, name, genre, release_date, duration_minutes, description, language, poster, trailer\n" +
                "FROM movie \n" +
                "INNER JOIN movie_details \n" +
                "ON (movie.movie_details_id = movie_details.movie_details_id)\n" +
                "WHERE (movie_details.genre) like '?' ORDER BY release_date;";

        SqlRowSet rs = jdbc.queryForRowSet(query, gender);
        List<Movie> movieList = new ArrayList<>();
        return getMovieList(movieList, rs);
    }

    public List<Movie> findMovieByName(String name) {
        String query = "SELECT movie_id, movie.movie_details_id, type, name, genre, release_date, duration_minutes, description, language, poster, trailer\n" +
                "FROM movie \n" +
                "INNER JOIN movie_details \n" +
                "ON (movie.movie_details_id = movie_details.movie_details_id)\n" +
                "WHERE (movie_details.name) LIKE ?;";
        SqlRowSet rs = jdbc.queryForRowSet(query, "%" + name + "%");
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
//        movie.setAvailable(rs.getBoolean("available"));
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
