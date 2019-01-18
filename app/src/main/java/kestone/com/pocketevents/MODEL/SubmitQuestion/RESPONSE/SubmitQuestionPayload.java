package kestone.com.pocketevents.MODEL.SubmitQuestion.RESPONSE;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Karan Juneja on 11/27/2017.
 */

public class SubmitQuestionPayload {
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
