package kestone.com.pocketevents.MODEL.ChangePassword.REQUEST;

/**
 * Created by Xyz on 2/1/2018.
 */

public class ChangePasswordRequst {

    String UserID;
    String OldPassword;
    String NewPassword;

    public ChangePasswordRequst(String userID, String oldPassword, String newPassword) {
        UserID = userID;
        OldPassword = oldPassword;
        NewPassword = newPassword;
    }
}
