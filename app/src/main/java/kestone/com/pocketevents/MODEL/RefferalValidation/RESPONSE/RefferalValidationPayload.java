package kestone.com.pocketevents.MODEL.RefferalValidation.RESPONSE;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Xyz on 2/2/2018.
 */

public class RefferalValidationPayload {

    @SerializedName("Status")
    String Status;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
