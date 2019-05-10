package dk.kea.dat18i.teamsix.biotrio.repositories;

import dk.kea.dat18i.teamsix.biotrio.models.Booking;
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
public class BookingRepository {

    @Autowired
    private JdbcTemplate jdbc;

    public List<Booking> findAllBooking() {
        String query = "SELECT * from booking";

        SqlRowSet rs = jdbc.queryForRowSet(query);
        List<Booking> bookingList = new ArrayList<>();

        return getBookingList(bookingList, rs);
    }

    public Booking findBooking(int id) {
        String query = "SELECT * FROM booking WHERE booking_id = ? ;";

        SqlRowSet rs = jdbc.queryForRowSet(query, id);
        Booking booking = new Booking();

        try {

            if (rs.first()) {

                getBooking(rs, booking);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return booking;
    }

    public void deleteBooking(int id)
    {
        PreparedStatementCreator psc = connection -> {
            PreparedStatement ps = connection.prepareStatement("DELETE from booking where booking_id = ?");
            ps.setInt(1, id);
            return ps;
        };
        jdbc.update(psc);
    }

    public String insertBooking(Booking booking)
    {
        PreparedStatementCreator psc = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO booking values(null, ?, ?, ?, ?)");
                ps.setInt(1, booking.getMovie_plan_id());
                ps.setString(2, booking.getPhone_number());
                ps.setString(3, booking.getEmail());
                ps.setString(4, booking.getConfirmation_code());
                ps.setBoolean(5, booking.getPaid());
                return ps;
            }
        };

        jdbc.update(psc);
        return "redirect:/bookings";
    }

    public void editBooking(Booking booking)
    {
        PreparedStatementCreator psc = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement("UPDATE booking set movie_plan_id = ? , phone_number = ? , email = ?, `confirmation_code` = ?, paid = ? WHERE booking_id = ?");
                ps.setInt(1, booking.getMovie_plan_id());
                ps.setString(2, booking.getPhone_number());
                ps.setString(3, booking.getEmail());
                ps.setString(4, booking.getConfirmation_code());
                ps.setBoolean(5, booking.getPaid());
                ps.setInt(5, booking.getBooking_id());
                return ps;
            }
        };

        jdbc.update(psc);
    }

    private void getBooking(SqlRowSet rs, Booking booking) {

        booking.setBooking_id(rs.getInt("booking_id"));
        booking.setMovie_plan_id(rs.getInt("movie_plan_id"));
        booking.setPhone_number(rs.getString("phone_number"));
        booking.setEmail(rs.getString("email"));
        booking.setConfirmation_code(rs.getString("confirmation_code"));
        booking.setPaid(rs.getBoolean("paid"));
    }

    private List<Booking> getBookingList(List<Booking> bookingList, SqlRowSet rs) {
        try {
            while (rs.next()) {
                Booking booking = new Booking();
                getBooking(rs, booking);
                bookingList.add(booking);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookingList;
    }
}
