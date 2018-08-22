package kestone.com.kestone.MODEL.Consulting;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PaymentPayload implements Serializable {

    @SerializedName("CreateDate")
    String CreateDate;

    @SerializedName("DesignAmount")
    String DesignAmount;

    @SerializedName("EventId")
    String EventId;

    @SerializedName("EventName")
    String EventName;

    @SerializedName("MoreAmount")
    String MoreAmount;

    @SerializedName("SetupAmount")
    String SetupAmount;

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getDesignAmount() {
        return DesignAmount;
    }

    public void setDesignAmount(String designAmount) {
        DesignAmount = designAmount;
    }

    public String getEventId() {
        return EventId;
    }

    public void setEventId(String eventId) {
        EventId = eventId;
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }

    public String getMoreAmount() {
        return MoreAmount;
    }

    public void setMoreAmount(String moreAmount) {
        MoreAmount = moreAmount;
    }

    public String getSetupAmount() {
        return SetupAmount;
    }

    public void setSetupAmount(String setupAmount) {
        SetupAmount = setupAmount;
    }

    public String getVenueAmount() {
        return VenueAmount;
    }

    public void setVenueAmount(String venueAmount) {
        VenueAmount = venueAmount;
    }

    @SerializedName("VenueAmount")
    String VenueAmount;


}
