package kestone.com.kestone.MODEL.Login.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by karan on 7/9/2017.
 */

public class LoginResponse {

    @SerializedName("loginResult")
    List<LoginResult> response;

    public List<LoginResult> getResponse() {
        return response;
    }

    public void setResponse(List<LoginResult> response) {
        this.response = response;
    }
}
