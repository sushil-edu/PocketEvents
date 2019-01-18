package kestone.com.pocketevents.MODEL.More.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by karan on 10/26/2017.
 */

public class MoreResponse {
    @SerializedName("MoreAPIResult")
    List<MoreResult> response;

    public List<MoreResult> getResponse() {
        return response;
    }

    public void setResponse(List<MoreResult> response) {
        this.response = response;
    }
}
