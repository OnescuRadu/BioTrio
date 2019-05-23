package dk.kea.dat18i.teamsix.biotrio.models;

public class TheaterRoom {
    private int theater_room_id;
    private String name;
    private int rows_no;
    private int columns_no;
    private Boolean capability_3d;

    /**
     * @param theater_room_id The theater room's id
     * @param name The theater room's name
     * @param rows_no The theater room's number of rows
     * @param columns_no The theater room's number of columns
     * @param capability_3d The theater room's 3D capability (2D only = false and 2D&3D = true)
     */
    public TheaterRoom(int theater_room_id, String name, int rows_no, int columns_no, Boolean capability_3d) {
        this.theater_room_id = theater_room_id;
        this.name = name;
        this.rows_no = rows_no;
        this.columns_no = columns_no;
        this.capability_3d = capability_3d;
    }

    /**
     * The default constructor for the theater room
     */
    public TheaterRoom(){

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
     * @return a string containing the name of the theater room
     */
    public String getName() {
        return name;
    }

    /**
     * @param name represents the theater room's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return an int representing the theater room's number of rows
     */
    public int getRows_no() {
        return rows_no;
    }

    /**
     * @param rows_no represents the theater room's number of rows
     */
    public void setRows_no(int rows_no) {
        this.rows_no = rows_no;
    }

    /**
     * @return an int representing the theater room's number of columns
     */
    public int getColumns_no() {
        return columns_no;
    }

    /**
     * @param columns_no represents the theater room's number of columns
     */
    public void setColumns_no(int columns_no) {
        this.columns_no = columns_no;
    }

    /**
     * @return a boolean representing the 3D capability of the theater room ((2D only = false and 2D&3D = true)
     */
    public Boolean getCapability_3d() {
        return capability_3d;
    }

    /**
     * @param capability_3d represents the 3D capability of the theater room
     */
    public void setCapability_3d(Boolean capability_3d) {
        this.capability_3d = capability_3d;
    }

    /**
     * @return a string with the theater room object's data
     */
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
