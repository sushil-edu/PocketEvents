package kestone.com.kestone.MODEL.Payment.REQUEST;

import android.util.Log;

/**
 * Created by karan on 10/30/2017.
 */

public class PaymentRequest {
    String VenueAmount;

    String SetupAmount;

    String DesignAmount;

    String ArtistAmount;

    String ActivitiesAmount;

    String GiveawayAmount;

    String EventID;

    String UserID;

    String TotalAmount;

    String TaxAmount;

    String TaxPercent;

    public PaymentRequest(String venueAmount, String setupAmount, String designAmount, String artistAmount, String activitiesAmount, String giveawayAmount, String eventID, String userID, String totalAmount, String taxAmount, String taxPercent) {
        VenueAmount = venueAmount;
        SetupAmount = setupAmount;
        DesignAmount = designAmount;
        ArtistAmount = artistAmount;
        ActivitiesAmount = activitiesAmount;
        GiveawayAmount = giveawayAmount;
        EventID = eventID;
        UserID = userID;
        TotalAmount = totalAmount;
        TaxAmount = taxAmount;
        TaxPercent = taxPercent;

        Log.d("VenueAmount",VenueAmount);
        Log.d("SetupAmount",SetupAmount);
        Log.d("DesignAmount",DesignAmount);
        Log.d("ArtistAmount",ArtistAmount);
        Log.d("ActivitiesAmount",ActivitiesAmount);
        Log.d("GiveawayAmount",GiveawayAmount);
        Log.d("EventID",EventID);
        Log.d("UserID",UserID);
        Log.d("TotalAmount",TotalAmount);
        Log.d("TaxAmount",TaxAmount);
        Log.d("TaxPercent",TaxPercent);
    }

    public PaymentRequest(String venueAmount, String setupAmount, String designAmount, String artistAmount, String activitiesAmount, String giveawayAmount) {
        VenueAmount = venueAmount;
        SetupAmount = setupAmount;
        DesignAmount = designAmount;
        ArtistAmount = artistAmount;
        ActivitiesAmount = activitiesAmount;
        GiveawayAmount = giveawayAmount;
    }

    public String getEventID() {
        return EventID;
    }

    public void setEventID(String eventID) {
        EventID = eventID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getTaxAmount() {
        return TaxAmount;
    }

    public void setTaxAmount(String taxAmount) {
        TaxAmount = taxAmount;
    }

    public String getTaxPercent() {
        return TaxPercent;
    }

    public void setTaxPercent(String taxPercent) {
        TaxPercent = taxPercent;
    }
}
