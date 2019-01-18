package kestone.com.pocketevents.UI;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import kestone.com.pocketevents.R;
import qiu.niorgai.StatusBarCompat;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RegisterConfirmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_confirm);
       // StatusBarCompat.translucentStatusBar(RegisterConfirmActivity.this,true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
