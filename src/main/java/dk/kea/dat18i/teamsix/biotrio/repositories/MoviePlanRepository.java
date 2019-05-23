package dk.kea.dat18i.teamsix.biotrio.repositories;

import dk.kea.dat18i.teamsix.biotrio.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MoviePlanRepository {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private MovieRepository movieRepo;

    public MoviePlan findMoviePlan(int id) {
        String query = "SELECT movie_plan_id, date_time,  price, movie_plan.movie_id, movie.movie_details_id, type, movie_details.name, movie_plan.theater_room_id, theater_room.name as theater_room_name,rows_no,columns_no,3d_capability \n" +
                "FROM movie_plan\n" +
                "INNER JOIN theater_room\n" +
                "ON (movie_plan.theater_room_id = theater_room.theater_room_id)\n" +
                "INNER JOIN movie\n" +
                "ON (movie_plan.movie_id = movie.movie_id)\n" +
                "INNER JOIN movie_details\n" +
                "ON (movie.movie_details_id = movie_details.movie_details_id) WHERE movie_plan.movie_plan_id = ? ;";

        SqlRowSet rs = jdbc.queryForRowSet(query, id);
        MoviePlan moviePlan = new MoviePlan();
        try {

            if (rs.first()) {
                getMoviePlan(rs, moviePlan);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return moviePlan;
    }

    public List<MoviePlan> findMoviePlanByMovieId(int id) {
        String query = "SELECT movie_plan_id, date_time,  price, movie_plan.movie_id, movie.movie_details_id, type, movie_details.name,movie_plan.theater_room_id, theater_room.name as theater_room_name,rows_no,columns_no,3d_capability \n" +
                "FROM movie_plan\n" +
                "INNER JOIN theater_room\n" +
                "ON (movie_plan.theater_room_id = theater_room.theater_room_id)\n" +
                "INNER JOIN movie\n" +
                "ON (movie_plan.movie_id = movie.movie_id)\n" +
                "INNER JOIN movie_details\n" +
                "ON (movie.movie_details_id = movie_details.movie_details_id) " +
                "WHERE movie_plan.movie_id = ? ;";

        SqlRowSet rs = jdbc.queryForRowSet(query, id);
        List<MoviePlan> moviePlanList = new ArrayList<>();

        return getMoviePlanList(moviePlanList, rs);
    }

    public List<MoviePlan> findAllMoviePlan() {
        String query = "SELECT movie_plan_id, date_time,  price, movie_plan.movie_id, movie.movie_details_id, type, movie_details.name,movie_plan.theater_room_id, theater_room.name as theater_room_name,rows_no,columns_no,3d_capability \n" +
                "FROM movie_plan\n" +
                "INNER JOIN theater_room\n" +
                "ON (movie_plan.theater_room_id = theater_room.theater_room_id)\n" +
                "INNER JOIN movie\n" +
                "ON (movie_plan.movie_id = movie.movie_id)\n" +
                "INNER JOIN movie_details\n" +
                "ON (movie.movie_details_id = movie_details.movie_details_id)";

        SqlRowSet rs = jdbc.queryForRowSet(query);
        List<MoviePlan> moviePlanList = new ArrayList<>();

        return getMoviePlanList(moviePlanList, rs);
    }

    public void deleteMoviePlan(int id) {
        PreparedStatementCreator psc = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement("DELETE from movie_plan where movie_plan_id = ?");
                ps.setInt(1, id);
                return ps;
            }
        };
        jdbc.update(psc);
    }

    private void getMoviePlan(SqlRowSet rs, MoviePlan moviePlan) {

        moviePlan.setMovie_plan_id(rs.getInt("movie_plan_id"));

        moviePlan.setDate_time(rs.getTimestamp("date_time").toLocalDateTime());
        moviePlan.setPrice(rs.getDouble("price"));
        moviePlan.setMovie_id(rs.getInt("movie_id"));
        moviePlan.setTheater_room_id(rs.getInt("theater_room_id"));


        Movie movie = new Movie();
        MovieDetails movieDetails = new MovieDetails();
        TheaterRoom theaterRoom = new TheaterRoom();
        movie.setMovie_id(rs.getInt("movie_id"));
        movie.setMovie_details_id(rs.getInt("movie_details_id"));
        movie.setType(rs.getBoolean("type"));


        movieDetails.setMovie_details_id(rs.getInt("movie_details_id"));
        movieDetails.setName(rs.getString("name"));

        theaterRoom.setTheater_room_id(rs.getInt("theater_room_id"));
        theaterRoom.setName(rs.getString("theater_room_name"));
        theaterRoom.setRows_no(rs.getInt("rows_no"));
        theaterRoom.setColumns_no(rs.getInt("columns_no"));
        theaterRoom.setCapability_3d(rs.getBoolean("3d_capability"));

        movie.setMovieDetails(movieDetails);
        moviePlan.setMovie(movie);
        moviePlan.setTheaterRoom(theaterRoom);

    }

    private List<MoviePlan> getMoviePlanList(List<MoviePlan> moviePlanList, SqlRowSet rs) {
        try {

            while (rs.next()) {
                MoviePlan moviePlan = new MoviePlan();
                getMoviePlan(rs, moviePlan);

                moviePlanList.add(moviePlan);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return moviePlanList;
    }

    public void insertMoviePlan(MoviePlan moviePlan) {
        PreparedStatementCreator psc = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO movie_plan values(null, ?, ?, ?, ?)");
                ps.setInt(1, moviePlan.getMovie_id());
                ps.setInt(2, moviePlan.getTheater_room_id());
                ps.setTimestamp(3, Timestamp.valueOf(moviePlan.getDate_time()));
                ps.setDouble(4, moviePlan.getPrice());
                return ps;
            }
        };

        jdbc.update(psc);
    }

    public void editMoviePlan(MoviePlan moviePlan) {
        PreparedStatementCreator psc = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement("UPDATE movie_plan set movie_id = ? , theater_room_id = ? , price = ? , date_time = ? WHERE movie_plan_id = ?");
                ps.setInt(1, moviePlan.getMovie_id());
                ps.setInt(2, moviePlan.getTheater_room_id());
                ps.setDouble(3, moviePlan.getPrice());
                ps.setTimestamp(4, Timestamp.valueOf(moviePlan.getDate_time()));
                ps.setInt(5, moviePlan.getMovie_plan_id());
                return ps;
            }
        };

        jdbc.update(psc);
    }

    public boolean checkIfMoviePlanIsAvailable(int theater_room_id, LocalDateTime date_time, int duration_minutes)
    {
        String query = "select theater_room_id, duration_minutes, date_time from movie_plan\n" +
                "inner join movie\n" +
                "on movie.movie_id = movie_plan.movie_id\n" +
                "inner join movie_details\n" +
                "on movie_details.movie_details_id = movie.movie_details_id\n" +
                "where theater_room_id = ? && (date_time = ? || DATE_ADD(date_time, INTERVAL duration_minutes minute) >= ? || DATE_ADD(?, INTERVAL ? minute) >= date_time );";

        SqlRowSet rs = jdbc.queryForRowSet(query, theater_room_id, Timestamp.valueOf(date_time), Timestamp.valueOf(date_time), Timestamp.valueOf(date_time), duration_minutes);
        return rs.first() ;
    }


}
