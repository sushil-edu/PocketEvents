package kestone.com.kestone.MODEL.Payment.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by karan on 10/30/2017.
 */

public class PaymentResult {

    @SerializedName("Code")
    String Code;

    @SerializedName("Message")
    String Message;

    @SerializedName("Payload")
    List<Payload> payload;

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

    public List<Payload> getPayload() {
        return payload;
    }

    public void setPayload(List<Payload> payload) {
        this.payload = payload;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
