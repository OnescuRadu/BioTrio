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

@Repository
public class TheaterRoomRepository {
    @Autowired
    private JdbcTemplate jdbc;

    public List<TheaterRoom> findAllTheaterRoom() {
        String query = "SELECT * from theater_room";

        SqlRowSet rs = jdbc.queryForRowSet(query);
        List<TheaterRoom> theaterRoomList = new ArrayList<>();

        return getTheaterRoomList(theaterRoomList, rs);
    }

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

    public String insertTheaterRoom(TheaterRoom theaterRoom) {
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
        return "redirect:/theater-room";
    }

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

    private void getTheaterRoom(SqlRowSet rs, TheaterRoom theaterRoom) {

        theaterRoom.setTheater_room_id(rs.getInt("theater_room_id"));
        theaterRoom.setName(rs.getString("name"));
        theaterRoom.setRows_no(rs.getInt("rows_no"));
        theaterRoom.setColumns_no(rs.getInt("columns_no"));
        theaterRoom.setCapability_3d(rs.getBoolean("3d_capability"));

    }

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
