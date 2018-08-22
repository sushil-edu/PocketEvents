package kestone.com.kestone.MODEL.SaveVenue.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by karan on 7/18/2017.
 */

public class SaveEventResult {
    @SerializedName("Code")
    String code;

    @SerializedName("Message")
    String messsage;

    @SerializedName("Payload")
    List<Payload> payload;

    @SerializedName("Status")
    String status;

    public SaveEventResult(String code, String messsage, List<Payload> payload, String status) {
        this.code = code;
        this.messsage = messsage;
        this.payload = payload;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMesssage() {
        return messsage;
    }

    public void setMesssage(String messsage) {
        this.messsage = messsage;
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
