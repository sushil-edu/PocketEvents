package kestone.com.kestone.MODEL.More.REQUEST;

import android.util.Log;

/**
 * Created by karan on 10/26/2017.
 */

public class MoreRequest {
    String EventID;

    String UserID;

    public MoreRequest(String eventID, String userID) {
        EventID = eventID;
        UserID = userID;
        Log.d("EventID",EventID);
        Log.d("EventID",EventID);
    }
}
