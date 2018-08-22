package kestone.com.kestone.MODEL.PaymentSuccess.RESPONSE;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Xyz on 12/21/2017.
 */

public class PaymentSuccessPayload {
    @SerializedName("InvoiceNo")
    String InvoiceNo;

    public String getInvoiceNo() {
        return InvoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        InvoiceNo = invoiceNo;
    }
}
