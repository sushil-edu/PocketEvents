package kestone.com.kestone.MODEL.Filters.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by karan on 7/10/2017.
 */

public class GetVenueFiltersResponse implements Serializable {

    @SerializedName("getVenueFiltersResult")
    List<GetVenueFiltersResult> response;

    public GetVenueFiltersResponse(List<GetVenueFiltersResult> response) {
        this.response = response;
    }

    public List<GetVenueFiltersResult> getResponse() {
        return response;
    }

    public void setResponse(List<GetVenueFiltersResult> response) {
        this.response = response;
    }
}
