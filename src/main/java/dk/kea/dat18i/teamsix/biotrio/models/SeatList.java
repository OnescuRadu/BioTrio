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

    public void formatSeatList(SeatList seatList)
    {
        for(int i=0; i<seatList.getSelectedSeats().length; i++)
        {
            String seat = seatList.getSelectedSeats()[i];

            String[] split = seat.split("-");
            String row = String.format("%02d", Integer.parseInt(split[0]));
            String col = String.format("%02d", Integer.parseInt(split[1]));

            seatList.getSelectedSeats()[i] = row + "-" + col;
        }
    }
}