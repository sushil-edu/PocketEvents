package kestone.com.pocketevents.MODEL.Payment.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by karan on 10/30/2017.
 */

public class PaymentResponse {
    @SerializedName("PaymentDetailsResult")
    List<PaymentResult> response;

    public List<PaymentResult> getResponse() {
        return response;
    }

    public void setResponse(List<PaymentResult> response) {
        this.response = response;
    }
}
