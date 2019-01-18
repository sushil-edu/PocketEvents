package kestone.com.pocketevents.MODEL.Filters.REQUEST;

/**
 * Created by karan on 7/20/2017.
 */

public class SavedFilterRequest {

    String EventID;

    String UserID;

    public SavedFilterRequest(String eventID, String userID) {
        EventID = eventID;
        UserID = userID;
    }
}
