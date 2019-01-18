package kestone.com.pocketevents.MODEL.Setup.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by karan on 10/5/2017.
 */

public class SetupResult implements Serializable {

    @SerializedName("Code")
    String Code;

    @SerializedName("Message")
    String Message;

    @SerializedName("Payload")
    List<Payload> Payload;

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

    public List<kestone.com.pocketevents.MODEL.Setup.RESPONSE.Payload> getPayload() {
        return Payload;
    }

    public void setPayload(List<kestone.com.pocketevents.MODEL.Setup.RESPONSE.Payload> payload) {
        Payload = payload;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
