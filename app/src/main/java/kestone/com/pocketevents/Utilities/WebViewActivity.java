package kestone.com.pocketevents.Utilities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import kestone.com.pocketevents.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class WebViewActivity extends AppCompatActivity {

    WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.activity_web_view );

        web = findViewById( R.id.webView );
        String url = getIntent().getStringExtra( "url" );
        web.getSettings().setJavaScriptEnabled( true );
        web.getSettings().setUserAgentString( "PocketEvents" );
        web.loadUrl( url );


    }

}
