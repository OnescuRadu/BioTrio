package dk.kea.dat18i.teamsix.biotrio.repositories;

import dk.kea.dat18i.teamsix.biotrio.models.MoviePlan;
import dk.kea.dat18i.teamsix.biotrio.models.TheaterRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MoviePlanRepository {

    @Autowired
    private JdbcTemplate jdbc;

    public List<MoviePlan> findMoviePlan(int id) {
        String query = "SELECT movie_plan_id, movie_id, movie_plan.theater_room_id, date, start_time, price, name, rows_no, columns_no, 3d_capability\n" +
                "FROM movie_plan \n" +
                "INNER JOIN theater_room \n" +
                "ON (movie_plan.theater_room_id = theater_room.theater_room_id) WHERE movie_id = ? ;";

        SqlRowSet rs = jdbc.queryForRowSet(query, id);
        List<MoviePlan> moviePlanList = new ArrayList<>();

        return getMoviePlanList(moviePlanList, rs);
    }

    public List<MoviePlan> findAllMoviePlan() {
        String query = "SELECT movie_plan_id, movie_id, movie_plan.theater_room_id, date, start_time, price, name, rows_no, columns_no, 3d_capability\n" +
                "FROM movie_plan \n" +
                "INNER JOIN theater_room \n" +
                "ON (movie_plan.theater_room_id = theater_room.theater_room_id);";

        SqlRowSet rs = jdbc.queryForRowSet(query);
        List<MoviePlan> moviePlanList = new ArrayList<>();

        return getMoviePlanList(moviePlanList, rs);
    }

    public void deleteMoviePlan(int id)
    {
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

    public List<MoviePlan> getMoviePlanList(List<MoviePlan> moviePlanList, SqlRowSet rs)
    {
        try {

            while (rs.next()) {
                MoviePlan moviePlan = new MoviePlan();
                TheaterRoom theaterRoom = new TheaterRoom();

                moviePlan.setMovie_plan_id(rs.getInt("movie_plan_id"));
                moviePlan.setMovie_id(rs.getInt("movie_id"));
                moviePlan.setTheater_room_id(rs.getInt("theater_room_id"));
                moviePlan.setDate(rs.getDate("date").toLocalDate());
                moviePlan.setStart_time(rs.getTime("start_time").toLocalTime());
                moviePlan.setPrice(rs.getDouble("price"));

                theaterRoom.setTheater_room_id(rs.getInt("theater_room_id"));
                theaterRoom.setName(rs.getString("name"));
                theaterRoom.setRows_no(rs.getInt("rows_no"));
                theaterRoom.setColumns_no(rs.getInt("columns_no"));
                theaterRoom.setCapability_3d(rs.getBoolean("3d_capability"));

                moviePlanList.add(moviePlan);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return moviePlanList;
    }
}
