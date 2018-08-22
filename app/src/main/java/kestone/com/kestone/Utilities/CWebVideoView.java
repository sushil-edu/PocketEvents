package kestone.com.kestone.Utilities;

import android.content.Context;
import android.webkit.WebView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by GANESH SHARMA GANIBHAI2011@gmail.com.
 */

//URL  http://mobileapps.kestoneapps.com/eiab
//URL2 http://pocketevents.in/adminpanel

public class CWebVideoView {
    private String url;
    private Context context;
    private WebView webview;
    private static final String HTML_TEMPLATE = "webvideo.html";

    public CWebVideoView(Context context, WebView webview) {
        this.webview = webview;
        this.context = context;
        webview.setBackgroundColor(0);
        webview.getSettings().setJavaScriptEnabled(true);
    }

    public void load(String url){
        this.url = url;
        String data = readFromfile(HTML_TEMPLATE, context);
        data = data.replace("%1", url);
        webview.loadData(data, "text/html", "UTF-8");
    }

    public String readFromfile(String fileName, Context context) {
        StringBuilder returnString = new StringBuilder();
        InputStream fIn = null;
        InputStreamReader isr = null;
        BufferedReader input = null;
        try {
            fIn = context.getResources().getAssets().open(fileName, Context.MODE_WORLD_READABLE);
            isr = new InputStreamReader(fIn);
            input = new BufferedReader(isr);
            String line = "";
            while ((line = input.readLine()) != null) {
                returnString.append(line);
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
        if (url!=null){
            load(url);
        }
    }
}
