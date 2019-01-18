package kestone.com.pocketevents.MODEL.FlorePlanMail.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Xyz on 2/1/2018.
 */

public class FlorePlanMailResponse {

    @SerializedName("FlorePlanMailResult")
    List<FlorePlanMailResult>response;

    public List<FlorePlanMailResult> getResponse() {
        return response;
    }

    public void setResponse(List<FlorePlanMailResult> response) {
        this.response = response;
    }
}
