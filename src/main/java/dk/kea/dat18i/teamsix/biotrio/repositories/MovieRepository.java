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
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the repository for the movie
 */

@Repository
public class MovieRepository {


    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private MovieDetailsRepository movieDetailsRepo;

    /**
     * Method finds all the movies in the database
     *
     * @return a list of Movie objects
     */

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

    /**
     * Method finds the movie that has the given movie id in the database
     *
     * @param id represents the movie's id
     * @return a populated Movie object
     */

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

    /**
     * Method inserts in the database the given Movie object
     *
     * @param movie represents a Movie object
     */

    public void saveMovie(Movie movie) {

        PreparedStatementCreator psc_movie_details = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO movie_details (description, genre, language, name, poster, trailer, duration_minutes, release_date)VALUES( ?, ?, ?, ?, ?, ?, ?, ?)", new String[]{"movie_details_id"});
                ps.setString(1, movie.getMovieDetails().getDescription());
                ps.setString(2, movie.getMovieDetails().getGenre());
                ps.setString(3, movie.getMovieDetails().getLanguage());
                ps.setString(4, movie.getMovieDetails().getName());
                ps.setString(5, movie.getMovieDetails().getPoster());
                ps.setString(6, movie.getMovieDetails().getTrailer());
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
    }

    /**
     * Method inserts in the database the given Movie object with details which already exists
     *
     * @param movie represents a Movie object
     */

    public void insertMovieUsingDetails(Movie movie)
    {
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO movie VALUES (NULL, ?, ?)");
                ps.setInt(1, movie.getMovie_details_id());
                ps.setBoolean(2, movie.getType());
                return ps;
            }
        };
        jdbc.update(psc);
    }

    /**
     * Method finds the movie that has the given gender in the database
     *
     * @param gender represents the given gender
     * @return a list of Movie objects
     */

    public List<Movie> findMovieByGender(String gender) {
        String query = "SELECT movie_id, movie.movie_details_id, type, name, genre, release_date, duration_minutes, description, language, poster, trailer \n" +
                "FROM movie\n" +
                "INNER JOIN movie_details \n" +
                "ON (movie.movie_details_id = movie_details.movie_details_id)\n" +
                "WHERE (movie_details.genre) like ?";

        SqlRowSet rs = jdbc.queryForRowSet(query, "%" + gender + "%");
        List<Movie> movieList = new ArrayList<>();
        return getMovieList(movieList, rs);
    }

    /**
     * Method finds the movie that has the given name in the database
     *
     * @param name represents the given name
     * @return a list of Movie objects
     */

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

    /**
     * Method deletes from the database the movie that has the given id
     *
     * @param id represents the movie's id
     */

    public void deleteMovie(int id) {
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement("DELETE from movie where movie_id = ?");
                ps.setInt(1, id);
                return ps;
            }
        };
        jdbc.update(psc);
    }

    /**
     * Method sets movie id, movie details id, movie type inside the given Movie object using the given SqlRowSet
     * This method is used for avoiding writing the same setters in every other method and having duplicate code
     *
     * @param rs   represents a RowSet object containing a set of rows
     * @param movie represents a Movie object
     */

    private void getMovie(SqlRowSet rs, Movie movie) {
        movie.setMovie_id(rs.getInt("movie_id"));
        movie.setMovie_details_id(rs.getInt("movie_details_id"));
        movie.setType(rs.getBoolean("type"));

        MovieDetails movieDetails = new MovieDetails();

        movieDetailsRepo.getMovieDetails(rs, movieDetails);

        movie.setMovieDetails(movieDetails);
    }

    /**
     * Method initializes Movie objects using the given RowSet and adds them to a list that is returned in the end
     *
     * @param movieList represents a list of Movie objects
     * @param rs       represents a RowSet object containing a set of rows
     * @return a list of Movie objects
     */

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

    /**
     * Method updates the information from the database of the given Movie object
     *
     * @param movie represents a Movie object
     */

    public void updateMovie(Movie movie) {

        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement("UPDATE movie_details SET name = ?, genre = ?, release_date = ?, duration_minutes= ?, description = ?, language = ?, poster = ?, trailer = ? WHERE movie_details_id = ?");
                ps.setString(1, movie.getMovieDetails().getName());
                ps.setString(2, movie.getMovieDetails().getGenre());
                ps.setDate(3, java.sql.Date.valueOf(movie.getMovieDetails().getRelease_date()));
                ps.setInt(4, movie.getMovieDetails().getDuration_minutes());
                ps.setString(5, movie.getMovieDetails().getDescription());
                ps.setString(6, movie.getMovieDetails().getLanguage());
                ps.setString(7, movie.getMovieDetails().getPoster());
                ps.setString(8, movie.getMovieDetails().getTrailer());
                ps.setInt(9, movie.getMovieDetails().getMovie_details_id());
                return ps;
            }
        };

        jdbc.update(psc);

        PreparedStatementCreator psc2 = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement("UPDATE movie SET type = ?  WHERE movie_id = ?");
                ps.setBoolean(1, movie.getType());
                ps.setInt(2, movie.getMovie_id());
                return ps;
            }
        };
        jdbc.update(psc2);
    }

}
