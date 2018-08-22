package kestone.com.kestone.MODEL.Venue.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by karan on 7/12/2017.
 */

public class Payload implements Serializable {
    @SerializedName("IsRecomended")
    String isRecomended;

    @SerializedName("TotalCount")
    String totalCount;

    @SerializedName("VanueFilter")
    String venueFilter;

    @SerializedName("VenueAddress")
    String venueAddress;

    @SerializedName("VenueImages")
    String[] venueImages;

    @SerializedName("VenueLat")
    String venueLat;

    @SerializedName("VenueLong")
    String venueLon;

    @SerializedName("VenueName")
    String venueName;

    @SerializedName("VenuePhone")
    String[] venuePhone;

    @SerializedName("KeystoneRating")
    String KeystoneRating;

    @SerializedName("Attributes")
    List<Attributes> attributes;

    @SerializedName("Halls")
    List<Halls> halls;

    @SerializedName("VenueID")
    String venueID;

    boolean AddedToCompare = false;

    int compareIndex;

    int compareHallIndex;


    public Payload() {
    }

    public Payload(String isRecomended, String totalCount, String venueFilter, String venueAddress,
                   String[] venueImages, String venueLat, String venueLon, String venueName, String[] venuePhone, List<Attributes> attributes, List<Halls> halls) {
        this.isRecomended = isRecomended;
        this.totalCount = totalCount;
        this.venueFilter = venueFilter;
        this.venueAddress = venueAddress;
        this.venueImages = venueImages;
        this.venueLat = venueLat;
        this.venueLon = venueLon;
        this.venueName = venueName;
        this.venuePhone = venuePhone;
        this.attributes = attributes;
        this.halls = halls;
    }

    public String getIsRecomended() {
        return isRecomended;
    }

    public void setIsRecomended(String isRecomended) {
        this.isRecomended = isRecomended;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getVenueFilter() {
        return venueFilter;
    }

    public void setVenueFilter(String venueFilter) {
        this.venueFilter = venueFilter;
    }

    public String getVenueAddress() {
        return venueAddress;
    }

    public void setVenueAddress(String venueAddress) {
        this.venueAddress = venueAddress;
    }

    public String[] getVenueImages() {
        return venueImages;
    }

    public void setVenueImages(String[] venueImages) {
        this.venueImages = venueImages;
    }

    public String getVenueLat() {
        return venueLat;
    }

    public void setVenueLat(String venueLat) {
        this.venueLat = venueLat;
    }

    public String getVenueLon() {
        return venueLon;
    }

    public void setVenueLon(String venueLon) {
        this.venueLon = venueLon;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String[] getVenuePhone() {
        return venuePhone;
    }

    public void setVenuePhone(String[] venuePhone) {
        this.venuePhone = venuePhone;
    }

    public List<Attributes> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attributes> attributes) {
        this.attributes = attributes;
    }

    public List<Halls> getHalls() {
        return halls;
    }

    public void setHalls(List<Halls> halls) {
        this.halls = halls;
    }

    public boolean isAddedToCompare() {
        return AddedToCompare;
    }

    public void setAddedToCompare(boolean addedToCompare) {
        AddedToCompare = addedToCompare;
    }

    public int getCompareIndex() {
        return compareIndex;
    }

    public void setCompareIndex(int compareIndex) {
        this.compareIndex = compareIndex;
    }


    public String getVenueID() {
        return venueID;
    }

    public void setVenueID(String venueID) {
        this.venueID = venueID;
    }

    public int getCompareHallIndex() {
        return compareHallIndex;
    }

    public void setCompareHallIndex(int compareHallIndex) {
        this.compareHallIndex = compareHallIndex;
    }

    public String getKeystoneRating() {
        return KeystoneRating;
    }

    public void setKeystoneRating(String keystoneRating) {
        KeystoneRating = keystoneRating;
    }
}
