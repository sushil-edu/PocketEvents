package kestone.com.kestone.MODEL.GetPaymentQuote.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import kestone.com.kestone.MODEL.Payment.RESPONSE.Payload;

/**
 * Created by Xyz on 12/21/2017.
 */

public class GetPaymentQuoteResult  {

    @SerializedName("Code")
    String Code;

    @SerializedName("Message")
    String Message;

    @SerializedName("Payload")
    List<GetPaymentQuotePayload> payload;

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

    public List<GetPaymentQuotePayload> getPayload() {
        return payload;
    }

    public void setPayload(List<GetPaymentQuotePayload> payload) {
        this.payload = payload;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
