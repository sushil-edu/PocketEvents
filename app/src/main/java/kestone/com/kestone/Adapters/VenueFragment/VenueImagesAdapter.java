package kestone.com.kestone.Adapters.VenueFragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import kestone.com.kestone.R;

/**
 * Created by karan on 7/24/2017.
 */

public class VenueImagesAdapter extends RecyclerView.Adapter<VenueImagesAdapter.Holder> {
    String [] images;
    Context mContext;

    public VenueImagesAdapter(Context context, String[] i) {
        mContext = context;
        images = i;
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView image;

        public Holder(View view) {
            super(view);
            image = (ImageView)view.findViewById(R.id.venue_images_imageView);
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_venue_images, parent, false);
        return new VenueImagesAdapter.Holder(itemView);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        if (images.length>0)
            Glide.with(mContext).load(images[position].trim()).crossFade().placeholder(R.drawable.placeholder_big).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }


}
