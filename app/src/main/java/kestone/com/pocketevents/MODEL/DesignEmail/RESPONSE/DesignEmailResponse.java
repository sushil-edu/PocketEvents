package kestone.com.pocketevents.MODEL.DesignEmail.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by karan on 10/16/2017.
 */

public class DesignEmailResponse {
    @SerializedName("DesignEmailResult")
    List<DesignEmailResult> response;

    public List<DesignEmailResult> getResponse() {
        return response;
    }

    public void setResponse(List<DesignEmailResult> response) {
        this.response = response;
    }
}
