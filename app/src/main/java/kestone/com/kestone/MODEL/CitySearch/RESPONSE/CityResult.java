package kestone.com.kestone.MODEL.CitySearch.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by karan on 7/6/2017.
 */

public class CityResult {
    @SerializedName("getCityResult")
    List<CityNameListResult> results;

    public CityResult(List<CityNameListResult> results) {
        this.results = results;
    }

    public List<CityNameListResult> getResults() {
        return results;
    }

    public void setResults(List<CityNameListResult> results) {
        this.results = results;
    }
}
