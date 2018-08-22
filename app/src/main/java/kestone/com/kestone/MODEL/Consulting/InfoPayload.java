package kestone.com.kestone.MODEL.Consulting;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class InfoPayload implements Serializable {
    @SerializedName("Desc")
    String Desc;

    @SerializedName("Type")
    String Type;

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String Desc) {
        Desc = Desc;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

}

