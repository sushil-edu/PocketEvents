package kestone.com.kestone.MODEL.GetSavedVenue.RESPONSE;

/**
 * Created by karan on 7/24/2017.
 */

public class AllEventWrapper {
    String cityName;

    String eventID;

    String eventName;

    public AllEventWrapper(String cityName, String eventID, String eventName) {
        this.cityName = cityName;
        this.eventID = eventID;
        this.eventName = eventName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
