package kestone.com.kestone.MODEL.PaymentSuccess.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Xyz on 12/21/2017.
 */

public class PaymentSuccessResponse {

    @SerializedName("InsertInvoiceDetailsResult")
    List<PaymentSuccessResult> response;

    public List<PaymentSuccessResult> getResponse() {
        return response;
    }

    public void setResponse(List<PaymentSuccessResult> response) {
        this.response = response;
    }
}
