package dk.kea.dat18i.teamsix.biotrio.models;

import java.time.LocalDate;

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

    public int getMovie_details_id() {
        return movie_details_id;
    }

    public void setMovie_details_id(int movie_details_id) {
        this.movie_details_id = movie_details_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public LocalDate getRelease_date() {
        return release_date;
    }

    public void setRelease_date(LocalDate release_date) {
        this.release_date = release_date;
    }

    public int getDuration_minutes() {
        return duration_minutes;
    }

    public void setDuration_minutes(int duration_minutes) {
        this.duration_minutes = duration_minutes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

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

    public MovieDetails() {

    }

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
