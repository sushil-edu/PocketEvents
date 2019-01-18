package kestone.com.pocketevents.MODEL.EventEmail.REQUEST;

/**
 * Created by karan on 10/26/2017.
 */

public class EventEmailRequest {
    String EventID;

    String UserID;

    public EventEmailRequest(String eventID, String userID) {
        EventID = eventID;
        UserID = userID;
    }
}
