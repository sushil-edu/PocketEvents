package kestone.com.pocketevents.MODEL.Filters.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by karan on 7/20/2017.
 */

public class SavedEventListResponse {

    @SerializedName("SavedEventListResult")
    List<GetVenueFiltersResult> response;

    public List<GetVenueFiltersResult> getResponse() {
        return response;
    }

    public void setResponse(List<GetVenueFiltersResult> response) {
        this.response = response;
    }
}
