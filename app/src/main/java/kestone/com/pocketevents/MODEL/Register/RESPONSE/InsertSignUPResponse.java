package kestone.com.pocketevents.MODEL.Register.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by karan on 7/10/2017.
 */

public class InsertSignUPResponse {
    @SerializedName("InsertSignUPResult")
    List<InsertSignUPResult> response;

    public List<InsertSignUPResult> getResponse() {
        return response;
    }

    public void setResponse(List<InsertSignUPResult> response) {
        this.response = response;
    }
}
