package kestone.com.kestone.MODEL.ContactUs;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by karan on 11/6/2017.
 */

public class ContactUsResult implements Serializable {

    @SerializedName("Code")
    String Code;

    @SerializedName("Message")
    String Message;

    @SerializedName("Payload")
    List<ContactUsPayload> payload;

    @SerializedName("Status")
    String Status;

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public List<ContactUsPayload> getPayload() {
        return payload;
    }

    public void setPayload(List<ContactUsPayload> payload) {
        this.payload = payload;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
