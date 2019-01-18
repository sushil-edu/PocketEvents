package kestone.com.pocketevents.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import kestone.com.pocketevents.R;
import kestone.com.pocketevents.Utilities.ZoomImageView;

/**
 * Created by Xyz on 1/12/2018.
 */

public class FullScreenImage extends AppCompatActivity {


    protected ZoomImageView imageView;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_full_screen_image );
        imageView = (ZoomImageView) findViewById( R.id.imageView );
        Intent intent = getIntent();
        String imageurl = intent.getStringExtra( "imageurl" );

        pb = (ProgressBar) findViewById( R.id.progressBar2 );


        Glide.with( FullScreenImage.this )
                .load( imageurl )
                .placeholder( R.drawable.placeholder_big )
                .listener( new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        pb.setVisibility( View.GONE );
                        return false;
                    }
                } )
                .into( imageView )
        ;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }
}