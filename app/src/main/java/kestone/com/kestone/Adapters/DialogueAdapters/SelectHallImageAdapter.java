package kestone.com.kestone.Adapters.DialogueAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import kestone.com.kestone.R;

/**
 * Created by karan on 7/14/2017.
 */

public class SelectHallImageAdapter extends RecyclerView.Adapter<SelectHallImageAdapter.Holder> {
    LayoutInflater inflater;
    Context mContext;
    String[] images;

    public SelectHallImageAdapter(Context context, String [] im) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
        images = im;
    }

    public class Holder extends RecyclerView.ViewHolder{
        ImageView image;

        public Holder(View view) {
            super(view);
            image = (ImageView)view.findViewById(R.id.select_hall_images);
        }
    }
    @Override
    public SelectHallImageAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.select_hall_recyclerpager_item, parent, false);
        return new SelectHallImageAdapter.Holder(itemView);
    }

    @Override
    public void onBindViewHolder(SelectHallImageAdapter.Holder holder, int position) {
        Glide.with(mContext).load(images[position].trim()).placeholder(R.drawable.placeholder_big).crossFade().into(holder.image);

    }

    @Override
    public int getItemCount() {
        if (images!=null){
            return images.length;
        }else {
            return 0;
        }

    }
}
