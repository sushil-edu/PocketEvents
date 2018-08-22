package kestone.com.kestone.MODEL.MyOrders.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by karan on 10/25/2017.
 */

public class PayloadMyOrders implements Serializable {

    @SerializedName("Activities")
    String Activities;

    @SerializedName("Artist")
    String Artist;

    @SerializedName("CityID")
    String CityID;

    @SerializedName("CityName")
    String CityName;

    @SerializedName("Design")
    String Design;

    @SerializedName("EventId")
    String EventId;

    @SerializedName("EventName")
    String EventName;

    @SerializedName("Giveaway")
    String Giveaway;

    @SerializedName("InvoiceNo")
    String InvoiceNo;

    @SerializedName("OrderID")
    String OrderID;

    @SerializedName("OrderStatus")
    String OrderStatus;

    @SerializedName("SetUp")
    String SetUp;

    @SerializedName("Tax")
    String Tax;

    @SerializedName("TotalAmount")
    String TotalAmount;

    @SerializedName("Venue")
    String Venue;

    @SerializedName("CreateDate")
    String CreateDate;

    @SerializedName("Rating")
    String Rating;

    boolean expanded = false;

    public String getActivities() {
        return Activities;
    }

    public void setActivities(String activities) {
        Activities = activities;
    }

    public String getArtist() {
        return Artist;
    }

    public void setArtist(String artist) {
        Artist = artist;
    }

    public String getCityID() {
        return CityID;
    }

    public void setCityID(String cityID) {
        CityID = cityID;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getDesign() {
        return Design;
    }

    public void setDesign(String design) {
        Design = design;
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

    public String getGiveaway() {
        return Giveaway;
    }

    public void setGiveaway(String giveaway) {
        Giveaway = giveaway;
    }

    public String getInvoiceNo() {
        return InvoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        InvoiceNo = invoiceNo;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        OrderStatus = orderStatus;
    }

    public String getSetUp() {
        return SetUp;
    }

    public void setSetUp(String setUp) {
        SetUp = setUp;
    }

    public String getTax() {
        return Tax;
    }

    public void setTax(String tax) {
        Tax = tax;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getVenue() {
        return Venue;
    }

    public void setVenue(String venue) {
        Venue = venue;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }
}
