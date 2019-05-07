package dk.kea.dat18i.teamsix.biotrio.models;


import java.time.LocalDate;
import java.time.LocalTime;

public class MoviePlan {
    private int movie_plan_id;
    private int movie_id;
    private int theater_room_id;
    private LocalDate date;
    private LocalTime start_time;
    private double price;
    private TheaterRoom theaterRoom;

    public MoviePlan(int movie_plan_id, int movie_id, int theater_room_id, LocalDate date, LocalTime start_time, double price, TheaterRoom theaterRoom) {
        this.movie_plan_id = movie_plan_id;
        this.movie_id = movie_id;
        this.theater_room_id = theater_room_id;
        this.date = date;
        this.start_time = start_time;
        this.price = price;
        this.theaterRoom = theaterRoom;
    }

    public MoviePlan(){

    }

    public int getMovie_plan_id() {
        return movie_plan_id;
    }

    public void setMovie_plan_id(int movie_plan_id) {
        this.movie_plan_id = movie_plan_id;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public int getTheater_room_id() {
        return theater_room_id;
    }

    public void setTheater_room_id(int theater_room_id) {
        this.theater_room_id = theater_room_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStart_time() {
        return start_time;
    }

    public void setStart_time(LocalTime start_time) {
        this.start_time = start_time;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public TheaterRoom getTheaterRoom() {
        return theaterRoom;
    }

    public void setTheaterRoom(TheaterRoom theaterRoom) {
        this.theaterRoom = theaterRoom;
    }

    @Override
    public String toString() {
        return "MoviePlan{" +
                "movie_plan_id=" + movie_plan_id +
                ", movie_id=" + movie_id +
                ", theater_room_id=" + theater_room_id +
                ", date=" + date +
                ", start_time=" + start_time +
                ", price=" + price +
                '}';
    }
}
