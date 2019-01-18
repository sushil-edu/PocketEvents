package kestone.com.pocketevents.MODEL.Setup.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by karan on 10/5/2017.
 */

public class Data implements Serializable {
    @SerializedName("Description")
    String Description;

    @SerializedName("Description2")
    String Description2;

    @SerializedName("Details")
    List<Details> Details;

    @SerializedName("Isrecommended")
    String Isrecommended;

    @SerializedName("PackageImages")
    String[] PackageImages;

    @SerializedName("SetupId")
    String SetupId;

    @SerializedName("SetupName")
    String SetupName;

    boolean isSelected = false;

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDescription2() {
        return Description2;
    }

    public void setDescription2(String description2) {
        Description2 = description2;
    }

    public List<kestone.com.pocketevents.MODEL.Setup.RESPONSE.Details> getDetails() {
        return Details;
    }

    public void setDetails(List<kestone.com.pocketevents.MODEL.Setup.RESPONSE.Details> details) {
        Details = details;
    }

    public String getIsrecommended() {
        return Isrecommended;
    }

    public void setIsrecommended(String isrecommended) {
        Isrecommended = isrecommended;
    }

    public String[] getPackageImages() {
        return PackageImages;
    }

    public void setPackageImages(String[] packageImages) {
        PackageImages = packageImages;
    }

    public String getSetupId() {
        return SetupId;
    }

    public void setSetupId(String setupId) {
        SetupId = setupId;
    }

    public String getSetupName() {
        return SetupName;
    }

    public void setSetupName(String setupName) {
        SetupName = setupName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
