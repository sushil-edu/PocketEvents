package kestone.com.kestone.MODEL.Filters.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by karan on 7/10/2017.
 */

public class Payload implements Serializable {

    @SerializedName("FilerID")
    String filterId;

    @SerializedName("FilterName")
    String filterName;

    @SerializedName("FilterNameDisplay")
    String filterNameDisplay;

    @SerializedName("FilterSubHeading")
    String filterSubHeading;

    @SerializedName("MoreType")
    String moreType;

    @SerializedName("ParamName")
    String paramName;

    @SerializedName("SelectionType")
    String selectiontype;

    @SerializedName("Style")
    String style;

    @SerializedName("Value")
    List<Value> values;

    @SerializedName("IsSelected")
    String isSelected = "false";

    SavedRange ranges = null;




    String selectedValueLabel;




    public Payload(String filterId, String filterName, String filterNameDisplay, String filterSubHeading, String moreType, String paramName, String selectiontype, String style, List<Value> values) {
        this.filterId = filterId;
        this.filterName = filterName;
        this.filterNameDisplay = filterNameDisplay;
        this.filterSubHeading = filterSubHeading;
        this.moreType = moreType;
        this.paramName = paramName;
        this.selectiontype = selectiontype;
        this.style = style;
        this.values = values;
    }

    public String getFilterId() {
        return filterId;
    }

    public void setFilterId(String filterId) {
        this.filterId = filterId;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public String getFilterNameDisplay() {
        return filterNameDisplay;
    }

    public void setFilterNameDisplay(String filterNameDisplay) {
        this.filterNameDisplay = filterNameDisplay;
    }

    public String getFilterSubHeading() {
        return filterSubHeading;
    }

    public void setFilterSubHeading(String filterSubHeading) {
        this.filterSubHeading = filterSubHeading;
    }

    public String getMoreType() {
        return moreType;
    }

    public void setMoreType(String moreType) {
        this.moreType = moreType;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getSelectiontype() {
        return selectiontype;
    }

    public void setSelectiontype(String selectiontype) {
        this.selectiontype = selectiontype;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public List<Value> getValues() {
        return values;
    }

    public void setValues(List<Value> values) {
        this.values = values;
    }

    public boolean isSelected() {
        return Boolean.parseBoolean(isSelected);
    }

    public void setSelected(boolean selected) {
        isSelected = String.valueOf(selected);
    }

    public String getSelectedValueLabel() {
        return selectedValueLabel;
    }

    public void setSelectedValueLabel(String selectedValueLabel) {
        this.selectedValueLabel = selectedValueLabel;
    }

    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }

//    public SavedRange getRanges() {
//        return ranges;
//    }
//
//    public void setRanges(SavedRange ranges) {
//        this.ranges = ranges;
//    }
    public SavedRange getRanges() {
        return ranges;
    }

    public void setRanges(SavedRange ranges) {
        this.ranges = ranges;
    }
}
