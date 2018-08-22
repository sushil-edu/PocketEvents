package kestone.com.kestone.MODEL.RewardsReferrals.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import kestone.com.kestone.MODEL.MyOrders.RESPONSE.MyOrdersResult;

/**
 * Created by Xyz on 1/17/2018.
 */

public class RewardsReferralsResponse implements Serializable {

    @SerializedName("ReferralRewardResult")
    List<ReferralRewardResult> response;

    public List<ReferralRewardResult> getResponse() {
        return response;
    }

    public void setResponse(List<ReferralRewardResult> response) {
        this.response = response;
    }
}
