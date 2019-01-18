package kestone.com.pocketevents.MODEL.Payment.RESPONSE;

import com.google.gson.annotations.SerializedName;

/**
 * Created by karan on 10/30/2017.
 */

public class Payload {
    @SerializedName("InvoiceNo")
    String InvoiceNo;

    @SerializedName("Success")
    String Success;

    public String getInvoiceNo() {
        return InvoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        InvoiceNo = invoiceNo;
    }

    public String getSuccess() {
        return Success;
    }

    public void setSuccess(String success) {
        Success = success;
    }
}
