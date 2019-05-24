package dk.kea.dat18i.teamsix.biotrio.repositories;

import dk.kea.dat18i.teamsix.biotrio.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the repository for the user
 */
@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbc;

    /**
     * Implementation of PasswordEncoder
     * Eg. 'pass' becomes '$2a$10$hLA38Utx1nxf9pVmhYa7xOCmM/CnC4UqBsbwvyFsP7fbOhMZy1iZa'
     */
    @Autowired
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * Method finds all the users in the database
     *
     * @return a list of User objects
     */
    public List<User> findAllUser() {
        String query = "SELECT * from user";

        List<User> userList = new ArrayList<>();
        SqlRowSet rs = jdbc.queryForRowSet(query);
        return getUserList(userList, rs);
    }


    /**
     * Method finds the user that has a given id in the database
     *
     * @param id represents the user's id
     * @return a populated User object
     */
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

    /**
     * Method deletes from the database the user that has the given id
     *
     * @param id represents the user's id
     */
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

    /**
     * Method inserts in the database the given User object
     *
     * @param user represents the User object
     */
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

    /**
     * Method updates the password from the database of the given User object
     *
     * @param user represents the User object
     */
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

    /**
     * Method updates the username, role and access status from the database of the given User object
     *
     * @param user represents the User object
     */
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

    /**
     * Method sets username, role, access status inside the given User object using the given SqlRowSet
     * This method is used for avoiding writing the same setters in every other method and having duplicate code
     *
     * @param rs   represents a RowSet object containing a set of rows
     * @param user represents a User object
     */
    private void getUser(SqlRowSet rs, User user) {
        user.setUser_id(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setRole(rs.getString("role").replaceFirst("^ROLE_", ""));
        user.setEnabled(rs.getBoolean("enabled"));
    }

    /**
     * Method initializes User objects using the given RowSet and adds them to a list that is returned in the end
     *
     * @param userList represents a list of User objects
     * @param rs       represents a RowSet object containing a set of rows
     * @return a list of User objects
     */
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

    /**
     * Method checks if the given username already exists in the database
     * <p>
     * rs.first() tries to move the cursor to the first row of the RowSet and returns true if the row exists or false if the row doesn't exist
     *
     * @param username represents the username
     * @return TRUE if the given username already exists and FALSE if it doesn't
     */
    public boolean checkIfUsernameExists(String username) {
        String query = "SELECT * FROM user WHERE username = ? ;";
        SqlRowSet rs = jdbc.queryForRowSet(query, username);
        return rs.first();
    }

    /**
     * Method checks if the given username already exists in the database but it does not have the given id
     * <p>
     * This method is used when the username is edited in the edit user page,
     * so it will return true if a username already exists and it does not belong to the same user that we edited
     * <p>
     * rs.first() tries to move the cursor to the first row of the RowSet and returns true if the row exists or false if the row doesn't exist
     *
     * @param username represents the username
     * @param id       represents the user's id
     * @return TRUE if the given username already exists and FALSE if it doesn't
     */
    public boolean checkIfUsernameExistsWithId(String username, int id) {
        String query = "SELECT * FROM user WHERE username = ? and user_id != ?;";
        SqlRowSet rs = jdbc.queryForRowSet(query, username, id);
        return rs.first();
    }


}
