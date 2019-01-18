package kestone.com.pocketevents.MODEL.RefferalValidation.RESPONSE;

import android.net.sip.SipSession;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Xyz on 2/2/2018.
 */

public class RefferalValidationResponse {

    @SerializedName("RefferalValidationResult")
    List<RefferalValidationResult>response;

    public List<RefferalValidationResult> getResponse() {
        return response;
    }

    public void setResponse(List<RefferalValidationResult> response) {
        this.response = response;
    }
}
