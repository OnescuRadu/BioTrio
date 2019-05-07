package dk.kea.dat18i.teamsix.biotrio.repositories;

import dk.kea.dat18i.teamsix.biotrio.models.MoviePlan;
import dk.kea.dat18i.teamsix.biotrio.models.TeatherRoom;
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
public class TeatherRoomRepository
{
    @Autowired
    private JdbcTemplate jdbc;

    public List<TeatherRoom> findAllTeatherRoom() {
        String query = "SELECT * from teather_room";

        SqlRowSet rs = jdbc.queryForRowSet(query);
        List<TeatherRoom> teatherRoomList = new ArrayList<>();

        return getTeatherRoomList(teatherRoomList, rs);
    }

    public List<TeatherRoom> findTeatherRoom(int id) {
        String query = "SELECT * FROM teather_room WHERE movie_id = ? ;";

        SqlRowSet rs = jdbc.queryForRowSet(query, id);
        List<TeatherRoom> teatherRoomList = new ArrayList<>();

        return getTeatherRoomList(teatherRoomList, rs);
    }

    public void deleteTeatherRoom(int id)
    {
        PreparedStatementCreator psc = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement("DELETE from movie_plan where id = ?");
                ps.setInt(1, id);
                return ps;
            }
        };
    }

    public List<TeatherRoom> getTeatherRoomList(List<TeatherRoom> teatherRoomList, SqlRowSet rs)
    {
        try {

            while (rs.next()) {

                TeatherRoom teatherRoom = new TeatherRoom();


                teatherRoom.setTeather_room_id(rs.getInt("teather_room_id"));
                teatherRoom.setName(rs.getString("name"));
                teatherRoom.setRows_no(rs.getInt("rows_no"));
                teatherRoom.setColumns_no(rs.getInt("columns_no"));
                teatherRoom.setCapability_3d(rs.getBoolean("3d_capability"));

                teatherRoomList.add(teatherRoom);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return teatherRoomList;
    }
}
