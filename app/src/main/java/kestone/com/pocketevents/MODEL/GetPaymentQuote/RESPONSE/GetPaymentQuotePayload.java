package kestone.com.pocketevents.MODEL.GetPaymentQuote.RESPONSE;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Xyz on 12/21/2017.
 */

public class GetPaymentQuotePayload {

         @SerializedName("BaseAmount")
         String BaseAmount;

         @SerializedName("CGSTTax")
        String CGSTTax;

         @SerializedName("CGSTTaxAmount")
         String CGSTTaxAmount;

         @SerializedName("ClientGSTStateID")
        String ClientGSTStateID;

         @SerializedName("CompanyGSTStateID")
         String CompanyGSTStateID;

         @SerializedName("GSTValidation")
        String GSTValidation;

         @SerializedName("GrandTotalAmount")

        String GrandTotalAmount;

         @SerializedName("IGSTTax")
         String IGSTTax;

         @SerializedName("IGSTTaxAmount")
        String IGSTTaxAmount;

         @SerializedName("SGSTTax")
        String SGSTTax;

         @SerializedName("SGSTTaxAmount")
        String SGSTTaxAmount;
         @SerializedName("Tax")
        String Tax;
         @SerializedName("TaxAmount")
        String TaxAmount;

    public String getBaseAmount() {
        return BaseAmount;
    }

    public void setBaseAmount(String baseAmount) {
        BaseAmount = baseAmount;
    }

    public String getCGSTTax() {
        return CGSTTax;
    }

    public void setCGSTTax(String CGSTTax) {
        this.CGSTTax = CGSTTax;
    }

    public String getCGSTTaxAmount() {
        return CGSTTaxAmount;
    }

    public void setCGSTTaxAmount(String CGSTTaxAmount) {
        this.CGSTTaxAmount = CGSTTaxAmount;
    }

    public String getClientGSTStateID() {
        return ClientGSTStateID;
    }

    public void setClientGSTStateID(String clientGSTStateID) {
        ClientGSTStateID = clientGSTStateID;
    }

    public String getCompanyGSTStateID() {
        return CompanyGSTStateID;
    }

    public void setCompanyGSTStateID(String companyGSTStateID) {
        CompanyGSTStateID = companyGSTStateID;
    }

    public String getGSTValidation() {
        return GSTValidation;
    }

    public void setGSTValidation(String GSTValidation) {
        this.GSTValidation = GSTValidation;
    }

    public String getGrandTotalAmount() {
        return GrandTotalAmount;
    }

    public void setGrandTotalAmount(String grandTotalAmount) {
        GrandTotalAmount = grandTotalAmount;
    }

    public String getIGSTTax() {
        return IGSTTax;
    }

    public void setIGSTTax(String IGSTTax) {
        this.IGSTTax = IGSTTax;
    }

    public String getIGSTTaxAmount() {
        return IGSTTaxAmount;
    }

    public void setIGSTTaxAmount(String IGSTTaxAmount) {
        this.IGSTTaxAmount = IGSTTaxAmount;
    }

    public String getSGSTTax() {
        return SGSTTax;
    }

    public void setSGSTTax(String SGSTTax) {
        this.SGSTTax = SGSTTax;
    }

    public String getSGSTTaxAmount() {
        return SGSTTaxAmount;
    }

    public void setSGSTTaxAmount(String SGSTTaxAmount) {
        this.SGSTTaxAmount = SGSTTaxAmount;
    }

    public String getTax() {
        return Tax;
    }

    public void setTax(String tax) {
        Tax = tax;
    }

    public String getTaxAmount() {
        return TaxAmount;
    }

    public void setTaxAmount(String taxAmount) {
        TaxAmount = taxAmount;
    }
}
