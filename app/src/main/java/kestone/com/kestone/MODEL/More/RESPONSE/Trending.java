package kestone.com.kestone.MODEL.More.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by karan on 10/26/2017.
 */

public class Trending {
    @SerializedName("MoreID")
    String MoreID;

    @SerializedName("MoreName")
    String MoreName;

    @SerializedName("TrendingValue")
    List<TrendingValue> trendingValues;

    public String getMoreID() {
        return MoreID;
    }

    public void setMoreID(String moreID) {
        MoreID = moreID;
    }

    public String getMoreName() {
        return MoreName;
    }

    public void setMoreName(String moreName) {
        MoreName = moreName;
    }

    public List<TrendingValue> getTrendingValues() {
        return trendingValues;
    }

    public void setTrendingValues(List<TrendingValue> trendingValues) {
        this.trendingValues = trendingValues;
    }
}
