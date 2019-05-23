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

    /**
     * @param movie_plan_id The movie plan's id
     * @param movie_id The movie's id
     * @param movie The Movie object
     * @param theater_room_id The theater room's id
     * @param theaterRoom The TheaterRoom object
     * @param date_time The date and the time the movie will be planned
     * @param price The price per seat
     */
    public MoviePlan(int movie_plan_id, int movie_id, Movie movie, int theater_room_id, TheaterRoom theaterRoom, LocalDateTime date_time, double price) {
        this.movie_plan_id = movie_plan_id;
        this.movie_id = movie_id;
        this.movie = movie;
        this.theater_room_id = theater_room_id;
        this.theaterRoom = theaterRoom;
        this.date_time = date_time;
        this.price = price;
    }

    /**
     * The default constructor for movie plan
     */
    public MoviePlan(){

    }

    /**
     * @return an movie object representing which movie is planned
     */
    public Movie getMovie() {
        return movie;
    }

    /**
     * @param movie represents the movie which is planned
     */
    public void setMovie(Movie movie) {
        this.movie = movie;
    }


    /**
     * @return an int representing the movie plan's id
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
     * @return an int representing the movie's id
     */
    public int getMovie_id() {
        return movie_id;
    }

    /**
     * @param movie_id represents the movie's id
     */
    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    /**
     * @return an int representing the theater room's id
     */
    public int getTheater_room_id() {
        return theater_room_id;
    }

    /**
     * @param theater_room_id represents the theater room's id
     */
    public void setTheater_room_id(int theater_room_id) {
        this.theater_room_id = theater_room_id;
    }

    /**
     * @return a LocalDateTime containing the date and the time when the movie is planned
     */
    public LocalDateTime getDate_time() {
        return date_time;
    }

    /**
     * @param date_time represents the date and the time when the movie is planned
     */
    public void setDate_time(LocalDateTime date_time) {
        this.date_time = date_time;
    }

    /**
     * @return a double representing the price per seat
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price represents the price per seat
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return a TheaterRoom object representing where the movie is planned
     */
    public TheaterRoom getTheaterRoom() {
        return theaterRoom;
    }

    /**
     * @param theaterRoom represents the theater room where the movie is planned
     */
    public void setTheaterRoom(TheaterRoom theaterRoom) {
        this.theaterRoom = theaterRoom;
    }

    /**
     * @return a string with the movie plan object's data
     */
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
