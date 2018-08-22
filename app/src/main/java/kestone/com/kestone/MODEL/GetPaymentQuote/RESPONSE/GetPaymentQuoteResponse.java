package kestone.com.kestone.MODEL.GetPaymentQuote.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import kestone.com.kestone.MODEL.Payment.RESPONSE.PaymentResult;

/**
 * Created by Xyz on 12/21/2017.
 */

public class GetPaymentQuoteResponse {

    @SerializedName("GetPaymentQuoteResult")
    List<GetPaymentQuoteResult> response;

    public List<GetPaymentQuoteResult> getResponse() {
        return response;
    }

    public void setResponse(List<GetPaymentQuoteResult> response) {
        this.response = response;
    }
}
