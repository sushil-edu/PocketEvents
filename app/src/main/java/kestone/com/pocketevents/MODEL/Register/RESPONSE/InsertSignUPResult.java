package kestone.com.pocketevents.MODEL.Register.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import kestone.com.pocketevents.MODEL.Login.RESPONSE.Payload;

/**
 * Created by karan on 7/10/2017.
 */

public class InsertSignUPResult {
    @SerializedName("Code")
    String code;

    @SerializedName("Message")
    String message;

    @SerializedName("Status")
    String status;

    @SerializedName("Payload")
    List<Payload> payloads;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public List<Payload> getPayloads() {
        return payloads;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPayloads(List<Payload> payloads) {
        this.payloads = payloads;
    }
}
