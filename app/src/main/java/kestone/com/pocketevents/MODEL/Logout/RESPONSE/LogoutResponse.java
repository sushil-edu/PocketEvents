package kestone.com.pocketevents.MODEL.Logout.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by karan on 8/3/2017.
 */

public class LogoutResponse {
    @SerializedName("logoutResult")
    List<LogoutResult> response;

    public List<LogoutResult> getResponse() {
        return response;
    }

    public void setResponse(List<LogoutResult> response) {
        this.response = response;
    }

}
