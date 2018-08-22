package kestone.com.kestone.MODEL.Login.REQUEST;

/**
 * Created by karan on 7/9/2017.
 */

public class LoginRequest {

    String EmailID;

    String Password;

    String deviceToken;

    String UserID;

    public LoginRequest(String emailID, String password, String deviceToken, String userID) {
        EmailID = emailID;
        Password = password;
        this.deviceToken = deviceToken;
        UserID = userID;
    }

    public String getEmailID() {
        return EmailID;
    }

    public void setEmailID(String emailID) {
        EmailID = emailID;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }
}
