package kestone.com.pocketevents.MODEL.DeleteEvent.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by karan on 10/4/2017.
 */

public class DeleteEventResponse {
    @SerializedName("DeleteEventResult")
    List<DeleteEventResult> response;

    public List<DeleteEventResult> getResponse() {
        return response;
    }

    public void setResponse(List<DeleteEventResult> response) {
        this.response = response;
    }
}
