package dk.kea.dat18i.teamsix.biotrio.models;

import java.util.Arrays;
import java.util.List;


/**
 * Represents a booking
 */
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

    /**
     * @param booking_id The booking's id
     * @param movie_plan_id The movie plan's id.
     * @param moviePlan The MoviePlan object
     * @param phone_number The booking's phone number
     * @param email The booking's email
     * @param confirmation_code The booking's confirmation code
     * @param paid The booking's parameter that stores if the booking is paid or not (TRUE or FALSE)
     * @param totalPrice The booking's total price
     * @param ticketList The booking's list of tickets
     */
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

    /**
     * The default constructor for booking
     */
    public Booking()
    {
    }

    /**
     * @return an array of strings containing the seats
     */
    public String[] getSeatList() {
        return seatList;
    }

    /**
     * @param seatList The array of strings containing the seats
     */
    public void setSeatList(String[] seatList) {
        this.seatList = seatList;
    }

    /**
     * @return the total price
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * @param totalPrice of type double representing the total price
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * @return a list containing Ticket objects
     */
    public List<Ticket> getTicketList() {
        return ticketList;
    }

    /**
     * @param ticketList a list containing Ticket objects
     */
    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }


    /**
     * @return the booking's id
     */
    public int getBooking_id() {
        return booking_id;
    }

    /**
     * @param booking_id represents the booking's id
     */
    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    /**
     * @return the id of the movie plan
     */
    public int getMovie_plan_id() {
        return movie_plan_id;
    }

    /**
     * @param movie_plan_id represents the movie plan's id
     */
    public void setMovie_plan_id(int movie_plan_id) {
        this.movie_plan_id = movie_plan_id;
    }

    /**
     * @return an object of type MoviePlan
     */
    public MoviePlan getMoviePlan() {
        return moviePlan;
    }

    /**
     * @param moviePlan represents the MoviePlan object
     */
    public void setMoviePlan(MoviePlan moviePlan) {
        this.moviePlan = moviePlan;
    }

    /**
     * @return a string containing booking's phone number
     */
    public String getPhone_number() {
        return phone_number;
    }

    /**
     * @param phone_number the booking's phone number
     */
    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    /**
     * @return a string containing booking's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email represents the booking's email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return a string containing booking's confirmation code
     */
    public String getConfirmation_code() {
        return confirmation_code;
    }

    /**
     * @param confirmation_code represents the booking's confirmation code
     */
    public void setConfirmation_code(String confirmation_code) {
        this.confirmation_code = confirmation_code;
    }

    /**
     * @return a boolean value representing the paid status of the booking (TRUE or FALSE)
     */
    public Boolean getPaid() {
        return paid;
    }

    /**
     * @param paid is a variable of type boolean and represents the paid status of the booking (TRUE or FALSE)
     */
    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    /**
     * Method calculates the total price of the booking, based on the number of tickets and their price.
     * @return a double representing the total price of the booking
     */
    public double getBookingTotalPrice(){
        double price = 0;
        int i=0;
        while (i<ticketList.size()) {
            price += moviePlan.getPrice();
            i++;
        }
        return price;
    }

    /**
     * Method populates the 'seats', resulting in a 2D array containing the booked seats based on the list of Ticket objects
     * @param seats a boolean two dimensional array
     * @param rows the number of theater room's rows
     * @param cols the number of theater room's columns
     * @param reservedSeats a list of Ticket objects containing all the booked seats
     */
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

    /**
     * Method changes the format of the seats from the array of strings, making them having two digits
     * Eg. '1-1' will be formatted like '01-01'
     * @param seatList represents a string array containing the seats
     */
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

    /**
     * @return a string with the booking object's data
     */
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
