package dk.kea.dat18i.teamsix.biotrio.repositories;

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

/**
 * Represents the repository for the theater room
 */
@Repository
public class TheaterRoomRepository {

    @Autowired
    private JdbcTemplate jdbc;

    /**
     * Method finds all the theater room's in the database
     *
     * @return a list containing all the theater rooms
     */
    public List<TheaterRoom> findAllTheaterRoom() {
        String query = "SELECT * from theater_room";

        SqlRowSet rs = jdbc.queryForRowSet(query);
        List<TheaterRoom> theaterRoomList = new ArrayList<>();

        return getTheaterRoomList(theaterRoomList, rs);
    }

    /**
     * Method finds the theater room that has a given id in the database
     *
     * @param id represents the theater room's id
     * @return a TheaterRoom object that has the given id
     */
    public TheaterRoom findTheaterRoom(int id) {
        String query = "SELECT * FROM theater_room WHERE theater_room_id = ? ;";

        SqlRowSet rs = jdbc.queryForRowSet(query, id);
        TheaterRoom theaterRoom = new TheaterRoom();

        try {

            if (rs.first()) {

                getTheaterRoom(rs, theaterRoom);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return theaterRoom;
    }

    /**
     * Method deletes from the database the theater room that has the given id
     *
     * @param id represents the theater room's id
     */
    public void deleteTheaterRoom(int id) {
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement("DELETE from theater_room where theater_room_id = ?");
                ps.setInt(1, id);
                return ps;
            }
        };
        jdbc.update(psc);
    }

    /**
     * Method inserts in the database the given TheaterRoom object
     *
     * @param theaterRoom represents a TheaterRoom object
     */
    public void insertTheaterRoom(TheaterRoom theaterRoom) {
        PreparedStatementCreator psc = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO theater_room values(null, ?, ?, ?, ?)");
                ps.setString(1, theaterRoom.getName());
                ps.setInt(2, theaterRoom.getRows_no());
                ps.setInt(3, theaterRoom.getColumns_no());
                ps.setBoolean(4, theaterRoom.getCapability_3d());
                return ps;
            }
        };

        jdbc.update(psc);

    }

    /**
     * Method updates the information from the database of the given TheaterRoom object
     *
     * @param theaterRoom represents a TheaterRoom object
     */
    public void editTheaterRoom(TheaterRoom theaterRoom) {
        PreparedStatementCreator psc = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement("UPDATE theater_room set name = ? , rows_no = ? , columns_no = ?, `3d_capability` = ? WHERE theater_room_id = ?");
                ps.setString(1, theaterRoom.getName());
                ps.setInt(2, theaterRoom.getRows_no());
                ps.setInt(3, theaterRoom.getColumns_no());
                ps.setBoolean(4, theaterRoom.getCapability_3d());
                ps.setInt(5, theaterRoom.getTheater_room_id());
                return ps;
            }
        };

        jdbc.update(psc);
    }

    /**
     * Method sets theater room id, name, rows number, columns number, 3d capability inside the given TheaterRoom object using the given SqlRowSet
     * This method is used for avoiding writing the same setters in every other method and having duplicate code
     *
     * @param rs represents a RowSet object containing a set of rows
     * @param theaterRoom represents a TheaterRoom object
     */
    private void getTheaterRoom(SqlRowSet rs, TheaterRoom theaterRoom) {

        theaterRoom.setTheater_room_id(rs.getInt("theater_room_id"));
        theaterRoom.setName(rs.getString("name"));
        theaterRoom.setRows_no(rs.getInt("rows_no"));
        theaterRoom.setColumns_no(rs.getInt("columns_no"));
        theaterRoom.setCapability_3d(rs.getBoolean("3d_capability"));

    }

    /**
     * Method initializes TheaterRoom objects using the given RowSet and adds them to a list that is returned in the end
     *
     * @param theaterRoomList represents a list of TheaterRoom objects
     * @param rs represents a RowSet object containing a set of rows
     * @return a list of TheaterRoom objects
     */
    private List<TheaterRoom> getTheaterRoomList(List<TheaterRoom> theaterRoomList, SqlRowSet rs) {
        try {
            while (rs.next()) {
                TheaterRoom theaterRoom = new TheaterRoom();
                getTheaterRoom(rs, theaterRoom);
                theaterRoomList.add(theaterRoom);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return theaterRoomList;
    }


}
