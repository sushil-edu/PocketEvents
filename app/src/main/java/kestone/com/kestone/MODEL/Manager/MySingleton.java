package kestone.com.kestone.MODEL.Manager;

import java.util.ArrayList;
import java.util.List;

import kestone.com.kestone.MODEL.Design.RESPONSE.DesignResponse;
import kestone.com.kestone.MODEL.Design.RESPONSE.DesignResult;
import kestone.com.kestone.MODEL.Filters.RESPONSE.GetVenueFiltersResponse;
import kestone.com.kestone.MODEL.Filters.RESPONSE.Payload;
import kestone.com.kestone.MODEL.More.RESPONSE.MoreResponse;
import kestone.com.kestone.MODEL.Venue.RESPONSE.GetVenueListResponse;
import kestone.com.kestone.Utilities.StorageUtilities;

/**
 * Created by karan on 7/11/2017.
 */

public class MySingleton {

    private static MySingleton instance;

    public GetVenueFiltersResponse model;

    public GetVenueListResponse venues;


    public MoreResponse moreResponse;

    public DesignResponse designResponse;

    public int morePOS;

    public String LabelMore;



    private int selectedHallVenuePosition = 999;

    private int selectedHallPosition = 999;

    //Filter Management
    List<Payload> payload1;
    List<Payload> payload2;
    List<Payload> payload3;

    List<kestone.com.kestone.MODEL.Venue.RESPONSE.Payload> compare = new ArrayList<>();


    //Compare Management
    boolean c1Empty = true;
    boolean c2Empty = true;
    boolean c3Empty = true;

    int compareIndex1 = 999;
    int compareIndex2 = 999;
    int compareIndex3 = 999;

    int compareHallIndex1 = 999;
    int compareHallIndex2 = 999;
    int compareHallIndex3 = 999;

    int compareCount = 0;


    public static void initInstance() {
        if (instance == null) {
            // Create the instance
            instance = new MySingleton();
        }
    }

    public static synchronized MySingleton getInstance() {
        // Return the instance
        return instance;
    }

    private MySingleton() {
        compare.add(0, null);
        compare.add(1, null);
        compare.add(2, null);
    }




    //FILTER MANAGEMENT
    public void resetAll() {
        resetPayload1();
        resetPayload2();
        //resetPayload3();

    }
    public void resetPayload2() {
        for (int i = 0; i < MySingleton.getInstance().getPayload2().size(); i++) {
            MySingleton.getInstance().getPayload2().get(i).setSelected(false);
            for (int j = 0; j < MySingleton.getInstance().getPayload2().get(i).getValues().size(); j++) {
                MySingleton.getInstance().getPayload2().get(i).getValues().get(j).setSelected(false);
            }
        }

    }
    public void resetPayload1() {
        for (int i = 0; i < MySingleton.getInstance().getPayload1().size(); i++) {
            MySingleton.getInstance().getPayload1().get(i).setSelected(false);
            MySingleton.getInstance().getPayload1().get(i).setRanges(null);
            for (int j = 0; j < MySingleton.getInstance().getPayload1().get(i).getValues().size(); j++) {
                MySingleton.getInstance().getPayload1().get(i).getValues().get(j).setSelected(false);
            }
        }
    }
    // END FILTER MANAGEMENT





    //COMPARE MANAGEMENT
    public boolean isC1Empty() {
        return c1Empty;
    }

    public void setC1Empty(boolean c1Empty) {
        this.c1Empty = c1Empty;
        if (c1Empty) {
            compareCount--;
        } else {
            compareCount++;
        }
    }

    public boolean isC2Empty() {
        return c2Empty;
    }

    public void setC2Empty(boolean c2Empty) {
        this.c2Empty = c2Empty;
        if (c2Empty) {
            compareCount--;
        } else {
            compareCount++;
        }
    }

    public boolean isC3Empty() {
        return c3Empty;
    }

    public void setC3Empty(boolean c3Empty) {
        this.c3Empty = c3Empty;
        if (c3Empty) {
            compareCount--;
        } else {
            compareCount++;
        }
    }

    public int getCompareCount() {
        return compareCount;
    }

    public void resetCompare() {
        c1Empty = true;
        c2Empty = true;
        c3Empty = true;
        compareCount = 0;
        compare.set(0, null);
        compare.set(1, null);
        compare.set(2, null);

        selectedHallPosition = 999;
        selectedHallVenuePosition = 999;



    }
    //END COMPARE MANAGEMENT










    //GETTERS AND SETTERS

    public DesignResponse getDesignResponse() {
        return designResponse;
    }

    public void setDesignResponse(DesignResponse designResponse) {
        this.designResponse = designResponse;
    }

    public int getSelectedHallVenuePosition() {
        return selectedHallVenuePosition;
    }

    public void setSelectedHallVenuePosition(int selectedHallVenuePosition) {
        this.selectedHallVenuePosition = selectedHallVenuePosition;
    }

    public int getSelectedHallPosition() {
        return selectedHallPosition;
    }

    public void setSelectedHallPosition(int selectedHallPosition) {
        this.selectedHallPosition = selectedHallPosition;
    }

    public int getCompareIndex1() {
        return compareIndex1;
    }

    public void setCompareIndex1(int compareIndex1) {
        this.compareIndex1 = compareIndex1;
    }

    public int getCompareIndex2() {
        return compareIndex2;
    }

    public void setCompareIndex2(int compareIndex2) {
        this.compareIndex2 = compareIndex2;
    }

    public int getCompareIndex3() {
        return compareIndex3;
    }

    public void setCompareIndex3(int compareIndex3) {
        this.compareIndex3 = compareIndex3;
    }

    public GetVenueListResponse getVenues() {
        return venues;
    }

    public void setVenues(GetVenueListResponse venues) {
        this.venues = venues;
    }

    public List<Payload> getPayload3() {
        return payload3;
    }

    public void setPayload3(List<Payload> payload3) {
        this.payload3 = payload3;
    }

    public GetVenueFiltersResponse getModel() {
        return model;
    }

    public void setModel(GetVenueFiltersResponse model) {
        this.model = model;
    }

    public List<Payload> getPayload2() {
        return payload2;
    }

    public void setPayload2(List<Payload> payload2) {
        this.payload2 = payload2;
    }

    public List<Payload> getPayload1() {
        return payload1;
    }

    public void setPayload1(List<Payload> payload1) {
        this.payload1 = payload1;
    }

    public List<kestone.com.kestone.MODEL.Venue.RESPONSE.Payload> getCompare() {
        return compare;
    }

    public void setCompare(List<kestone.com.kestone.MODEL.Venue.RESPONSE.Payload> compare) {
        this.compare = compare;

    }

    public int getCompareHallIndex1() {
        return compareHallIndex1;
    }

    public void setCompareHallIndex1(int compareHallIndex1) {
        this.compareHallIndex1 = compareHallIndex1;
    }

    public int getCompareHallIndex2() {
        return compareHallIndex2;
    }

    public void setCompareHallIndex2(int compareHallIndex2) {
        this.compareHallIndex2 = compareHallIndex2;
    }

    public int getCompareHallIndex3() {
        return compareHallIndex3;
    }

    public void setCompareHallIndex3(int compareHallIndex3) {
        this.compareHallIndex3 = compareHallIndex3;
    }

    public MoreResponse getMoreResponse() {
        return moreResponse;
    }

    public void setMoreResponse(MoreResponse moreResponse) {
        this.moreResponse = moreResponse;
    }

    public int getMorePOS() {
        return morePOS;
    }

    public void setMorePOS(int morePOS) {
        this.morePOS = morePOS;
    }

    public String getLabelMore() {
        return LabelMore;
    }

    public void setLabelMore(String labelMore) {
        LabelMore = labelMore;
    }

}
