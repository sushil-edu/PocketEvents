package kestone.com.kestone.MODEL.ChangePassword.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Xyz on 2/1/2018.
 */

public class ChangePasswordResponse implements Serializable {

    @SerializedName("ChangePasswordResult")
    List<ChangePasswordResult>response;

    public List<ChangePasswordResult> getResponse() {
        return response;
    }

    public void setResponse(List<ChangePasswordResult> response) {
        this.response = response;
    }
}
