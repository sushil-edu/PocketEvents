package kestone.com.pocketevents.MODEL.EventEmail.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by karan on 10/26/2017.
 */

public class EventEmailResponse {
    @SerializedName("EventDesignEmailResult")
    List<EventEmailResult> response;

    public List<EventEmailResult> getResponse() {
        return response;
    }

    public void setResponse(List<EventEmailResult> response) {
        this.response = response;
    }
}
