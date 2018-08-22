package kestone.com.kestone.MODEL.Filters.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by karan on 7/10/2017.
 */

public class GetVenueFiltersResult implements Serializable {

    @SerializedName("Code")
    String code;

    @SerializedName("Message")
    String message;

    @SerializedName("Payload")
    List<Payload> payloads;

    @SerializedName("Status")
    String status;

    public GetVenueFiltersResult(String code, String message, List<Payload> payloads, String status) {
        this.code = code;
        this.message = message;
        this.payloads = payloads;
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

    public List<Payload> getPayloads() {
        return payloads;
    }

    public void setPayloads(List<Payload> payloads) {
        this.payloads = payloads;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
