package dk.kea.dat18i.teamsix.biotrio.models;

import java.util.Arrays;

public class SeatList {

    private String[] selectedSeats;

    public SeatList(String[] selectedSeats) {
        this.selectedSeats = selectedSeats;
    }

    public SeatList(){

    }

    public String[] getSelectedSeats() {
        return selectedSeats;
    }

    public void setSelectedSeats(String[] selectedSeats) {
        this.selectedSeats = selectedSeats;
    }

    @Override
    public String toString() {
        return "SeatList{" +
                "selectedSeats=" + Arrays.toString(selectedSeats) +
                '}';
    }
}