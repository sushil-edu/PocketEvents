package kestone.com.pocketevents.MODEL.DeleteEvent.REQUEST;

/**
 * Created by karan on 10/4/2017.
 */

public class DeleteEventRequest {
    String EventID;

    String UserID;

    public DeleteEventRequest(String eventID, String userID) {
        EventID = eventID;
        UserID = userID;
    }
}
