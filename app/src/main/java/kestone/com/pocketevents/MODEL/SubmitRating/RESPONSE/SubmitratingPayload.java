package kestone.com.pocketevents.MODEL.SubmitRating.RESPONSE;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Karan Juneja on 11/30/2017.
 */

public class SubmitratingPayload {
    @SerializedName("Message")
    String Message;

    @SerializedName("Status")
    String Status;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
