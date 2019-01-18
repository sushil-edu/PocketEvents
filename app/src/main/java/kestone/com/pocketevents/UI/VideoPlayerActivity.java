package kestone.com.pocketevents.UI;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import kestone.com.pocketevents.R;
import kestone.com.pocketevents.Utilities.GeneralUtils;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class VideoPlayerActivity extends AppCompatActivity {

    private int position = 0;
    private MediaController mediaControls;

    VideoView myVideoView;
    TextView back;

    private String video_url;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        progressDialog = new ProgressDialog(VideoPlayerActivity.this);
        progressDialog.setMessage(getString(R.string.PleaseWait));
        progressDialog.setCancelable(false);
        myVideoView = (VideoView)findViewById(R.id.video_view);
        back = (TextView)findViewById(R.id.tv_done);
       // video_url = getIntent().getStringExtra("data");
        video_url = "https://www.youtube.com/watch?v=nin7j42pPGU";
        if (mediaControls == null) {
            mediaControls = new MediaController(VideoPlayerActivity.this);
        }

        if (GeneralUtils.isNetworkAvailable(VideoPlayerActivity.this)) {
            progressDialog.show();
            try {
                myVideoView.setMediaController(mediaControls);
                myVideoView.setVideoURI(Uri.parse(video_url));
            } catch (Exception e) {
                e.printStackTrace();
            }

            myVideoView.requestFocus();
            myVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                    progressDialog.dismiss();
                    return false;
                }
            });
            myVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                public void onPrepared(MediaPlayer mediaPlayer) {
                    progressDialog.dismiss();
                    myVideoView.seekTo(position);
                    if (position == 0) {
                        mediaPlayer.start();
                    } else {
                        mediaPlayer.pause();
                    }

                }
            });
        } else {
            GeneralUtils.displayNetworkAlert(VideoPlayerActivity.this, false);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("Position", myVideoView.getCurrentPosition());
        myVideoView.pause();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        position = savedInstanceState.getInt("Position");
        myVideoView.seekTo(position);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }



}
