package kestone.com.kestone.MODEL.ForgotPassword.RESPONSE;

import com.google.gson.annotations.SerializedName;

/**
 * Created by karan on 7/28/2017.
 */

public class Payload {
    @SerializedName("Active")
    String active;

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
