package dk.kea.dat18i.teamsix.biotrio.models;

/**
 * Represents the ticket
 */
public class Ticket {
    private int ticket_id;
    private String seat_number;
    private int booking_id;

    /**
     * @param ticket_id The ticket's id
     * @param seat_number The ticket's seat number
     * @param booking_id The booking's id where the ticket belongs to
     */
    public Ticket(int ticket_id, String seat_number, int booking_id) {
        this.ticket_id = ticket_id;
        this.seat_number = seat_number;
        this.booking_id = booking_id;
    }

    /**
     * The default constructor for the ticket
     */
    public Ticket()
    {

    }

    /**
     * @return an int representing the ticket id
     */
    public int getTicket_id() {
        return ticket_id;
    }

    /**
     * @param ticket_id represents the ticket id
     */
    public void setTicket_id(int ticket_id) {
        this.ticket_id = ticket_id;
    }

    /**
     * @return a string containing the seat number
     */
    public String getSeat_number() {
        return seat_number;
    }

    /**
     * @param seat_number represents the seat number
     */
    public void setSeat_number(String seat_number) {
        this.seat_number = seat_number;
    }

    /**
     * @return an int representing the booking id
     */
    public int getBooking_id() {
        return booking_id;
    }

    /**
     * @param booking_id represents the booking id
     */
    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    /**
     * @return a string with the ticket object's data
     */
    @Override
    public String toString() {
        return "Ticket{" +
                "ticket_id=" + ticket_id +
                ", seat_number='" + seat_number + '\'' +
                ", booking_id=" + booking_id +
                '}';
    }
}
