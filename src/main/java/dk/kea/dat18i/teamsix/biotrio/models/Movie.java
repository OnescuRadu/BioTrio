package dk.kea.dat18i.teamsix.biotrio.models;

/**
 * Represents a movie
 */
public class Movie {
    private int movie_id;
    private int movie_details_id;
    private MovieDetails movieDetails;
    private boolean type;

    /**
     * @param movie_id The movie's id
     * @param movie_details_id The movie details' id
     * @param movieDetails The MovieDetails object
     * @param type The movie's type (false = 2D, true = 3D)
     */
    public Movie(int movie_id, int movie_details_id, MovieDetails movieDetails, boolean type) {
        this.movie_id = movie_id;
        this.movie_details_id = movie_details_id;
        this.movieDetails = movieDetails;
        this.type = type;
    }

    /**
     * The default constructor for movie
     */
    public Movie() {
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
     * @return an int representing the movie_details_id
     */
    public int getMovie_details_id() {
        return movie_details_id;
    }

    /**
     * @param movie_details_id represents the movie details' id
     */
    public void setMovie_details_id(int movie_details_id) {
        this.movie_details_id = movie_details_id;
    }

    /**
     * @return a boolean representing the movie's type (2D or 3D)
     */
    public boolean isType() {
        return type;
    }

    /**
     * @param type represents the movie's type (false = 2D or true = 3D)
     */
    public void setType(boolean type) {
        this.type = type;
    }

    /**
     * @return a boolean representing the movie's type (2D or 3D)
     */
    public Boolean getType(){ return type; }

    /**
     * @return a MovieDetails object
     */
    public MovieDetails getMovieDetails() {
        return movieDetails;
    }

    /**
     * @param movieDetails represents a MovieDetails object
     */
    public void setMovieDetails(MovieDetails movieDetails) {
        this.movieDetails = movieDetails;
    }


    /**
     * @return a string with the movie object's data
     */
    @Override
    public String toString() {
        return "Movie{" +
                "movie_id=" + movie_id +
                ", movie_details_id=" + movie_details_id +
                ", type=" + type +
                '}';
    }
}
