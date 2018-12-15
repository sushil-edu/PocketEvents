package kestone.com.kestone.UI;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import kestone.com.kestone.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TermsAndConditions extends AppCompatActivity {

//    TextView button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);
//        button=(TextView) findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

        WebView wv;
        wv = (WebView) findViewById(R.id.term_condition);
        wv.loadUrl("file:///android_asset/termandcondition.html");

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
