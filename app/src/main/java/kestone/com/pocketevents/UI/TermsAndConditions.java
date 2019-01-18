package kestone.com.pocketevents.UI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import kestone.com.pocketevents.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TermsAndConditions extends AppCompatActivity {

    TextView button, title;
    WebView wv;
    String url = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_terms_and_conditions );
//        button=(TextView) findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

        title = (TextView) findViewById( R.id.tvTitle );
        wv = (WebView) findViewById( R.id.term_condition );
        title.setText( getIntent().getStringExtra( "title" ) );

        wv.getSettings().setJavaScriptEnabled( true );
        wv.getSettings().setDomStorageEnabled( true );
        wv.getSettings().setLoadsImagesAutomatically( true );
        wv.setWebViewClient( new WebClient() );

        if (getIntent().getStringExtra( "url" ).isEmpty()) {
            wv.loadUrl( "file:///android_asset/termandcondition.html" );
        } else {
            url = getIntent().getStringExtra( "url" );
            wv.loadUrl( url );
        }
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext( CalligraphyContextWrapper.wrap( newBase ) );
    }

    private class WebClient extends WebViewClient  {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl( url );
            return true;
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource( view, url );
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished( view, url );
        }

//        @Override
//        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//            super.onReceivedError( view, request, error );
//        }

        @Override
        public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(TermsAndConditions.this);
            String message = "SSL Certificate error.";
            switch (error.getPrimaryError()) {
                case SslError.SSL_UNTRUSTED:
                    message = "The certificate authority is not trusted.";
                    break;
                case SslError.SSL_EXPIRED:
                    message = "The certificate has expired.";
                    break;
                case SslError.SSL_IDMISMATCH:
                    message = "The certificate Hostname mismatch.";
                    break;
                case SslError.SSL_NOTYETVALID:
                    message = "The certificate is not yet valid.";
                    break;
            }
            message += " Do you want to continue anyway?";
            builder.setTitle("SSL Certificate Error");
            builder.setMessage(message);
            builder.setPositiveButton("continue", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    handler.proceed();
                }
            });
            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    handler.cancel();
                }
            });
            final AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}
