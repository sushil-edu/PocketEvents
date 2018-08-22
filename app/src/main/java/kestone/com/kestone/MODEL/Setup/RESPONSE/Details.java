package kestone.com.kestone.MODEL.Setup.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by karan on 10/5/2017.
 */

public class Details implements Serializable {
    @SerializedName("Label")
    String Label;

    @SerializedName("Values")
    String Values;

    public String getLabel() {
        return Label;
    }

    public void setLabel(String label) {
        Label = label;
    }

    public String getValues() {
        return Values;
    }

    public void setValues(String values) {
        Values = values;
    }
}
