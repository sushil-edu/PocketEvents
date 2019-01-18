package kestone.com.pocketevents.MODEL.SubmitQuestion.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Karan Juneja on 11/27/2017.
 */

public class SubmitQuestionResponse {
    @SerializedName("SubmitQuestionResult")
    List<SubmitQuestionResult> response;

    public List<SubmitQuestionResult> getResponse() {
        return response;
    }

    public void setResponse(List<SubmitQuestionResult> response) {
        this.response = response;
    }
}
