package kestone.com.kestone.MODEL.ForgotPassword.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by karan on 7/28/2017.
 */

public class ForgotPasswordResult {
    @SerializedName("Code")
    String code;

    @SerializedName("Message")
    String message;

    @SerializedName("Payload")
    List<Payload> payload;

    @SerializedName("Status")
    String status;

    public ForgotPasswordResult(String code, String message, List<Payload> payload, String status) {
        this.code = code;
        this.message = message;
        this.payload = payload;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Payload> getPayload() {
        return payload;
    }

    public void setPayload(List<Payload> payload) {
        this.payload = payload;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
