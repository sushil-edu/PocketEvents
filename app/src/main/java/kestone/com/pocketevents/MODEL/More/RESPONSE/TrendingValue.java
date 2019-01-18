package kestone.com.pocketevents.MODEL.More.RESPONSE;

import com.google.gson.annotations.SerializedName;

/**
 * Created by karan on 10/26/2017.
 */

public class TrendingValue {
    @SerializedName("Heading")
    String Heading;

    @SerializedName("ImageURL")
    String ImageURL;

    @SerializedName("SubHeading")
    String SubHeading;

    @SerializedName("TrendingValueID")
    String TrendingValueID;

    @SerializedName("URL")
    String URL;

    public String getHeading() {
        return Heading;
    }

    public void setHeading(String heading) {
        Heading = heading;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public String getSubHeading() {
        return SubHeading;
    }

    public void setSubHeading(String subHeading) {
        SubHeading = subHeading;
    }

    public String getTrendingValueID() {
        return TrendingValueID;
    }

    public void setTrendingValueID(String trendingValueID) {
        TrendingValueID = trendingValueID;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
