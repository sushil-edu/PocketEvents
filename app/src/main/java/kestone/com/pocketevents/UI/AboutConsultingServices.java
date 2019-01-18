package kestone.com.pocketevents.UI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.http.SslError;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import java.util.List;

import kestone.com.pocketevents.MODEL.Consulting.ConsultingInfoResponse;
import kestone.com.pocketevents.MODEL.Consulting.InfoPayload;
import kestone.com.pocketevents.R;

public class AboutConsultingServices extends AppCompatActivity {

    ConsultingInfoResponse data;
    List<InfoPayload> dataList;
    WebView body1;//, body2, body3, body4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_consulting_services);

        body1 = (WebView) findViewById(R.id.body1);
//        body2 = (TextView) findViewById(R.id.body2);
//        body3 = (TextView) findViewById(R.id.body3);
//        body4 = (TextView) findViewById(R.id.body4);

//        if (getIntent() != null) {
//            data = (ConsultingInfoResponse) getIntent().getSerializableExtra("data");
//            dataList = data.getResponse().get(0).getPayload();
//            if (dataList != null && dataList.size() > 0) {
        body1.getSettings().setJavaScriptEnabled( true );
        body1.getSettings().setDomStorageEnabled( true );
        body1.getSettings().setLoadsImagesAutomatically( true );
        body1.setWebViewClient( new WebClient() );
                body1.loadUrl( "file:///android_asset/consultingservices.html" );
//                body2.setText(Html.fromHtml(dataList.get(1).getDesc()));
//                body3.setText(Html.fromHtml(dataList.get(2).getDesc()));
//                body4.setText(Html.fromHtml(dataList.get(3).getDesc()));
//            }
//        }

    }
    private class WebClient extends WebViewClient {
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
            final AlertDialog.Builder builder = new AlertDialog.Builder(AboutConsultingServices.this);
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
