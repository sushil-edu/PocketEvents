package kestone.com.kestone.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.net.MalformedURLException;

import kestone.com.kestone.MODEL.Manager.MySingleton;
import kestone.com.kestone.R;
import kestone.com.kestone.Utilities.CWebVideoView;
import kestone.com.kestone.Utilities.GeneralUtils;
import kestone.com.kestone.Utilities.ZoomImageView;

/**
 * Created by Xyz on 1/12/2018.
 */

public class FullScreenImage extends AppCompatActivity {



    protected ZoomImageView imageView;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);
        imageView=(ZoomImageView) findViewById(R.id.imageView);
        Intent intent = getIntent();
        String imageurl = intent.getStringExtra("imageurl");

        pb=(ProgressBar)findViewById(R.id.progressBar2);


        Glide.with(FullScreenImage.this)
                .load(imageurl)
                .placeholder(R.drawable.placeholder_big)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        pb.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imageView)
        ;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }
}