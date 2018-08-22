package kestone.com.kestone.MODEL.ChangePassword.RESPONSE;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Xyz on 2/1/2018.
 */

public class ChangePasswordPayload {

    @SerializedName("Status")
    String Status;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
