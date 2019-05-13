package dk.kea.dat18i.teamsix.biotrio.models;


import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class MoviePlan {
    private int movie_plan_id;
    private int movie_id;
    private Movie movie;
    private int theater_room_id;
    private TheaterRoom theaterRoom;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime date_time;
    private double price;

    public MoviePlan(int movie_plan_id, int movie_id, Movie movie, int theater_room_id, TheaterRoom theaterRoom, LocalDateTime date_time, double price) {
        this.movie_plan_id = movie_plan_id;
        this.movie_id = movie_id;
        this.movie = movie;
        this.theater_room_id = theater_room_id;
        this.theaterRoom = theaterRoom;
        this.date_time = date_time;
        this.price = price;
    }

    public MoviePlan(){

    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
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

    public LocalDateTime getDate_time() {
        return date_time;
    }

    public void setDate_time(LocalDateTime date_time) {
        this.date_time = date_time;
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
                ", movie=" + movie +
                ", theater_room_id=" + theater_room_id +
                ", theaterRoom=" + theaterRoom +
                ", date_time=" + date_time +
                ", price=" + price +
                '}';
    }
}
