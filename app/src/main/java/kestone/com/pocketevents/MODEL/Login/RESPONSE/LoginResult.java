package kestone.com.pocketevents.MODEL.Login.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by karan on 7/9/2017.
 */

public class LoginResult {
    @SerializedName("Code")
    String code;

    @SerializedName("Message")
    String message;

    @SerializedName("Payload")
    List<Payload> payloads;

    @SerializedName("Status")
    String status;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<Payload> getPayloads() {
        return payloads;
    }

    public String getStatus() {
        return status;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPayloads(List<Payload> payloads) {
        this.payloads = payloads;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
