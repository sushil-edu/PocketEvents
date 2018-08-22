package kestone.com.kestone.MODEL.Venue.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by karan on 7/12/2017.
 */

public class Capacity implements Serializable {
    @SerializedName("SeatingCapacity")
    String seatingCapacity;

    @SerializedName("SeatingName")
    String seatingName;

    public String getSeatingCapacity() {
        return seatingCapacity;
    }

    public void setSeatingCapacity(String seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

    public String getSeatingName() {
        return seatingName;
    }

    public void setSeatingName(String seatingName) {
        this.seatingName = seatingName;
    }

    public Capacity(String seatingCapacity, String seatingName) {
        this.seatingCapacity = seatingCapacity;
        this.seatingName = seatingName;
    }
}
