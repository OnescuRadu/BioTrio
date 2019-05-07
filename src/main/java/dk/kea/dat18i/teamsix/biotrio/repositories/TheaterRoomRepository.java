package dk.kea.dat18i.teamsix.biotrio.repositories;

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
public class TheaterRoomRepository
{
    @Autowired
    private JdbcTemplate jdbc;

    public List<TheaterRoom> findAllTheaterRoom() {
        String query = "SELECT * from theater_room";

        SqlRowSet rs = jdbc.queryForRowSet(query);
        List<TheaterRoom> theaterRoomList = new ArrayList<>();

        return getTheaterRoomList(theaterRoomList, rs);
    }

    public List<TheaterRoom> findTheaterRoom(int id) {
        String query = "SELECT * FROM theater_room WHERE theater_room_id = ? ;";

        SqlRowSet rs = jdbc.queryForRowSet(query, id);
        List<TheaterRoom> theaterRoomList = new ArrayList<>();

        return getTheaterRoomList(theaterRoomList, rs);
    }

    public void deleteTheaterRoom(int id)
    {
        PreparedStatementCreator psc = connection -> {
            PreparedStatement ps = connection.prepareStatement("DELETE from theater_room where theater_room_id = ?");
            ps.setInt(1, id);
            return ps;
        };
        jdbc.update(psc);
    }

    private List<TheaterRoom> getTheaterRoomList(List<TheaterRoom> theaterRoomList, SqlRowSet rs)
    {
        try {

            while (rs.next()) {

                TheaterRoom theaterRoom = new TheaterRoom();


                theaterRoom.setTheater_room_id(rs.getInt("theater_room_id"));
                theaterRoom.setName(rs.getString("name"));
                theaterRoom.setRows_no(rs.getInt("rows_no"));
                theaterRoom.setColumns_no(rs.getInt("columns_no"));
                theaterRoom.setCapability_3d(rs.getBoolean("3d_capability"));

                theaterRoomList.add(theaterRoom);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return theaterRoomList;
    }
}
