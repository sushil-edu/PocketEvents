package kestone.com.kestone.MODEL.GetSavedVenue.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by karan on 7/19/2017.
 */

public class Payload implements Serializable {
    @SerializedName("CityID")
    String CityID;

    @SerializedName("Cityname")
    String Cityname;

    @SerializedName("CreateDate")
    String CreateDate;

    @SerializedName("EventId")
    String EventId;

    @SerializedName("EventName")
    String EventName;

    @SerializedName("HallId")
    String HallId;

    @SerializedName("HallName")
    String HallName;

    @SerializedName("VenueId")
    String VenueId;

    @SerializedName("VenueName")
    String VenueName;

    @SerializedName("SetupID")
    String SetupID;

    @SerializedName("SetupName")
    String SetupName;

    @SerializedName("DesignID")
    String DesignID;

    @SerializedName("DesignName")
    String DesignName;

    @SerializedName("MoreFilterName")
    String MoreFilterName;



    boolean isExpanded = false;

    public String getCityID() {
        return CityID;
    }

    public void setCityID(String cityID) {
        CityID = cityID;
    }

    public String getCityname() {
        return Cityname;
    }

    public void setCityname(String cityname) {
        Cityname = cityname;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getEventId() {
        return EventId;
    }

    public void setEventId(String eventId) {
        EventId = eventId;
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
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

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public String getSetupID() {
        return SetupID;
    }

    public void setSetupID(String setupID) {
        SetupID = setupID;
    }

    public String getSetupName() {
        return SetupName;
    }

    public void setSetupName(String setupName) {
        SetupName = setupName;
    }

    public String getDesignID() {
        return DesignID;
    }

    public void setDesignID(String designID) {
        DesignID = designID;
    }

    public String getDesignName() {
        return DesignName;
    }

    public void setDesignName(String designName) {
        DesignName = designName;
    }

    public String getMoreFilterName() {
        return MoreFilterName;
    }

    public void setMoreFilterName(String moreFilterName) {
        MoreFilterName = moreFilterName;
    }
}
