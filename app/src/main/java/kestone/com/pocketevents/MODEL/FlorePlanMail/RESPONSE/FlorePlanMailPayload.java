package kestone.com.pocketevents.MODEL.FlorePlanMail.RESPONSE;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Xyz on 2/1/2018.
 */

public class FlorePlanMailPayload {
    @SerializedName("Status")
    String Status;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
