package kestone.com.kestone.MODEL.Logout.RESPONSE;

import com.google.gson.annotations.SerializedName;

/**
 * Created by karan on 8/3/2017.
 */

public class Payload {

    @SerializedName("message")
    String message;

    public Payload(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
