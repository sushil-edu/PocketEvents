package kestone.com.kestone.MODEL.GetSavedVenue.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by karan on 7/19/2017.
 */

public class GetSavedEventListResponse implements Serializable {
    @SerializedName("getSavedEventListResult")
    List<GetSavedEventListResult> response;

    public List<GetSavedEventListResult> getResponse() {
        return response;
    }

    public void setResponse(List<GetSavedEventListResult> response) {
        this.response = response;
    }
}
