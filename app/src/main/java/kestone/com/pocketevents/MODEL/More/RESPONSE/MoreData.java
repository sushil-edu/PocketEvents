package kestone.com.pocketevents.MODEL.More.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by karan on 10/26/2017.
 */

public class MoreData {
    @SerializedName("Valueid")
    String Valueid;

    @SerializedName("Valuename")
    String Valuename;

    @SerializedName("Values")
    List<Values> values;

    boolean selected;

    public String getValueid() {
        return Valueid;
    }

    public void setValueid(String valueid) {
        Valueid = valueid;
    }

    public String getValuename() {
        return Valuename;
    }

    public void setValuename(String valuename) {
        Valuename = valuename;
    }

    public List<Values> getValues() {
        return values;
    }

    public void setValues(List<Values> values) {
        this.values = values;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
