package kestone.com.kestone.MODEL.Setup.REQUEST;

import android.util.Log;

/**
 * Created by karan on 10/5/2017.
 */

public class SetupRequest {

    String EventID;

    String VenueId;

    String HallId;

    String CityId;

    public SetupRequest(String eventID, String venueId, String hallId, String cityId) {
        EventID = eventID;
        VenueId = venueId;
        HallId = hallId;
        CityId = cityId;

        Log.d("EventID",EventID);
        Log.d("VenueId",VenueId);
        Log.d("HallId",HallId);
        Log.d("CityId",CityId);
    }
}
