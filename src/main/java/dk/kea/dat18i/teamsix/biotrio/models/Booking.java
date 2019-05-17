package dk.kea.dat18i.teamsix.biotrio.models;

import java.util.Arrays;
import java.util.List;

public class Booking {
    private int booking_id;
    private int movie_plan_id;
    private MoviePlan moviePlan;
    private String phone_number;
    private String email;
    private String confirmation_code;
    private double totalPrice;
    private Boolean paid = false;
    private List<Ticket> ticketList;
    private String[] seatList;

    public Booking(int booking_id, int movie_plan_id, MoviePlan moviePlan, String phone_number, String email, String confirmation_code, Boolean paid, Double totalPrice, List<Ticket> ticketList) {
        this.booking_id = booking_id;
        this.movie_plan_id = movie_plan_id;
        this.moviePlan = moviePlan;
        this.phone_number = phone_number;
        this.email = email;
        this.confirmation_code = confirmation_code;
        this.totalPrice = totalPrice;
        this.paid = paid;
        this.ticketList = ticketList;
    }

    public Booking()
    {
    }

    public String[] getSeatList() {
        return seatList;
    }

    public void setSeatList(String[] seatList) {
        this.seatList = seatList;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }



    public int getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    public int getMovie_plan_id() {
        return movie_plan_id;
    }

    public void setMovie_plan_id(int movie_plan_id) {
        this.movie_plan_id = movie_plan_id;
    }

    public MoviePlan getMoviePlan() {
        return moviePlan;
    }

    public void setMoviePlan(MoviePlan moviePlan) {
        this.moviePlan = moviePlan;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmation_code() {
        return confirmation_code;
    }

    public void setConfirmation_code(String confirmation_code) {
        this.confirmation_code = confirmation_code;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public double getBookingTotalPrice(){
        double price = 0;
        int i=0;
        while (i<ticketList.size()) {
            price += moviePlan.getPrice();
            i++;
        }
        return price;
    }

    public static void findBookedSeats(boolean[][] seats, int rows, int cols, List<Ticket> reservedSeats) {
        for(int i = 0; i < rows; i++)
        {
            for(int j=0; j< cols; j++)
            {
                for (Ticket reservedSeat : reservedSeats) {
                    String seat = String.format("%02d", i + 1) + "-" + String.format("%02d", j + 1);
                    if (reservedSeat.getSeat_number().equals(seat)) {
                        seats[i][j] = true;
                    }
                }
            }
        }
    }

    public static void formatSeatList(String[] seatList)
    {
        for(int i=0; i<seatList.length; i++)
        {
            String seat = seatList[i];

            String[] split = seat.split("-");
            String row = String.format("%02d", Integer.parseInt(split[0]));
            String col = String.format("%02d", Integer.parseInt(split[1]));

            seatList[i] = row + "-" + col;
        }
    }

    @Override
    public String toString() {
        return "Booking{" +
                "booking_id=" + booking_id +
                ", movie_plan_id=" + movie_plan_id +
                ", moviePlan=" + moviePlan +
                ", phone_number='" + phone_number + '\'' +
                ", email='" + email + '\'' +
                ", confirmation_code='" + confirmation_code + '\'' +
                ", totalPrice=" + totalPrice +
                ", paid=" + paid +
                ", ticketList=" + ticketList +
                ", seatList=" + Arrays.toString(seatList) +
                '}';
    }
}
