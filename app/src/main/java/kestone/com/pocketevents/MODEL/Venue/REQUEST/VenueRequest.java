package kestone.com.pocketevents.MODEL.Venue.REQUEST;

import android.util.Log;

/**
 * Created by karan on 7/12/2017.
 */

public class VenueRequest {
    String CityID;

    String StartRowIndex;

    String MaxRows;

    String selectedFilters;

    public VenueRequest(String cityID, String startRowIndex, String maxRows, String selectedFilters) {
        CityID = cityID;
        StartRowIndex = startRowIndex;
        MaxRows = maxRows;
        this.selectedFilters = selectedFilters;

        Log.d("CityID",CityID);
        Log.d("StartRowIndex",StartRowIndex);
        Log.d("MaxRows",MaxRows);
        Log.d("selectedFilters",selectedFilters);
    }
}
