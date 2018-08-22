package kestone.com.kestone.MODEL.Consulting;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by karan on 10/24/2017.
 */

public class Payload implements Serializable {
    @SerializedName("Amount")
    String Amount;

    @SerializedName("ID")
    String ID;

    @SerializedName("Name")
    String Name;

    boolean selected = false;


    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
