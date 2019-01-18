package kestone.com.pocketevents.MODEL.CitySearch.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by karan on 7/6/2017.
 */

public class CityNameListResult {
    @SerializedName("Code")
    public String code;

    @SerializedName("Message")
    public String message;

    @SerializedName("Payload")
    public List<City> payload;

    @SerializedName("Status")
    public String status;

    public CityNameListResult(String code, String message, List<City> payload, String status) {
        this.code = code;
        this.message = message;
        this.payload = payload;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<City> getPayload() {
        return payload;
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

    public void setPayload(List<City> payload) {
        this.payload = payload;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
