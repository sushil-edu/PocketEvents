package kestone.com.kestone.MODEL.FlorePlanMail.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Xyz on 2/1/2018.
 */

public class FlorePlanMailResult {
    @SerializedName("Code")
    String Code;

    @SerializedName("Message")
    String Message;

    @SerializedName("Payload")
    List<FlorePlanMailPayload>payload;

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

    public List<FlorePlanMailPayload> getPayload() {
        return payload;
    }

    public void setPayload(List<FlorePlanMailPayload> payload) {
        this.payload = payload;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
