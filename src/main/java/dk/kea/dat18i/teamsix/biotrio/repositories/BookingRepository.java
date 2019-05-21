package dk.kea.dat18i.teamsix.biotrio.repositories;


import dk.kea.dat18i.teamsix.biotrio.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
    private TicketRepository ticketRepo;

    @Autowired
    private JdbcTemplate jdbc;

    public List<Booking> findAllBooking() {
        String query = "SELECT booking.booking_id, booking.movie_plan_id, phone_number, email, confirmation_code, paid, movie_plan.movie_plan_id, date_time, price as ticket_price, duration_minutes, movie_plan.movie_id, \n" +
                "movie.movie_details_id, type as movie_type, movie_details.name as movie_name, language, movie_plan.theater_room_id, \n" +
                "theater_room.name as theater_room_name from booking\n" +
                "INNER JOIN movie_plan\n" +
                "on booking.movie_plan_id = movie_plan.movie_plan_id\n" +
                "INNER JOIN theater_room\n" +
                "ON (movie_plan.theater_room_id = theater_room.theater_room_id)\n" +
                "INNER JOIN movie\n" +
                "ON (movie_plan.movie_id = movie.movie_id)\n" +
                "INNER JOIN movie_details\n" +
                "ON (movie.movie_details_id = movie_details.movie_details_id)";

        SqlRowSet rs = jdbc.queryForRowSet(query);
        List<Booking> bookingList = new ArrayList<>();
        return getBookingList(bookingList, rs);
    }

    public Booking findBooking(int id) {
        String query = "SELECT booking.booking_id, booking.movie_plan_id, phone_number, email, confirmation_code, paid, movie_plan.movie_plan_id, date_time, price as ticket_price, duration_minutes, movie_plan.movie_id,\n" +
                "movie.movie_details_id, type as movie_type, movie_details.name as movie_name, language, movie_plan.theater_room_id,\n" +
                "theater_room.name as theater_room_name from booking\n" +
                "INNER JOIN movie_plan\n" +
                "on booking.movie_plan_id = movie_plan.movie_plan_id\n" +
                "INNER JOIN theater_room\n" +
                "ON (movie_plan.theater_room_id = theater_room.theater_room_id)\n" +
                "INNER JOIN movie\n" +
                "ON (movie_plan.movie_id = movie.movie_id)\n" +
                "INNER JOIN movie_details\n" +
                "ON (movie.movie_details_id = movie_details.movie_details_id)\n" +
                "WHERE booking_id = ?";

        SqlRowSet rs = jdbc.queryForRowSet(query, id);
        Booking booking = new Booking();

        try {
            if (rs.first()) {
                getBooking(rs,booking);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if(rs.getRow()==0)
            return null;
        return booking;
    }
    public Booking findBookingByConfirmationCode(String email_phone, String confirmation_code) {
        String query = "SELECT booking.booking_id, booking.movie_plan_id, phone_number, email, confirmation_code, paid, movie_plan.movie_plan_id, date_time, price as ticket_price, duration_minutes, movie_plan.movie_id,\n" +
                "movie.movie_details_id, type as movie_type, movie_details.name as movie_name, language, movie_plan.theater_room_id,\n" +
                "theater_room.name as theater_room_name from booking\n" +
                "INNER JOIN movie_plan\n" +
                "on booking.movie_plan_id = movie_plan.movie_plan_id\n" +
                "INNER JOIN theater_room\n" +
                "ON (movie_plan.theater_room_id = theater_room.theater_room_id)\n" +
                "INNER JOIN movie\n" +
                "ON (movie_plan.movie_id = movie.movie_id)\n" +
                "INNER JOIN movie_details\n" +
                "ON (movie.movie_details_id = movie_details.movie_details_id)\n" +
                "WHERE ((email = ? or phone_number = ?) and confirmation_code = ?)";

        SqlRowSet rs = jdbc.queryForRowSet(query, email_phone, email_phone, confirmation_code);
        Booking booking = new Booking();

        try {
            if (rs.first()) {
                getBooking(rs,booking);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if(rs.getRow()==0)
            return null;
        return booking;
    }

    private void getBooking(SqlRowSet rs, Booking booking) {
        booking.setBooking_id(rs.getInt("booking_id"));
        booking.setPhone_number(rs.getString("phone_number"));
        booking.setEmail(rs.getString("email"));
        booking.setConfirmation_code(rs.getString("confirmation_code"));
        booking.setPaid(rs.getBoolean("paid"));
        booking.setMovie_plan_id(rs.getInt("movie_plan_id"));


        MoviePlan moviePlan = new MoviePlan();
        TheaterRoom theaterRoom = new TheaterRoom();
        Movie movie = new Movie();
        MovieDetails movieDetails = new MovieDetails();
        List<Ticket> ticketList = new ArrayList<>();

        moviePlan.setMovie_plan_id(rs.getInt("movie_plan_id"));
        moviePlan.setMovie_id(rs.getInt("movie_id"));
        moviePlan.setTheater_room_id(rs.getInt("theater_room_id"));
        moviePlan.setDate_time(rs.getTimestamp("date_time").toLocalDateTime());
        moviePlan.setPrice(rs.getDouble("price"));

        movie.setMovie_id(rs.getInt("movie_id"));
        movie.setMovie_details_id(rs.getInt("movie_details_id"));
        movie.setType(rs.getBoolean("type"));

        movieDetails.setMovie_details_id(rs.getInt("movie_details_id"));
        movieDetails.setName(rs.getString("name"));
        movieDetails.setDuration_minutes(rs.getInt("duration_minutes"));
        movieDetails.setLanguage(rs.getString("language"));

        theaterRoom.setTheater_room_id(rs.getInt("theater_room_id"));
        theaterRoom.setName(rs.getString("theater_room_name"));

        ticketList = ticketRepo.findTicketsByBooking(rs.getInt("booking_id"));
        movie.setMovieDetails(movieDetails);
        moviePlan.setMovie(movie);
        moviePlan.setTheaterRoom(theaterRoom);
        booking.setTicketList(ticketList);
        booking.setMoviePlan(moviePlan);
        booking.setTotalPrice(booking.getBookingTotalPrice());

    }

    private List<Booking> getBookingList(List<Booking> bookingsList, SqlRowSet rs) {
        try {
            while (rs.next()) {
                Booking booking = new Booking();
                getBooking(rs, booking);
                bookingsList.add(booking);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookingsList;
    }

    public Booking insertBooking(Booking booking)
    {
        PreparedStatementCreator psc = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO booking VALUES(null, ?, ?, ?, ?, ?)", new String[]{"id"});
                ps.setInt(1, booking.getMovie_plan_id());
                ps.setString(2, booking.getPhone_number());
                ps.setString(3, booking.getEmail());
                ps.setString(4, booking.getConfirmation_code());
                ps.setBoolean(5, booking.getPaid());
                return ps;
            }
        };


        KeyHolder id = new GeneratedKeyHolder();
        jdbc.update(psc, id);
        booking.setBooking_id(id.getKey().intValue());
        return booking;
    }

    public void deleteBooking(int id) {
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement("DELETE from booking where booking_id = ?");
                ps.setInt(1, id);
                return ps;
            }
        };
        jdbc.update(psc);
    }

    public void deleteBookingByConfirmationCode(String phoneNumber, String confirmation_code) {
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement("DELETE from booking where confirmation_code = ? and phone_number = ?");
                ps.setString(1, confirmation_code);
                ps.setString(2, phoneNumber);
                return ps;
            }
        };
        jdbc.update(psc);
    }

    public void editBooking(Booking booking) {
        PreparedStatementCreator psc = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement("UPDATE booking set phone_number = ? , email = ? , paid = ? WHERE booking_id = ?");
                ps.setString(1, booking.getPhone_number());
                ps.setString(2, booking.getEmail());
                ps.setBoolean(3, booking.getPaid());
                ps.setInt(4, booking.getBooking_id());
                return ps;
            }
        };

        jdbc.update(psc);
    }
}
