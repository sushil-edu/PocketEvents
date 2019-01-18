package kestone.com.pocketevents.MODEL.InvoiceEmail.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by karan on 10/26/2017.
 */

public class InvoiceEmailResponse {
    @SerializedName("InvoiceEmailResult")
    List<InvoiceEmailResult> response;

    public List<InvoiceEmailResult> getResponse() {
        return response;
    }

    public void setResponse(List<InvoiceEmailResult> response) {
        this.response = response;
    }
}
