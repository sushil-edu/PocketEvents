package kestone.com.kestone.MODEL.DesignEmail.REQUEST;

import android.util.Log;

/**
 * Created by karan on 10/16/2017.
 */

public class DesignEmailRequest {
    String DesignID;

    String UserID;

    public DesignEmailRequest(String designID, String userID) {
        DesignID = designID;
        UserID = userID;

        Log.d("DesignID",DesignID);
        Log.d("UserID",UserID);
    }
}
