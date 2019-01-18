package kestone.com.pocketevents.MODEL.Filters.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by karan on 7/10/2017.
 */

public class Value implements Serializable {

    @SerializedName("DescriptionText")
    String descriptionText;

    @SerializedName("Id")
    String id;

    @SerializedName("ImageIcon")
    String imageIcon;

    @SerializedName("NameLabel")
    String nameLabel;

    @SerializedName("Selected")
    String isSelected = "false";

    public Value(String descriptionText, String id, String imageIcon, String nameLabel) {
        this.descriptionText = descriptionText;
        this.id = id;
        this.imageIcon = imageIcon;
        this.nameLabel = nameLabel;
    }

    public String getDescriptionText() {
        return descriptionText;
    }

    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageIcon() {
        return imageIcon;
    }

    public void setImageIcon(String imageIcon) {
        this.imageIcon = imageIcon;
    }

    public String getNameLabel() {
        return nameLabel;
    }

    public void setNameLabel(String nameLabel) {
        this.nameLabel = nameLabel;
    }

    public boolean isSelected() {
        return Boolean.parseBoolean(isSelected);
    }

    public void setSelected(boolean selected) {
        isSelected = String.valueOf(selected);
    }

    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }
}
