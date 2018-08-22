package kestone.com.kestone.MODEL.PaymentSuccess.RESPONSE;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Xyz on 12/21/2017.
 */

public class PaymentSuccessResult {
    @SerializedName("Code")
    String Code;

    @SerializedName("Message")
    String Message;

    @SerializedName("Status")
    String Status;

//    @SerializedName("Payload")
//    PaymentSuccessPayload payload;


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


}
