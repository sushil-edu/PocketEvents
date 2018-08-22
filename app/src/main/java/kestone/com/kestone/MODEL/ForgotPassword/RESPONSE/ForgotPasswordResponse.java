package kestone.com.kestone.MODEL.ForgotPassword.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by karan on 7/28/2017.
 */

public class ForgotPasswordResponse {
    @SerializedName("sendForgotLinkResult")
    List<ForgotPasswordResult> response;

    public List<ForgotPasswordResult> getResponse() {
        return response;
    }

    public void setResponse(List<ForgotPasswordResult> response) {
        this.response = response;
    }
}
