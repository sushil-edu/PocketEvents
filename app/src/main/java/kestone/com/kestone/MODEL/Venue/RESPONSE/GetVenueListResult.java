package kestone.com.kestone.MODEL.Venue.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by karan on 7/12/2017.
 */

public class GetVenueListResult implements Serializable {
    @SerializedName("Code")
    String code;

    @SerializedName("Message")
    String message;

    @SerializedName("Status")
    String status;

    @SerializedName("Payload")
    List<Payload> payload;






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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Payload> getPayload() {
        return payload;
    }

    public void setPayload(List<Payload> payload) {
        this.payload = payload;
    }



    public GetVenueListResult(String code, String message, String status, List<Payload> payload) {
        this.code = code;
        this.message = message;
        this.status = status;
        this.payload = payload;
    }
}
