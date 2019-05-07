package dk.kea.dat18i.teamsix.biotrio;

public class Movie {
    private int movie_id;
    private int movie_details_id;
    private boolean type;
    private boolean available;

    public Movie(int movie_id, int movie_details_id, boolean type, boolean available) {
        this.movie_id = movie_id;
        this.movie_details_id = movie_details_id;
        this.type = type;
        this.available = available;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public int getMovie_details_id() {
        return movie_details_id;
    }

    public void setMovie_details_id(int movie_details_id) {
        this.movie_details_id = movie_details_id;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "movie_id=" + movie_id +
                ", movie_details_id=" + movie_details_id +
                ", type=" + type +
                ", available=" + available +
                '}';
    }
}
