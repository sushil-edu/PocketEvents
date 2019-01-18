package kestone.com.pocketevents.MODEL.RewardsReferrals.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import kestone.com.pocketevents.MODEL.Login.RESPONSE.Payload;

/**
 * Created by Xyz on 1/17/2018.
 */

public class ReferralRewardResult implements Serializable{

    @SerializedName("Code")
    String code;

    @SerializedName("Message")
    String message;

    @SerializedName("Payload")
    List<PayloadItem> payloads;

    @SerializedName("Status")
    String status;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<PayloadItem> getPayloads() {
        return payloads;
    }

    public void setPayloads(List<PayloadItem> payloads) {
        this.payloads = payloads;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
