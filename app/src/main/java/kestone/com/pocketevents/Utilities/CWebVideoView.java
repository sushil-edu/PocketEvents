package kestone.com.pocketevents.Utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.http.SslError;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by GANESH SHARMA GANIBHAI2011@gmail.com.
 */

//URL  http://mobileapps.kestoneapps.com/eiab
//URL2 http://pocketevents.in/adminpanel

public class CWebVideoView {
    private static final String HTML_TEMPLATE = "webvideo.html";
    private String url;
    private Context context;
    private WebView webview;

    public CWebVideoView(Context context, WebView webview) {
        this.webview = webview;
        this.context = context;
        webview.setBackgroundColor( 0 );
        webview.getSettings().setJavaScriptEnabled( true );
    }

    public void load(String url) {
        this.url = url;
        String data = readFromfile( HTML_TEMPLATE, context );
        data = data.replace( "%1", url );
        webview.setWebViewClient( new MyWebViewClient() );
        webview.loadData( data, "text/html", "UTF-8" );
    }

    public String readFromfile(String fileName, Context context) {
        StringBuilder returnString = new StringBuilder();
        InputStream fIn = null;
        InputStreamReader isr = null;
        BufferedReader input = null;
        try {
            fIn = context.getResources().getAssets().open( fileName, Context.MODE_WORLD_READABLE );
            isr = new InputStreamReader( fIn );
            input = new BufferedReader( isr );
            String line = "";
            while ((line = input.readLine()) != null) {
                returnString.append( line );
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            try {
                if (isr != null)
                    isr.close();
                if (fIn != null)
                    fIn.close();
                if (input != null)
                    input.close();
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
        return returnString.toString();
    }

    public void reload() {
        if (url != null) {
            load( url );
        }
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl( url );
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            try {
            } catch (WindowManager.BadTokenException e) {
                e.printStackTrace();
            }
            super.onPageFinished( view, url );
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource( view, url );
        }

//        @Override
//        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//            super.onReceivedError( view, request, error );
//        }

        @Override
        public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
            final AlertDialog.Builder builder = new AlertDialog.Builder( context );
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
            builder.setTitle( "SSL Certificate Error" );
            builder.setMessage( message );
            builder.setPositiveButton( "continue", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    handler.proceed();
                }
            } );
            builder.setNegativeButton( "cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    handler.cancel();
                }
            } );
            final AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

}
