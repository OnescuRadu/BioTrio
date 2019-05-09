package dk.kea.dat18i.teamsix.biotrio.repositories;

import dk.kea.dat18i.teamsix.biotrio.models.Movie;
import dk.kea.dat18i.teamsix.biotrio.models.MovieDetails;
import dk.kea.dat18i.teamsix.biotrio.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserRepository {
    //TODO

    @Autowired
    private JdbcTemplate jdbc;

    public List<User> findAllUsers() {
        String query = "SELECT * from users";

        List<User> userList = new ArrayList<>();
        SqlRowSet rs = jdbc.queryForRowSet(query);
        return getUserList(userList, rs);
    }


    public User findUser(int id) {
        String query = "SELECT * FROM users WHERE movie_id = ? ;";
        SqlRowSet rs = jdbc.queryForRowSet(query, id);
        User user = new User();
        try {

            if (rs.first()) {

                getUser(rs, user);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    private void getUser(SqlRowSet rs, User user) {
        user.setUser_id(rs.getInt("movie_id"));
        user.setUsername(rs.getString("movie_details_id"));
        user.setPassword(rs.getString("type"));
        user.setEnabled(rs.getBoolean("available"));
    }

    private List<User> getUserList(List<User> userList, SqlRowSet rs) {
        try {

            while (rs.next()) {
                User user = new User();
                getUser(rs, user);
                userList.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }
}
