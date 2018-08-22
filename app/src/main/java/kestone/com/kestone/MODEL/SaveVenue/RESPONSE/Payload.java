package kestone.com.kestone.MODEL.SaveVenue.RESPONSE;

import com.google.gson.annotations.SerializedName;

/**
 * Created by karan on 7/18/2017.
 */

public class Payload {
    @SerializedName("Active")
    String active;

    @SerializedName("EventID")
    String eventId;

    @SerializedName("EventDate")
    String EventDate;

    public Payload(String active, String eventId) {
        this.active = active;
        this.eventId = eventId;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventDate() {
        return EventDate;
    }

    public void setEventDate(String eventDate) {
        EventDate = eventDate;
    }
}
