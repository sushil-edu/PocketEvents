package kestone.com.pocketevents.MODEL.Design.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by karan on 10/16/2017.
 */

public class Values implements Serializable {
    @SerializedName("Id")
    String ValueId;

    @SerializedName("Name")
    String Name;

    @SerializedName("URL")
    String URL;

    public String getValueId() {
        return ValueId;
    }

    public void setValueId(String valueId) {
        ValueId = valueId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
