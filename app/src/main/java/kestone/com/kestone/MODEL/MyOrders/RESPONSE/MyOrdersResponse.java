package kestone.com.kestone.MODEL.MyOrders.RESPONSE;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by karan on 10/25/2017.
 */

public class MyOrdersResponse implements Serializable {
    @SerializedName("MyOrderResult")
    List<MyOrdersResult> response;

    public List<MyOrdersResult> getResponse() {
        return response;
    }

    public void setResponse(List<MyOrdersResult> response) {
        this.response = response;
    }
}
