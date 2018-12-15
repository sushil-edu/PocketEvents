package kestone.com.kestone.MODEL.Theme.REQUEST;

import android.util.Log;

/**
 * Created by karan on 10/16/2017.
 */

public class DesignRequestTheme {

    String ID,  Mode;

    public DesignRequestTheme(String ID, String Mode) {
        this.ID = ID;
        this.Mode = Mode;

        Log.d("ID and mode ",ID+" Mode "+Mode);
    }
}
