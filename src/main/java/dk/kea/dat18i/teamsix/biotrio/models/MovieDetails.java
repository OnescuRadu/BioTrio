package dk.kea.dat18i.teamsix.biotrio.models;

import java.time.LocalDate;

/**
 * Represents the movie details
 */
public class MovieDetails {
    private int movie_details_id;
    private String name;
    private String genre;
    private LocalDate release_date;
    private int duration_minutes;
    private String description;
    private String language;
    private String poster;
    private String trailer;

    /**
     * @param movie_details_id The movie details id
     * @param name The movie's name
     * @param genre The movie's genre
     * @param release_date The movie's release date
     * @param duration_minutes The movie's duration in minutes
     * @param description The movie's description
     * @param language The movie's language
     * @param poster The movie's poster
     * @param trailer The movie's trailer
     */
    public MovieDetails(int movie_details_id, String name, String genre, LocalDate release_date, int duration_minutes, String description, String language, String poster, String trailer) {
        this.movie_details_id = movie_details_id;
        this.name = name;
        this.genre = genre;
        this.release_date = release_date;
        this.duration_minutes = duration_minutes;
        this.description = description;
        this.language = language;
        this.poster = poster;
        this.trailer = trailer;

    }

    /**
     * The default constructor for movie details
     */
    public MovieDetails() {

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
     * @return a string containing the name of the movie
     */
    public String getName() {
        return name;
    }

    /**
     * @param name represents the movie's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return a string containing the genre of the movie
     */
    public String getGenre() {
        return genre;
    }

    /**
     * @param genre represents the movie's genre
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * @return a LocalDate containing the release date of the movie
     */
    public LocalDate getRelease_date() {
        return release_date;
    }

    /**
     * @param release_date represents the release date of the movie
     */
    public void setRelease_date(LocalDate release_date) {
        this.release_date = release_date;
    }

    /**
     * @return an int representing the duration of the movie in minutes
     */
    public int getDuration_minutes() {
        return duration_minutes;
    }

    /**
     * @param duration_minutes represents the duration of the movie
     */
    public void setDuration_minutes(int duration_minutes) {
        this.duration_minutes = duration_minutes;
    }

    /**
     * @return a string containing the description of the movie
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description represents the description of the movie
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return a string containing the movie's language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @param language represents the movie's language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * @return a string containing the movie's poster's image link
     */
    public String getPoster() {
        return poster;
    }

    /**
     * @param poster represents the movie's poster's image link
     */
    public void setPoster(String poster) {
        this.poster = poster;
    }

    /**
     * @return a string containing the movie's trailer's video link
     */
    public String getTrailer() {
        return trailer;
    }

    /**
     * @param trailer represents the movie's trailer's video link
     */
    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }


    /**
     * @return a string with the movie details object's data
     */
    @Override
    public String toString() {
        return "MovieDetails{" +
                "movie_details_id=" + movie_details_id +
                ", name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ", release_date=" + release_date +
                ", duration_minutes=" + duration_minutes +
                ", description='" + description + '\'' +
                ", language='" + language + '\'' +
                ", poster='" + poster + '\'' +
                ", trailer='" + trailer + '\'' +
                '}';
    }
}
