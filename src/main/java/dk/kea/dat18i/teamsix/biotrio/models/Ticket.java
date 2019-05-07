package dk.kea.dat18i.teamsix.biotrio;

public class Ticket {
    private int ticket_id;
    private String seat_number;
    private int booking_id;

    public Ticket(int ticket_id, String seat_number, int booking_id) {
        this.ticket_id = ticket_id;
        this.seat_number = seat_number;
        this.booking_id = booking_id;
    }

    public int getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(int ticket_id) {
        this.ticket_id = ticket_id;
    }

    public String getSeat_number() {
        return seat_number;
    }

    public void setSeat_number(String seat_number) {
        this.seat_number = seat_number;
    }

    public int getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticket_id=" + ticket_id +
                ", seat_number='" + seat_number + '\'' +
                ", booking_id=" + booking_id +
                '}';
    }
}
