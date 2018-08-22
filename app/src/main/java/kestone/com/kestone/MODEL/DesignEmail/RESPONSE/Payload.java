package kestone.com.kestone.MODEL.DesignEmail.RESPONSE;

import com.google.gson.annotations.SerializedName;

/**
 * Created by karan on 10/16/2017.
 */

public class Payload {
    @SerializedName("DesignID")
    String DesignID;

    @SerializedName("Success")
    String Success;

    public String getDesignID() {
        return DesignID;
    }

    public void setDesignID(String designID) {
        DesignID = designID;
    }

    public String getSuccess() {
        return Success;
    }

    public void setSuccess(String success) {
        Success = success;
    }
}
