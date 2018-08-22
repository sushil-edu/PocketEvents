package kestone.com.kestone.MODEL.SubmitRating.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Karan Juneja on 11/30/2017.
 */

public class SubmitRatingResult {

    @SerializedName("Code")
    String Code;

    @SerializedName("Message")
    String Message;

    @SerializedName("Payload")
    List<SubmitratingPayload> payload;

    @SerializedName("Status")
    String Status;

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

    public List<SubmitratingPayload> getPayload() {
        return payload;
    }

    public void setPayload(List<SubmitratingPayload> payload) {
        this.payload = payload;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
