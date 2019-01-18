package kestone.com.pocketevents.MODEL.ContactUs;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by karan on 11/6/2017.
 */

public class ContactUsResponse implements Serializable {

    @SerializedName("GetContactUSResult")
    List<ContactUsResult> response;

    public List<ContactUsResult> getResponse() {
        return response;
    }

    public void setResponse(List<ContactUsResult> response) {
        this.response = response;
    }

}
