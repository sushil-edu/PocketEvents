package kestone.com.kestone.MODEL.HallDetails;

public class HallDetails {
    private static String SeatingStyle = "", HallCapacity = "", HallArea = "";

    public static String getSeatingStyle() {
        return SeatingStyle;
    }

    public static void setSeatingStyle(String seatingStyle) {
        SeatingStyle = seatingStyle;
    }

    public static String getHallCapacity() {
        return HallCapacity;
    }

    public static void setHallCapacity(String hallCapacity) {
        HallCapacity = hallCapacity;
    }

    public static String getHallArea() {
        return HallArea;
    }

    public static void setHallArea(String hallArea) {
        HallArea = hallArea;
    }
}
