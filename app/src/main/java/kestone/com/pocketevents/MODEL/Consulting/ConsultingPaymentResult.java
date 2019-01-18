package kestone.com.pocketevents.MODEL.Consulting;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ConsultingPaymentResult { @SerializedName("Code")
String Code;

    @SerializedName("Message")
    String Message;

    @SerializedName("Status")
    String Status;

    @SerializedName("Payload")
    List<PaymentPayload> payload;

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

    public List<PaymentPayload> getPayload() {
        return payload;
    }

    public void setPayload(List<PaymentPayload> payload) {
        this.payload = payload;
    }
}

