package kestone.com.kestone.MODEL.Venue.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by karan on 7/12/2017.
 */

public class HallSizes implements Serializable {
    @SerializedName("Dimensions")
    String dimensions;

    @SerializedName("Size")
    String size;

    @SerializedName("SizeName")
    String sizeName;

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public HallSizes(String dimensions, String size, String sizeName) {
        this.dimensions = dimensions;
        this.size = size;
        this.sizeName = sizeName;
    }
}
