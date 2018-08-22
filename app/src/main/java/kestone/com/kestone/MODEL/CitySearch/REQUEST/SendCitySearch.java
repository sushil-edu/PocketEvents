package kestone.com.kestone.MODEL.CitySearch.REQUEST;

/**
 * Created by karan on 7/6/2017.
 */

public class SendCitySearch {
    String Name;
    String Mode;

    public SendCitySearch(String name, String mode) {
        Name = name;
        Mode = mode;
    }

    public String getName() {
        return Name;
    }

    public String getMode() {
        return Mode;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setMode(String mode) {
        Mode = mode;
    }
}
