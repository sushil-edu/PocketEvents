package kestone.com.kestone.MODEL.Venue.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by karan on 7/12/2017.
 */

public class Attributes implements Serializable {

    @SerializedName("AttrName")
    String attrName;

    @SerializedName("AttrValue")
    String attrValue;

    public Attributes(String attrName, String attrValue) {
        this.attrName = attrName;
        this.attrValue = attrValue;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }
}
