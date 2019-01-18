package kestone.com.pocketevents.MODEL.DeleteEvent.RESPONSE;

import com.google.gson.annotations.SerializedName;

/**
 * Created by karan on 10/4/2017.
 */

public class Payload {
    @SerializedName("EventID")
    String EventID;

    @SerializedName("Success")
    String Success;

    public String getEventID() {
        return EventID;
    }

    public void setEventID(String eventID) {
        EventID = eventID;
    }

    public String getSuccess() {
        return Success;
    }

    public void setSuccess(String success) {
        Success = success;
    }
}
