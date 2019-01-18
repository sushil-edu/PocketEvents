package kestone.com.pocketevents.MODEL.FlorePlanMail.REQUEST;

/**
 * Created by Xyz on 2/1/2018.
 */

public class FlorePlanMailRequest {
    String UserID;

    String HallID;

    public String getUserID() {
        return UserID;
    }

    public FlorePlanMailRequest(String userID, String hallID) {
        UserID = userID;
        HallID = hallID;
    }
}
