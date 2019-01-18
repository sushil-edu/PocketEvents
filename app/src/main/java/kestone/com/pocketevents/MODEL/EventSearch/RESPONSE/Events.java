package kestone.com.pocketevents.MODEL.EventSearch.RESPONSE;

import com.google.gson.annotations.SerializedName;

/**
 * Created by karan on 10/24/2017.
 */

public class Events {

    @SerializedName("CreateDate")
    String CreateDate;

    @SerializedName("EventId")
    String EventId;

    @SerializedName("EventName")
    String EventName;

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
}
