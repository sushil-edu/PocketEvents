package kestone.com.pocketevents.MODEL.Consulting;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ConsultingInfoResponse implements Serializable {

    @SerializedName("getConsultancyInfoResult")
    List<ConsultingInfoResult> response;

    public List<ConsultingInfoResult> getResponse() {
        return response;
    }

    public void setResponse(List<ConsultingInfoResult> response) {
        this.response = response;
    }
}

