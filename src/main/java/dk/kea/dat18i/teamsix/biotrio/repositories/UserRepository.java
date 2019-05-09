package dk.kea.dat18i.teamsix.biotrio.repositories;

import dk.kea.dat18i.teamsix.biotrio.models.Movie;
import dk.kea.dat18i.teamsix.biotrio.models.MovieDetails;
import dk.kea.dat18i.teamsix.biotrio.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserRepository {
    //TODO

    @Autowired
    private JdbcTemplate jdbc;

    public List<User> findAllUsers() {
        String query = "select user.user_id, role, username, enabled from user inner join user_role on user.user_id = user_role.user_id";

        List<User> userList = new ArrayList<>();
        SqlRowSet rs = jdbc.queryForRowSet(query);
        return getUserList(userList, rs);
    }


    public User findUser(int id) {
        String query = "SELECT * FROM user WHERE movie_id = ? ;";
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

    public void deleteUser(int id)
    {
        PreparedStatementCreator psc = connection -> {
            PreparedStatement ps = connection.prepareStatement("DELETE from user where user_id = ?");
            ps.setInt(1, id);
            return ps;
        };

        jdbc.update(psc);
    }

    public User insertUser(User user)
    {
        PreparedStatementCreator psc = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO USERS VALUES(null, ?, ?, ?, ?)", new String[]{"id"});
                ps.setInt(1, user.getUser_id());
                ps.setString(2, user.getUsername());
                ps.setString(3, user.getPassword());
                ps.setBoolean(4, user.getEnabled());
                return ps;
            }
        };

        KeyHolder id = new GeneratedKeyHolder();
        jdbc.update(psc, id);
        user.setUser_id(id.getKey().intValue());
        return user;
    }

    private void getUser(SqlRowSet rs, User user) {
        user.setUser_id(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setEnabled(rs.getBoolean("enabled"));
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
