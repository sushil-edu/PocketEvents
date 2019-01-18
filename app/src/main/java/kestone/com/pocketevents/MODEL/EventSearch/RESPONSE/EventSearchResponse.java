package kestone.com.pocketevents.MODEL.EventSearch.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by karan on 10/24/2017.
 */

public class EventSearchResponse {
    @SerializedName("EventSearchResult")
    List<EventSearchResult> response;

    public List<EventSearchResult> getResponse() {
        return response;
    }

    public void setResponse(List<EventSearchResult> response) {
        this.response = response;
    }
}
