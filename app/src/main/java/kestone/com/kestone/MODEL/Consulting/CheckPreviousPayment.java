package kestone.com.kestone.MODEL.Consulting;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CheckPreviousPayment {
    @SerializedName("EventSearchIDResult")
    List<ConsultingPaymentResult> response;

    public List<ConsultingPaymentResult> getResponse() {
        return response;
    }

    public void setResponse(List<ConsultingPaymentResult> response) {
        this.response = response;
    }
}
