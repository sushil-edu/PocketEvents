package kestone.com.pocketevents.MODEL.Setup.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by karan on 10/5/2017.
 */

public class SetupResponse implements Serializable {
    @SerializedName("SetupAPIResult")
    List<SetupResult> response;

    public List<SetupResult> getResponse() {
        return response;
    }

    public void setResponse(List<SetupResult> response) {
        this.response = response;
    }


}
