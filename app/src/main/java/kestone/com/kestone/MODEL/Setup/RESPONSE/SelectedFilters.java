package kestone.com.kestone.MODEL.Setup.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by karan on 10/5/2017.
 */

public class SelectedFilters implements Serializable {
    @SerializedName("Id")
    String Id;

    @SerializedName("Label")
    String Label;

    @SerializedName("SelectedValue")
    String SelectedValue;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getLabel() {
        return Label;
    }

    public void setLabel(String label) {
        Label = label;
    }

    public String getSelectedValue() {
        return SelectedValue;
    }

    public void setSelectedValue(String selectedValue) {
        SelectedValue = selectedValue;
    }
}
