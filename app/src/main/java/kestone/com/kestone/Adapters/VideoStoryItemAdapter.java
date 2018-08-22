package kestone.com.kestone.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;


import kestone.com.kestone.R;

/**
 * Created by BUGLE4 on 02-Nov-17.
 */

public class VideoStoryItemAdapter extends RecyclerView.Adapter<VideoStoryItemAdapter.ViewHolder> {

    //  private final Context context;
    private int playPosition = -1;

    private boolean isPaused = false;

 /*   public VideoStoryItemAdapter(Context context, ArrayList<VideoResponse.VideoStory> newsDetailArrayList) {
            this.context = context;

    }*/

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_list_single_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        holder.cvVideo.setLayoutParams(layoutParams);
        holder.tvVideo.setText("Video");


        holder.ivPlayVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.videoView.start();
                holder.videoView.setVisibility(View.VISIBLE);
                holder.ivThumbnail.setVisibility(View.GONE);
                holder.ivPlayVideo.setVisibility(View.GONE);
                holder.ivFullVideo.setVisibility(View.VISIBLE);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.videoView.pause();
                holder.videoView.setVisibility(View.VISIBLE);
                holder.ivThumbnail.setVisibility(View.GONE);
                holder.ivPlayVideo.setVisibility(View.VISIBLE);
                holder.ivFullVideo.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        // return newsDetailArrayList.size();
        return 4;
    }

    public void setStopPlaying() {
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView tvVideo;
        protected LinearLayout cvVideo;
        protected ImageView ivPlayVideo;
        protected ImageView ivFullVideo;
        protected ImageView ivThumbnail;
        protected VideoView videoView;
        protected WebView webViewVideo;


        public ViewHolder(View itemView) {
            super(itemView);
            tvVideo = (TextView) itemView.findViewById(R.id.tv_video_list_item);
            cvVideo = (LinearLayout) itemView.findViewById(R.id.cv_video_list_item);
            ivPlayVideo = (ImageView) itemView.findViewById(R.id.iv_play_video_list_item);
            ivFullVideo = (ImageView) itemView.findViewById(R.id.iv_full_video_list_item);
            ivThumbnail = (ImageView) itemView.findViewById(R.id.vv_video_play_item);
            videoView = (VideoView) itemView.findViewById(R.id.iv_video_thumbnail);
            webViewVideo = (WebView) itemView.findViewById(R.id.wv_video_play_item);
        }
    }
}
