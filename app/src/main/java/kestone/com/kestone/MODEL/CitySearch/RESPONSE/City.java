package kestone.com.kestone.MODEL.CitySearch.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by karan on 7/6/2017.
 */

public class City  implements Serializable{

    @SerializedName("City_Name")
    String cityName;

    @SerializedName("Client_id")
    String cityID;

    @SerializedName("Parent_City")
    String parentID;

    public City(String cityName, String cityID,String parentID) {
        this.cityName = cityName;
        this.cityID = cityID;
        this.parentID = parentID;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCityID() {
        return cityID;
    }

    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }
}
