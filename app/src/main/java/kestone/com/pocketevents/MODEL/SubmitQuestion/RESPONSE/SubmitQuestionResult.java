package kestone.com.pocketevents.MODEL.SubmitQuestion.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Karan Juneja on 11/27/2017.
 */

public class SubmitQuestionResult {
    @SerializedName("Code")
    String Code;

    @SerializedName("Message")
    String Message;

    @SerializedName("Status")
    String Status;

    @SerializedName("Payload")
    List<SubmitQuestionPayload> payload;

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public void setPayload(List<SubmitQuestionPayload> payload) {
        this.payload = payload;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
