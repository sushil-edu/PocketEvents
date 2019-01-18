package kestone.com.pocketevents.MODEL.SaveVenue.REQUEST;

import android.util.Log;

/**
 * Created by karan on 7/18/2017.
 */

public class SaveVenueRequest {

    String userId;

    String eventName;

    String selectedFilters;

    String cityId;

    String EventID;

    String eventDate;

    String HallId;

    String VenueId;

    String SetupID;

    String DesignID = "0";

    String Morefilter = "0";

    public SaveVenueRequest(String userId, String eventName, String selectedFilters, String cityId,
                            String eventID, String eventDate, String hallId, String venueId,
                            String setupID, String designID, String morefilter) {
        this.userId = userId;
        this.eventName = eventName;
        this.selectedFilters = selectedFilters;
        this.cityId = cityId;
        EventID = eventID;
        this.eventDate = eventDate;
        HallId = hallId;
        VenueId = venueId;
        SetupID = setupID;
        DesignID = designID;
        Morefilter = morefilter;

        Log.d("userId",this.userId + " ");
        Log.d("eventName",this.eventName + " ");
        Log.d("selectedFilters",this.selectedFilters + " ");
        Log.d("cityId",this.cityId + " ");
        Log.d("EventID",this.EventID + " ");
        Log.d("HallId",this.HallId + " ");
        Log.d("VenueId",this.VenueId + " ");
        Log.d("eventDate",this.eventDate + " ");
        Log.d("SetupId",this.SetupID + " ");
        Log.d("MoreFilter",this.Morefilter + " ");

    }
}
