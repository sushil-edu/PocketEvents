package kestone.com.kestone.MODEL.Venue.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by karan on 7/12/2017.
 */

public class Halls implements Serializable {

    @SerializedName("capacity")
    List<Capacity> capacities;

    @SerializedName("Dimensions")
    String dimensions;

    @SerializedName("hallImages")
    String [] hallImages;

    @SerializedName("HallName")
    String hallName;

    @SerializedName("hallSizes")
    List<HallSizes> hallSizes;

    @SerializedName("IsRecommended")
    String isRecommended;

    @SerializedName("MinHallSizeRequired")
    String MinHallSizeRequired;

    public String getMinHallSizeRequired() {
        return MinHallSizeRequired;
    }

    public void setMinHallSizeRequired(String minHallSizeRequired) {
        MinHallSizeRequired = minHallSizeRequired;
    }

    @SerializedName("Floreplan")
    String floorPlan;

    @SerializedName("HallID")
    String hallId;

    boolean isSelected = false;

    boolean compare = false;


    public List<Capacity> getCapacities() {
        return capacities;
    }

    public void setCapacities(List<Capacity> capacities) {
        this.capacities = capacities;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String[] getHallImages() {
        return hallImages;
    }

    public void setHallImages(String[] hallImages) {
        this.hallImages = hallImages;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public List<HallSizes> getHallSizes() {
        return hallSizes;
    }

    public void setHallSizes(List<HallSizes> hallSizes) {
        this.hallSizes = hallSizes;
    }

    public String getIsRecommended() {
        return isRecommended;
    }

    public void setIsRecommended(String isRecommended) {
        this.isRecommended = isRecommended;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getHallId() {
        return hallId;
    }

    public void setHallId(String hallId) {
        this.hallId = hallId;
    }

    public Halls(List<Capacity> capacities, String dimensions, String[] hallImages, String hallName, List<HallSizes> hallSizes, String isRecommended) {
        this.capacities = capacities;
        this.dimensions = dimensions;
        this.hallImages = hallImages;
        this.hallName = hallName;
        this.hallSizes = hallSizes;
        this.isRecommended = isRecommended;
    }

    public String getFloorPlan() {
        return floorPlan;
    }

    public void setFloorPlan(String floorPlan) {
        this.floorPlan = floorPlan;
    }

    public boolean isCompare() {
        return compare;
    }

    public void setCompare(boolean compare) {
        this.compare = compare;
    }
}
