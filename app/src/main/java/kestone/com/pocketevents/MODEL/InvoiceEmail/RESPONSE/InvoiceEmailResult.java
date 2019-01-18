package kestone.com.pocketevents.MODEL.InvoiceEmail.RESPONSE;

import com.google.gson.annotations.SerializedName;

/**
 * Created by karan on 10/26/2017.
 */

public class InvoiceEmailResult {
    @SerializedName("Code")
    String Code;

    @SerializedName("Message")
    String Message;

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

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
