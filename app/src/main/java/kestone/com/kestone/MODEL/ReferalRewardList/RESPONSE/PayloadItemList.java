package kestone.com.kestone.MODEL.ReferalRewardList.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Xyz on 1/17/2018.
 */

public class PayloadItemList implements Serializable {

    @SerializedName("Dispatchstatus")
    String Dispatchstatus;

    @SerializedName("Earndate")
    String Earndate;

    @SerializedName("EarningPoint")
    String EarningPoint;

    @SerializedName("Earnmethod")
    String Earnmethod;

    @SerializedName("Invoiceamount")
    String Invoiceamount;

    boolean isExpanded = false;

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public String getDispatchstatus() {
        return Dispatchstatus;
    }

    public void setDispatchstatus(String dispatchstatus) {
        Dispatchstatus = dispatchstatus;
    }

    public String getEarndate() {
        return Earndate;
    }

    public void setEarndate(String earndate) {
        Earndate = earndate;
    }

    public String getEarningPoint() {
        return EarningPoint;
    }

    public void setEarningPoint(String earningPoint) {
        EarningPoint = earningPoint;
    }

    public String getEarnmethod() {
        return Earnmethod;
    }

    public void setEarnmethod(String earnmethod) {
        Earnmethod = earnmethod;
    }

    public String getInvoiceamount() {
        return Invoiceamount;
    }

    public void setInvoiceamount(String invoiceamount) {
        Invoiceamount = invoiceamount;
    }
}
