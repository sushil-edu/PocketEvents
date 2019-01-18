package kestone.com.pocketevents.Adapters.MoreFragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import kestone.com.pocketevents.MODEL.Manager.MySingleton;
import kestone.com.pocketevents.R;
import kestone.com.pocketevents.UI.FullScreenImage;
import kestone.com.pocketevents.UI.VideoPlayerActivityNew;

public class MoreTrendingAdapter extends RecyclerView.Adapter<MoreTrendingAdapter.Holder> {
    Context mContext;
    int POS;

    public MoreTrendingAdapter(Context context, int pos) {
        mContext = context;
        POS = pos;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from( parent.getContext() )
                .inflate( R.layout.item_more_trending, parent, false );
        return new MoreTrendingAdapter.Holder( itemView );
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        holder.name.setText( MySingleton.getInstance().getMoreResponse().getResponse().get( 0 ).getTrending().get( POS ).getTrendingValues().get( position ).getHeading() );
        holder.title.setText( MySingleton.getInstance().getMoreResponse().getResponse().get( 0 ).getTrending().get( POS ).getTrendingValues().get( position ).getSubHeading() );
        holder.card.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!MySingleton.getInstance().getMoreResponse().getResponse().get(0).getTrending().get(POS).getTrendingValues().get(position).getURL().equals("")) {
                    Intent intent = new Intent(mContext, VideoPlayerActivityNew.class);
                    intent.putExtra("data", MySingleton.getInstance().getMoreResponse().getResponse().get(0).getTrending().get(POS).getTrendingValues().get(position).getURL().trim());
                    //For Testing
                    // intent.putExtra("data","https://www.youtube.com/watch?v=OQ0Gzf4KYq4");
                    mContext.startActivity(intent);
                } else {
                    if (!MySingleton.getInstance().getMoreResponse().getResponse().get(0).getTrending().get(POS).getTrendingValues().get(position).getImageURL().equals("")) {
                        Intent intent2 = new Intent(mContext, FullScreenImage.class);
                        intent2.putExtra("imageurl", MySingleton.getInstance().getMoreResponse().getResponse().get(0).getTrending().get(POS).getTrendingValues().get(position).getImageURL().trim());
                        mContext.startActivity(intent2);
                    }
                }
            }
        } );

        Glide.with( mContext ).load( MySingleton.getInstance().getMoreResponse().getResponse().get( 0 ).getTrending().get( POS ).getTrendingValues().get( position ).getImageURL().trim() ).placeholder( R.drawable.placeholder_big ).crossFade().into( holder.imageView );

    }

    @Override
    public int getItemCount() {
        return MySingleton.getInstance().getMoreResponse().getResponse().get( 0 ).getTrending().get( POS ).getTrendingValues().size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView name, title;
        ImageView imageView, videoButton;
        LinearLayout card;

        public Holder(View view) {
            super( view );
            name = (TextView) view.findViewById( R.id.trendingName );
            title = (TextView) view.findViewById( R.id.trendingTitle );
            imageView = (ImageView) view.findViewById( R.id.trendingImage );

            card = (LinearLayout) view.findViewById( R.id.card );

        }


    }
}
