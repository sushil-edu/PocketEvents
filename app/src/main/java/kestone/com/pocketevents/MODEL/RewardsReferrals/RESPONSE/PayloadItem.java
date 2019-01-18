package kestone.com.pocketevents.MODEL.RewardsReferrals.RESPONSE;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Xyz on 1/17/2018.
 */

public class PayloadItem {

    @SerializedName("referral_codes")
    String referral_codes;

    @SerializedName("totalEarningpoint")
    String totalEarningpoint;

    public String getReferral_codes() {
        return referral_codes;
    }

    public void setReferral_codes(String referral_codes) {
        this.referral_codes = referral_codes;
    }

    public String getTotalEarningpoint() {
        return totalEarningpoint;
    }

    public void setTotalEarningpoint(String totalEarningpoint) {
        this.totalEarningpoint = totalEarningpoint;
    }
}
