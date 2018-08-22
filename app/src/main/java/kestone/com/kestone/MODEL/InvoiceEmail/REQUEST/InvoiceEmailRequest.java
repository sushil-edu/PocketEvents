package kestone.com.kestone.MODEL.InvoiceEmail.REQUEST;

/**
 * Created by karan on 10/26/2017.
 */

public class InvoiceEmailRequest {

    String InvoiceNo;

    String UserID;

    public InvoiceEmailRequest(String invoiceNo, String userID) {
        InvoiceNo = invoiceNo;
        UserID = userID;
    }
}
