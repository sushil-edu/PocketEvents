package kestone.com.kestone.MODEL.Theme.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by karan on 10/16/2017.
 */

public class DesignResponseTheme implements Serializable {

    @SerializedName("getThemeResult")
    List<ThemeResult> response;

    public List<ThemeResult> getResponse() {
        return response;
    }

    public void setResponse(List<ThemeResult> response) {
        this.response = response;
    }
}
