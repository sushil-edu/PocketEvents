package kestone.com.kestone.MODEL.Register.REQUEST;

/**
 * Created by karan on 7/10/2017.
 */

public class RegisterRequest {
    String EmailID;

    String Pssword;

    String FistName;

    String LastName;

    String Organisation;

    String Designation;

    String Profilepic;

    String DeviceType;

    String deviceToken;

    String Mode;

    String Mobile;

    String Address;

    String refferal;

    public RegisterRequest(String emailID, String pssword, String fistName, String lastName, String organisation, String designation, String profilepic, String deviceType, String deviceToken, String mode, String mobile, String address, String refferal) {
        EmailID = emailID;
        Pssword = pssword;
        FistName = fistName;
        LastName = lastName;
        Organisation = organisation;
        Designation = designation;
        Profilepic = profilepic;
        DeviceType = deviceType;
        this.deviceToken = deviceToken;
        Mode = mode;
        Mobile = mobile;
        Address = address;
        this.refferal = refferal;
    }
}
