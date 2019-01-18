package kestone.com.pocketevents.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import kestone.com.pocketevents.R;
import qiu.niorgai.StatusBarCompat;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class GetStartedActivity extends AppCompatActivity {

    TextView getStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);
       // StatusBarCompat.translucentStatusBar(GetStartedActivity.this,true);

        getStarted = (TextView) findViewById(R.id.getStarted_START);
        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(GetStartedActivity.this,CitySearchActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
