package kestone.com.kestone.Adapters.DesignFragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import kestone.com.kestone.MODEL.Design.RESPONSE.Values;
import kestone.com.kestone.R;

/**
 * Created by karan on 10/16/2017.
 */

public class DesignDialogueAdapter extends RecyclerView.Adapter<DesignDialogueAdapter.Holder> {
    List<Values> list;
    Context mContext;


    public DesignDialogueAdapter(Context context, List<Values> data) {
        mContext = context;
        list = data;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_design_dialogue, parent, false);
        return new DesignDialogueAdapter.Holder(itemView);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        holder.textView.setText(list.get(position).getName());
        Glide.with(mContext).load(list.get(position).getURL()).crossFade().placeholder(R.drawable.placeholder_big).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public Holder(View view) {
            super(view);
            textView = (TextView)view.findViewById(R.id.designText);
            imageView = (ImageView)view.findViewById(R.id.designImage);
        }
    }


}
