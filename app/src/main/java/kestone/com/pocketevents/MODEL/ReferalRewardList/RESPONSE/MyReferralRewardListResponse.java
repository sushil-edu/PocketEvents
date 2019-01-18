package kestone.com.pocketevents.MODEL.ReferalRewardList.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Xyz on 1/17/2018.
 */

public class MyReferralRewardListResponse implements Serializable {

    @SerializedName("MyReferralRewardListResult")
    List <MyReferralRewardListResult>response;

    public List<MyReferralRewardListResult> getResponse() {
        return response;
    }

    public void setResponse(List<MyReferralRewardListResult> response) {
        this.response = response;
    }
}
