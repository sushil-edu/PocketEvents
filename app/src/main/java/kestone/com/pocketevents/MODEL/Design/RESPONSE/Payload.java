package kestone.com.pocketevents.MODEL.Design.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by karan on 10/16/2017.
 */

public class Payload implements Serializable {

    @SerializedName("DesignID")
    String DesignID;

    @SerializedName("DsignName")
    String DsignName;

    @SerializedName("Values")
    List<Values> values;

    boolean isSelected=false;

    public String getDesignID() {
        return DesignID;
    }

    public void setDesignID(String designID) {
        DesignID = designID;
    }

    public String getDsignName() {
        return DsignName;
    }

    public void setDsignName(String dsignName) {
        DsignName = dsignName;
    }

    public List<Values> getValues() {
        return values;
    }

    public void setValues(List<Values> values) {
        this.values = values;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
