package kestone.com.pocketevents.MODEL.GetSavedVenue.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by karan on 7/19/2017.
 */

public class GetSavedEventListResult implements Serializable {

    @SerializedName("Code")
    String code;

    @SerializedName("Message")
    String message;

    @SerializedName("Payload")
    List<Payload> payload;

    @SerializedName("Status")
    String status;

    public GetSavedEventListResult(String code, String message, List<Payload> payload, String status) {
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
