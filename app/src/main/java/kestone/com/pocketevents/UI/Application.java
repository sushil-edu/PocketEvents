package kestone.com.pocketevents.UI;

import kestone.com.pocketevents.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by karan on 7/5/2017.
 */

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/kestone.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
