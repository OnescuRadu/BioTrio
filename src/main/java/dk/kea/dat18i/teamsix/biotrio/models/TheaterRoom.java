package dk.kea.dat18i.teamsix.biotrio.models;

public class TheaterRoom {
    private int theater_room_id;
    private String name;
    private int rows_no;
    private int columns_no;
    private Boolean capability_3d;

    public TheaterRoom(int theater_room_id, String name, int rows_no, int columns_no, Boolean capability_3d) {
        this.theater_room_id = theater_room_id;
        this.name = name;
        this.rows_no = rows_no;
        this.columns_no = columns_no;
        this.capability_3d = capability_3d;
    }

    public TheaterRoom(){

    }

    public int getTheater_room_id() {
        return theater_room_id;
    }

    public void setTheater_room_id(int theater_room_id) {
        this.theater_room_id = theater_room_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRows_no() {
        return rows_no;
    }

    public void setRows_no(int rows_no) {
        this.rows_no = rows_no;
    }

    public int getColumns_no() {
        return columns_no;
    }

    public void setColumns_no(int columns_no) {
        this.columns_no = columns_no;
    }

    public Boolean getCapability_3d() {
        return capability_3d;
    }

    public void setCapability_3d(Boolean capability_3d) {
        this.capability_3d = capability_3d;
    }

    @Override
    public String toString() {
        return "TheaterRoom{" +
                "theater_room_id=" + theater_room_id +
                ", name='" + name + '\'' +
                ", rows_no=" + rows_no +
                ", columns_no=" + columns_no +
                ", capability_3d=" + capability_3d +
                '}';
    }
}
