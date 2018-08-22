package kestone.com.kestone.MODEL.ForgotPassword.REQUEST;

/**
 * Created by karan on 7/28/2017.
 */

public class ForegotPasswordRequest {
    String EmailID;

    public ForegotPasswordRequest(String emailID) {
        EmailID = emailID;
    }

    public String getEmailID() {
        return EmailID;
    }

    public void setEmailID(String emailID) {
        EmailID = emailID;
    }
}
