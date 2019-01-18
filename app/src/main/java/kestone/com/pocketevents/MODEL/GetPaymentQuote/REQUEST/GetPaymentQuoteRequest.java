package kestone.com.pocketevents.MODEL.GetPaymentQuote.REQUEST;

/**
 * Created by Xyz on 12/21/2017.
 */

public class GetPaymentQuoteRequest {

    String TotalAmount;

    String GSTNo;

    public GetPaymentQuoteRequest(String totalAmount, String GSTNo) {
        TotalAmount = totalAmount;
        this.GSTNo = GSTNo;
    }
}
