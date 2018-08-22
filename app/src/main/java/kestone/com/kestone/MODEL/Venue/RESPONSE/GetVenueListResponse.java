package kestone.com.kestone.MODEL.Venue.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by karan on 7/12/2017.
 */

public class GetVenueListResponse implements Serializable {

    @SerializedName("getVenueListResult")
    List<GetVenueListResult> response;

    public List<GetVenueListResult> getResponse() {
        return response;
    }

    public void setResponse(List<GetVenueListResult> response) {
        this.response = response;
    }
}
