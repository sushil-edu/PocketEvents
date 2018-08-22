package kestone.com.kestone.Adapters.ConsultingServiceActivity;

import android.content.Context;
import android.graphics.drawable.TransitionDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import kestone.com.kestone.MODEL.Consulting.Payload;
import kestone.com.kestone.R;
import kestone.com.kestone.Utilities.GeneralUtils;

/**
 * Created by karan on 10/24/2017.
 */

public class ConsultingSelectionAdapter extends RecyclerView.Adapter<ConsultingSelectionAdapter.Holder>{
    Context mContext;
    ConsultingAdapterCallBack callBack;

    List<Payload> list;

    TransitionDrawable trans;

    public ConsultingSelectionAdapter(Context context, List<Payload> data) {
        list =data;
        mContext = context;
        callBack = (ConsultingAdapterCallBack) context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (GeneralUtils.hasLollipop()){
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_consulting, parent, false);
            return new ConsultingSelectionAdapter.Holder(itemView);
        }else {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_consulting_kitkat, parent, false);
            return new ConsultingSelectionAdapter.Holder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        holder.text1.setText(list.get(position).getName());
        if (list.get(position).isSelected()){
            holder.card.setBackgroundColor(ContextCompat.getColor(mContext,R.color.textColorRed));
            holder.text1.setTextColor(ContextCompat.getColor(mContext,R.color.textColorWhite));
        }else {
            holder.card.setBackgroundColor(ContextCompat.getColor(mContext,R.color.textColorWhite));
            holder.text1.setTextColor(ContextCompat.getColor(mContext,R.color.textColorGrey));
        }
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.get(position).isSelected()){
                    list.get(position).setSelected(false);
                    callBack.onClick(Integer.parseInt(list.get(position).getAmount()),false,position);
                }else{
                    list.get(position).setSelected(true);
                    callBack.onClick(Integer.parseInt(list.get(position).getAmount()),true,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        LinearLayout card;
        TextView text1;
        ImageView check;

        LinearLayout root;

        public Holder(View view) {
            super(view);
            card = (LinearLayout) view.findViewById(R.id.venueFilterCard);
            text1 = (TextView)view.findViewById(R.id.item_consulting_text);
            check = (ImageView) view.findViewById(R.id.item_consulting_image);
            root = (LinearLayout)view.findViewById(R.id.venueFilterRoot);
        }


    }

    public interface ConsultingAdapterCallBack {
        void onClick(int amount, boolean sel,int position);
    }


}
