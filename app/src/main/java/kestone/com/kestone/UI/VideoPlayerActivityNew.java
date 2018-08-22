package kestone.com.kestone.UI;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.widget.MediaController;
import android.widget.VideoView;

import java.net.MalformedURLException;

import kestone.com.kestone.R;
import kestone.com.kestone.Utilities.CWebVideoView;
import kestone.com.kestone.Utilities.GeneralUtils;

public class VideoPlayerActivityNew extends AppCompatActivity {

    protected VideoView videoView;

    protected WebView webViewVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player_new);
        webViewVideo=(WebView)findViewById(R.id.webView_video);
        videoView=(VideoView)findViewById(R.id.iv_video_view_play);
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");

        if (data != null){
            if (data.contains("youtube")){
               try {
                    CWebVideoView cWebVideoView = new CWebVideoView(this, webViewVideo);
                    if (data.contains("https://www.youtube.com/embed/")){
                        cWebVideoView.load(data);
                    } else {
                        String id = GeneralUtils.extractYoutubeId(data);
                        cWebVideoView.load("https://www.youtube.com/embed/"+id);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    // Start the MediaController
                    MediaController mediacontroller = new MediaController(VideoPlayerActivityNew.this);
                    mediacontroller.setAnchorView(videoView);
                    // Get the URL from String VideoURL
                    Uri video = Uri.parse(data);
                    videoView.setMediaController(mediacontroller);
                    videoView.setVideoURI(video);

                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }

                videoView.requestFocus();
                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    public void onPrepared(MediaPlayer mp) {
                        videoView.start();
                    }
                });
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (videoView.isPlaying()){
            videoView.pause();
            videoView.stopPlayback();
        }
        super.onBackPressed();
        finish();

    }
}
