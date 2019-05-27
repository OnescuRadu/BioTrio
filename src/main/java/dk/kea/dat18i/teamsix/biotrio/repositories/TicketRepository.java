package dk.kea.dat18i.teamsix.biotrio.repositories;

import dk.kea.dat18i.teamsix.biotrio.models.Ticket;
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
 * Represents the repository for the ticket
 */
@Repository
public class TicketRepository {

    @Autowired
    private JdbcTemplate jdbc;


    /**
     * Method finds all the tickets in the database
     *
     * @return a list of Ticket objects
     */
    public List<Ticket> findAllTickets() {
        String query = "SELECT * from ticket";

        List<Ticket> ticketList = new ArrayList<>();
        SqlRowSet rs = jdbc.queryForRowSet(query);
        return getTicketList(ticketList, rs);
    }

    /**
     * Method finds the ticket that has the given booking id in the database
     *
     * @param id represents the booking id
     * @return a list of Ticket objects
     */
    public List<Ticket> findTicketsByBooking(int id) {
        String query = "SELECT * from ticket WHERE booking_id = ? ;";
        SqlRowSet rs = jdbc.queryForRowSet(query, id);
        List<Ticket> ticketList = new ArrayList<>();

        return getTicketList(ticketList, rs);
    }

    /**
     * Method finds the ticket that has the given movie plan id in the database
     *
     * @param id represents the movie plan id
     * @return a list of Ticket objects
     */
    public List<Ticket> findTicketsByMoviePlan(int id) {
        String query = "select ticket.ticket_id, seat_number, ticket.booking_id from ticket\n" +
                "inner join booking\n" +
                "on booking.booking_id = ticket.booking_id\n" +
                "where movie_plan_id = ? ;";
        SqlRowSet rs = jdbc.queryForRowSet(query, id);
        List<Ticket> ticketList = new ArrayList<>();

        return getTicketList(ticketList, rs);
    }

    /**
     * Method finds the seats assigned to the tickets that have a given booking id in the database
     *
     * @param id booking id
     * @return a list of Ticket objects containing only seat numbers
     */
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


    /**
     * Method sets the variables inside the given Ticket object using the given SqlRowSet
     * This method is used for avoiding writing the same setters in every other method and having duplicate code
     *
     * @param rs rs represents a RowSet object containing a set of rows
     * @param ticket represents the given Ticket object
     */
    private void getTicket(SqlRowSet rs, Ticket ticket) {
        ticket.setTicket_id(rs.getInt("ticket_id"));
        ticket.setSeat_number(rs.getString("seat_number"));
        ticket.setBooking_id(rs.getInt("booking_id"));
    }

    /**
     * Method initializes Ticket objects using the given RowSet and adds them to a list that is returned in the end
     *
     * @param ticketList represents a list of Ticket objects
     * @param rs represents a RowSet object containing a set of rows
     * @return a list of Ticket objects
     */
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

    /**
     * Method inserts in the database the given Ticket object
     *
     * @param ticket represents the Ticket object
     */
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

    /**
     * Method checks if the seats selected are already booked for the given movie plan or not
     *
     * @param selectedSeats
     * @param movie_plan_id represents the movie plan's id (is used to identify the movie plan)
     * @return FALSE if the selected seats are already booked or TRUE if they are not
     */
    public boolean checkIfSeatsAreAvailable(List<Ticket> selectedSeats, int movie_plan_id) {
        //Initialize a list of Ticket objects and populating it with tickets that are already booking for the given movie plan.
        List<Ticket> reservedSeats = findTicketsByMoviePlan(movie_plan_id);

        //Iterate the list of already booked seats
        for (int i = 0; i < reservedSeats.size(); i++) {
            //Iterates the list of selected seats
            for (int j = 0; j < selectedSeats.size(); j++) {
                //If one of the already booked seats is the same of one the selected seats it returns false
                if (reservedSeats.get(i).getSeat_number().equals(selectedSeats.get(j).getSeat_number()))
                    return false;
            }
        }
        //If none of the selected seats are already booked it returns true meaning that the seats are available
        return true;
    }
}
