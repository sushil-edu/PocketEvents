package kestone.com.kestone.MODEL.More.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by karan on 10/26/2017.
 */

public class Payload {
    @SerializedName("MoreID")
    String MoreID;

    @SerializedName("MoreName")
    String MoreName;

    @SerializedName("MoreData")
    List<MoreData> moreData;

    int count = 0;


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

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

    public List<MoreData> getMoreData() {
        return moreData;
    }

    public void setMoreData(List<MoreData> moreData) {
        this.moreData = moreData;
    }
}
