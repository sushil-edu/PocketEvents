package kestone.com.kestone.MODEL.Theme.RESPONSE;

public class DesignStatus {
    String id;
    boolean status = false;

    public DesignStatus(String id, boolean status) {
        this.id = id;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public boolean isStatus() {
        return status;
    }
}
