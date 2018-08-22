package kestone.com.kestone.MODEL.EventSearch.REQUEST;

/**
 * Created by karan on 10/24/2017.
 */

public class EventSearchRequest {
    String userId;

    String EventName;

    public EventSearchRequest(String userId, String eventName) {
        this.userId = userId;
        EventName = eventName;
    }
}
