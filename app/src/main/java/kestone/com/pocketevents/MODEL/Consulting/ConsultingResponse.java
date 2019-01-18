package kestone.com.pocketevents.MODEL.Consulting;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by karan on 10/24/2017.
 */

public class ConsultingResponse implements Serializable {

    @SerializedName("GetPriceDetailsResult")
    List<ConsultingResult> response;

    public List<ConsultingResult> getResponse() {
        return response;
    }

    public void setResponse(List<ConsultingResult> response) {
        this.response = response;
    }
}
