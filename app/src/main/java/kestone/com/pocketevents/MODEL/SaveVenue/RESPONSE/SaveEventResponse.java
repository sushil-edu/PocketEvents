package kestone.com.pocketevents.MODEL.SaveVenue.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by karan on 7/18/2017.
 */

public class SaveEventResponse {
    @SerializedName("saveEventResult")
    List<SaveEventResult> response;


    public SaveEventResponse(List<SaveEventResult> response) {
        this.response = response;
    }

    public List<SaveEventResult> getResponse() {
        return response;
    }

    public void setResponse(List<SaveEventResult> response) {
        this.response = response;
    }
}
