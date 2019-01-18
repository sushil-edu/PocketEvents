package kestone.com.pocketevents.MODEL.More.RESPONSE;

import com.google.gson.annotations.SerializedName;

/**
 * Created by karan on 10/26/2017.
 */

public class Values {
    @SerializedName("OptionID")
    String OptionID;

    @SerializedName("OptionLabel")
    String OptionLabel;

    @SerializedName("Selected")
    String Selected;

    boolean selected;

    public String getOptionID() {
        return OptionID;
    }

    public void setOptionID(String optionID) {
        OptionID = optionID;
    }

    public String getOptionLabel() {
        return OptionLabel;
    }

    public void setOptionLabel(String optionLabel) {
        OptionLabel = optionLabel;
    }

    public String getSelected() {
        return Selected;
    }

    public void setSelected(String selected) {
        Selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
