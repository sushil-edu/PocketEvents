
package kestone.com.pocketevents.MODEL.Theme.RESPONSE;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PayloadNested implements Serializable {

    @SerializedName("Imageid")
    private String mImageid;
    @SerializedName("ThemeImage")
    private String mThemeImage;
    @SerializedName("ThemeType")
    private String mThemeType;

    public boolean isSelected;

    public String getImageid() {
        return mImageid;
    }

    public void setImageid(String imageid) {
        mImageid = imageid;
    }

    public String getThemeImage() {
        return mThemeImage;
    }

    public void setThemeImage(String themeImage) {
        mThemeImage = themeImage;
    }

    public String getThemeType() {
        return mThemeType;
    }

    public void setThemeType(String themeType) {
        mThemeType = themeType;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
