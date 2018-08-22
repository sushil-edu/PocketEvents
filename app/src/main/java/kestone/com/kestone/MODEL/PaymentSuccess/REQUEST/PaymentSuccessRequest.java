package kestone.com.kestone.MODEL.PaymentSuccess.REQUEST;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Xyz on 12/21/2017.
 */

public class PaymentSuccessRequest {
    @SerializedName("UserID")
    String UserID;

    @SerializedName("EventID")
    String EventID;

    @SerializedName("GSTNo")
    String GSTNo;

    @SerializedName("TransationID")
    String TransationID;

    @SerializedName("VanueAmount")
    String VanueAmount;

    @SerializedName("SetupAmount")
    String SetupAmount;

    @SerializedName("DesignAmount")
    String DesignAmount;

    @SerializedName("ArtistAmount")
    String ArtistAmount;

    @SerializedName("ActivitiesAmount")
    String ActivitiesAmount;

    @SerializedName("GiveawayAmount")
    String GiveawayAmount;

    @SerializedName("OtherAmount")
    String OtherAmount;

    @SerializedName("BaseAmount")
    String BaseAmount;

    @SerializedName("CGSTTax")
    String CGSTTax;

    @SerializedName("CGSTAmount")
    String CGSTAmount;

    @SerializedName("SGSTTax")
    String SGSTTax;

    @SerializedName("SGSTAmount")
    String SGSTAmount;

    @SerializedName("IGSTTax")
    String IGSTTax;

    @SerializedName("IGSTAmount")
    String IGSTAmount;

    @SerializedName("TotalTax")
    String TotalTax;

    @SerializedName("GrandTotal")
    String GrandTotal;

    public PaymentSuccessRequest(String userID, String eventID, String GSTNo, String transationID, String vanueAmount,
                                 String setupAmount, String designAmount, String artistAmount, String activitiesAmount,
                                 String giveawayAmount, String otherAmount, String baseAmount, String CGSTTax,
                                 String CGSTAmount, String SGSTTax, String SGSTAmount, String IGSTTax, String
                                         IGSTAmount, String totalTax, String grandTotal) {
        UserID = userID;
        EventID = eventID;
        this.GSTNo = GSTNo;
        TransationID = transationID;
        VanueAmount = vanueAmount;
        SetupAmount = setupAmount;
        DesignAmount = designAmount;
        ArtistAmount = artistAmount;
        ActivitiesAmount = activitiesAmount;
        GiveawayAmount = giveawayAmount;
        OtherAmount = otherAmount;
        BaseAmount = baseAmount;
        this.CGSTTax = CGSTTax;
        this.CGSTAmount = CGSTAmount;
        this.SGSTTax = SGSTTax;
        this.SGSTAmount = SGSTAmount;
        this.IGSTTax = IGSTTax;
        this.IGSTAmount = IGSTAmount;
        TotalTax = totalTax;
        GrandTotal = grandTotal;
    }
}
