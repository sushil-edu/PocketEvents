package kestone.com.kestone.MODEL.More.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by karan on 10/26/2017.
 */

public class MoreResult {
    @SerializedName("CityID")
    String CityID;

    @SerializedName("CityName")
    String CityName;

    @SerializedName("Code")
    String Code;

    @SerializedName("HallID")
    String HallID;

    @SerializedName("HallName")
    String HallName;

    @SerializedName("Message")
    String Message;

    @SerializedName("Payload")
    List<Payload> payload;

    @SerializedName("Status")
    String Status;

    @SerializedName("Trending")
    List<Trending> trending;

    @SerializedName("VanueID")
    String VanueID;

    @SerializedName("VanueName")
    String VanueName;


    public String getCityID() {
        return CityID;
    }

    public void setCityID(String cityID) {
        CityID = cityID;
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

    public String getHallID() {
        return HallID;
    }

    public void setHallID(String hallID) {
        HallID = hallID;
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

    public List<Trending> getTrending() {
        return trending;
    }

    public void setTrending(List<Trending> trending) {
        this.trending = trending;
    }

    public String getVanueID() {
        return VanueID;
    }

    public void setVanueID(String vanueID) {
        VanueID = vanueID;
    }

    public String getVanueName() {
        return VanueName;
    }

    public void setVanueName(String vanueName) {
        VanueName = vanueName;
    }
}
