package kestone.com.kestone.MODEL.Setup.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by karan on 10/5/2017.
 */

public class Payload implements Serializable {

    @SerializedName("CityId")
    String CityId;

    @SerializedName("CityName")
    String CityName;

    @SerializedName("Data")
    List<Data> Data;

    @SerializedName("HallId")
    String HallId;

    @SerializedName("HallName")
    String HallName;

    @SerializedName("HallImage")
    String HallImage;

    @SerializedName("VenueId")
    String VenueId;

    @SerializedName("VenueName")
    String VenueName;

    @SerializedName("selectedFilter")
    List<SelectedFilters> selectedFilter;

    public String getCityId() {
        return CityId;
    }

    public void setCityId(String cityId) {
        CityId = cityId;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public List<kestone.com.kestone.MODEL.Setup.RESPONSE.Data> getData() {
        return Data;
    }

    public void setData(List<kestone.com.kestone.MODEL.Setup.RESPONSE.Data> data) {
        Data = data;
    }

    public String getHallId() {
        return HallId;
    }

    public void setHallId(String hallId) {
        HallId = hallId;
    }

    public String getHallName() {
        return HallName;
    }

    public void setHallName(String hallName) {
        HallName = hallName;
    }

    public String getHallImage() {
        return HallImage;
    }

    public void setHallImage(String hallImage) {
        HallImage = hallImage;
    }

    public String getVenueId() {
        return VenueId;
    }

    public void setVenueId(String venueId) {
        VenueId = venueId;
    }

    public String getVenueName() {
        return VenueName;
    }

    public void setVenueName(String venueName) {
        VenueName = venueName;
    }

    public List<SelectedFilters> getSelectedFilter() {
        return selectedFilter;
    }

    public void setSelectedFilter(List<SelectedFilters> selectedFilter) {
        this.selectedFilter = selectedFilter;
    }
}
