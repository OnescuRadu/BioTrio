package dk.kea.dat18i.teamsix.biotrio.repositories;

import dk.kea.dat18i.teamsix.biotrio.models.TheaterRoom;
import dk.kea.dat18i.teamsix.biotrio.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbc;

    public List<User> findAllUser() {
        String query = "SELECT * from user";

        List<User> userList = new ArrayList<>();
        SqlRowSet rs = jdbc.queryForRowSet(query);
        return getUserList(userList, rs);
    }


    public User findUser(int id) {
        String query = "SELECT * FROM user WHERE user_id = ? ;";
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

    public void deleteUser(int id) {
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement("DELETE from user where user_id = ?");
                ps.setInt(1, id);
                return ps;
            }
        };
        jdbc.update(psc);
    }

    public void insertUser(User user) {
        PreparedStatementCreator psc = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO user values(null, ?, ?, ?, ?)");
                ps.setString(1, user.getUsername());
                ps.setString(2, passwordEncoder().encode(user.getPassword()));
                ps.setString(3, user.getRole());
                ps.setBoolean(4, user.getEnabled());
                return ps;
            }
        };

        jdbc.update(psc);
    }

    public void editUserPassword(User user) {
        PreparedStatementCreator psc = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement("UPDATE user set password = ? where user_id = ?");
                ps.setString(1, passwordEncoder().encode(user.getPassword()));
                ps.setInt(2, user.getUser_id());
                return ps;
            }
        };

        jdbc.update(psc);
    }

    public void editUser(User user) {
        PreparedStatementCreator psc = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement("UPDATE user set username = ? , role = ? , enabled = ? WHERE user_id = ?");
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getRole());
                ps.setBoolean(3, user.getEnabled());
                ps.setInt(4, user.getUser_id());
                return ps;
            }
        };

        jdbc.update(psc);
    }

    private void getUser(SqlRowSet rs, User user) {
        user.setUser_id(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setRole(rs.getString("role").replaceFirst("^ROLE_", ""));
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

    @Autowired
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
