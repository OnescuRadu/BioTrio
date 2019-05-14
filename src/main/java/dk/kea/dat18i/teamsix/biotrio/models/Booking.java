package dk.kea.dat18i.teamsix.biotrio.models;
public class Booking {
    private int booking_id;
    private int movie_plan_id;
    private MoviePlan moviePlan;
    private String phone_number;
    private String email;
    private String confirmation_code;
    private Boolean paid;



    public Booking(int booking_id, int movie_plan_id, MoviePlan moviePlan, String phone_number, String email, String confirmation_code, Boolean paid) {
        this.booking_id = booking_id;
        this.movie_plan_id = movie_plan_id;
        this.moviePlan = moviePlan;
        this.phone_number = phone_number;
        this.email = email;
        this.confirmation_code = confirmation_code;
        this.paid = paid;
    }

    public Booking()
    {
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

    @Override
    public String toString() {
        return "Booking{" +
                "booking_id=" + booking_id +
                ", movie_plan_id=" + movie_plan_id +
                ", moviePlan=" + moviePlan +
                ", phone_number='" + phone_number + '\'' +
                ", email='" + email + '\'' +
                ", confirmation_code='" + confirmation_code + '\'' +
                ", paid=" + paid +
                '}';
    }
}
