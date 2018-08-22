package kestone.com.kestone.MODEL.Design.REQUEST;

import android.util.Log;

/**
 * Created by karan on 10/16/2017.
 */

public class DesignRequest {

    String EventID;

    public DesignRequest(String eventID) {
        EventID = eventID;

        Log.d("EventID",EventID);
    }
}
