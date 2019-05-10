package dk.kea.dat18i.teamsix.biotrio.repositories;

import dk.kea.dat18i.teamsix.biotrio.models.MoviePlan;
import dk.kea.dat18i.teamsix.biotrio.models.TheaterRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MoviePlanRepository {

    @Autowired
    private JdbcTemplate jdbc;

    public MoviePlan findMoviePlan(int id) {
        //TODO
        //INNER JOIN WITH movie
        String query = "SELECT movie_plan_id, movie_id, movie_plan.theater_room_id, date, start_time, price, name, rows_no, columns_no, 3d_capability\n" +
                "FROM movie_plan \n" +
                "INNER JOIN theater_room \n" +
                "ON (movie_plan.theater_room_id = theater_room.theater_room_id) WHERE movie_id = ? ;";

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


    public List<MoviePlan> findAllMoviePlan() {
        String query = "SELECT movie_plan_id, movie_id, movie_plan.theater_room_id, date, start_time, price, name, rows_no, columns_no, 3d_capability\n" +
                "FROM movie_plan \n" +
                "INNER JOIN theater_room \n" +
                "ON (movie_plan.theater_room_id = theater_room.theater_room_id);";

        SqlRowSet rs = jdbc.queryForRowSet(query);
        List<MoviePlan> moviePlanList = new ArrayList<>();

        return getMoviePlanList(moviePlanList, rs);
    }

    public void deleteMoviePlan(int id) {
        PreparedStatementCreator psc = connection -> {
            PreparedStatement ps = connection.prepareStatement("DELETE from movie_plan where movie_plan_id = ?");
            ps.setInt(1, id);
            return ps;
        };
        jdbc.update(psc);
    }

    private void getMoviePlan(SqlRowSet rs, MoviePlan moviePlan) {

        moviePlan.setMovie_plan_id(rs.getInt("movie_plan_id"));
        moviePlan.setMovie_id(rs.getInt("movie_id"));
        moviePlan.setTheater_room_id(rs.getInt("theater_room_id"));
        moviePlan.setDate_time(rs.getTimestamp("date").toLocalDateTime());
        moviePlan.setPrice(rs.getDouble("price"));

        //TODO
        //inner join with movie
        //set everything into Movie object
        //set everything into theater room object
        //set Movie and Theater room into moviePlan

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

}
