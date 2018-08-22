package kestone.com.kestone.MODEL.SubmitRating.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Karan Juneja on 11/30/2017.
 */

public class SubmitRatingResponse {

    @SerializedName("SubmitRatingResult")
    List<SubmitRatingResult> response;

    public List<SubmitRatingResult> getResponse() {
        return response;
    }

    public void setResponse(List<SubmitRatingResult> response) {
        this.response = response;
    }
}
