package kestone.com.kestone.MODEL.Design.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by karan on 10/16/2017.
 */

public class DesignResult implements Serializable {

    @SerializedName("CityId")
    String CityId;

    @SerializedName("CityName")
    String CityName;

    @SerializedName("Code")
    String Code;

    @SerializedName("HallId")
    String HallId;

    @SerializedName("HallName")
    String HallName;

    @SerializedName("Message")
    String Message;

    @SerializedName("Payload")
    List<Payload> payload;

    @SerializedName("Status")
    String Status;

    @SerializedName("VenueId")
    String VenueId;

    @SerializedName("VenueName")
    String VenueName;

    public String getCityId() {
        return CityId;
    }

    public void setCityId(String cityId) {
        CityId = cityId;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getHallId() {
        return HallId;
    }

    public void setHallId(String hallId) {
        HallId = hallId;
    }

    public String getHallName() {
        return HallName;
    }

    public void setHallName(String hallName) {
        HallName = hallName;
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

    public String getVenueId() {
        return VenueId;
    }

    public void setVenueId(String venueId) {
        VenueId = venueId;
    }

    public String getVenueName() {
        return VenueName;
    }

    public void setVenueName(String venueName) {
        VenueName = venueName;
    }
}
