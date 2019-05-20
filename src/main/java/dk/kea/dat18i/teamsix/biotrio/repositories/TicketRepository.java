package dk.kea.dat18i.teamsix.biotrio.repositories;

import dk.kea.dat18i.teamsix.biotrio.models.Ticket;
import dk.kea.dat18i.teamsix.biotrio.models.User;
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
public class TicketRepository {
    @Autowired
    private JdbcTemplate jdbc;

    public List<Ticket> findAllTickets() {
        String query = "SELECT * from ticket";

        List<Ticket> ticketList = new ArrayList<>();
        SqlRowSet rs = jdbc.queryForRowSet(query);
        return getTicketList(ticketList, rs);
    }

    public List<Ticket> findTicketsByBooking(int id) {
        String query = "SELECT * from ticket WHERE booking_id = ? ;";
        SqlRowSet rs = jdbc.queryForRowSet(query, id);
        List<Ticket> ticketList = new ArrayList<>();

        return getTicketList(ticketList, rs);
    }

    public List<Ticket> findTicketsByMoviePlan(int id) {
        String query = "select ticket.ticket_id, seat_number, ticket.booking_id from ticket\n" +
                "inner join booking\n" +
                "on booking.booking_id = ticket.booking_id\n" +
                "where movie_plan_id = ? ;";
        SqlRowSet rs = jdbc.queryForRowSet(query, id);
        List<Ticket> ticketList = new ArrayList<>();

        return getTicketList(ticketList, rs);
    }

    public List<Ticket> findSeatsByBooking(int id) {
        String query = "select seat_number from ticket\n" +
                "inner join booking\n" +
                "on ticket.booking_id = booking.booking_id\n" +
                "inner join movie_plan\n" +
                "on movie_plan.movie_plan_id = booking.movie_plan_id\n" +
                "where movie_plan.movie_plan_id = ? ;";

        SqlRowSet rs = jdbc.queryForRowSet(query, id);

        List<Ticket> ticketList = new ArrayList<>();

        try {

            while (rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setSeat_number(rs.getString("seat_number"));
                ticketList.add(ticket);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ticketList;
    }


    private void getTicket(SqlRowSet rs, Ticket ticket) {
        ticket.setTicket_id(rs.getInt("ticket_id"));
        ticket.setSeat_number(rs.getString("seat_number"));
        ticket.setBooking_id(rs.getInt("booking_id"));
    }

    private List<Ticket> getTicketList(List<Ticket> ticketList, SqlRowSet rs) {
        try {

            while (rs.next()) {
                Ticket ticket = new Ticket();
                getTicket(rs, ticket);
                ticketList.add(ticket);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ticketList;
    }

    public void insertTicket(Ticket ticket) {
        PreparedStatementCreator psc = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO ticket values(null, ?, ?)");
                ps.setString(1, ticket.getSeat_number());
                ps.setInt(2, ticket.getBooking_id());
                return ps;
            }
        };

        jdbc.update(psc);
    }

    public boolean checkIfSeatsAreAvailable(List<Ticket> selectedSeats, int movie_plan_id)
    {
        List<Ticket> reservedSeats = findTicketsByMoviePlan(movie_plan_id);
        for(int i=0; i<reservedSeats.size(); i++)
        {
            for(int j=0; j<selectedSeats.size(); j++)
            {
                if(reservedSeats.get(i).getSeat_number().equals(selectedSeats.get(j).getSeat_number()))
                    return false;
            }
        }
        return true;
    }
}
