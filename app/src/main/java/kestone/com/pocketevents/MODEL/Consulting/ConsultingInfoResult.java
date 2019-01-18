package kestone.com.pocketevents.MODEL.Consulting;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ConsultingInfoResult implements Serializable {
    @SerializedName("Code")
    String Code;

    @SerializedName("Message")
    String Message;

    @SerializedName("Status")
    String Status;

    @SerializedName("Payload")
    List<InfoPayload> payload;

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

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public List<InfoPayload> getPayload() {
        return payload;
    }

    public void setPayload(List<InfoPayload> payload) {
        this.payload = payload;
    }
}

