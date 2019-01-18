package kestone.com.pocketevents.MODEL.Design.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by karan on 10/16/2017.
 */

public class DesignResponse implements Serializable {

    @SerializedName("DesignAPIResult")
    List<DesignResult> response;

    public List<DesignResult> getResponse() {
        return response;
    }

    public void setResponse(List<DesignResult> response) {
        this.response = response;
    }
}
