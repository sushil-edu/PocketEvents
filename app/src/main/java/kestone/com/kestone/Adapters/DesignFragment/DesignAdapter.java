package kestone.com.kestone.Adapters.DesignFragment;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import kestone.com.kestone.MODEL.Design.RESPONSE.Payload;
import kestone.com.kestone.MODEL.Design.RESPONSE.Values;
import kestone.com.kestone.R;
import kestone.com.kestone.Utilities.GeneralUtils;

/**
 * Created by karan on 10/13/2017.
 */

public class DesignAdapter extends RecyclerView.Adapter<DesignAdapter.Holder> {

    List<Payload> list;
    Context mContext;
    DesignCallback callback;


    public DesignAdapter(Context context, List<Payload> payload, DesignCallback designCallback) {
        mContext = context;
        callback =designCallback;
        list = payload;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (GeneralUtils.hasLollipop()){
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_venue_list, parent, false);
            return new DesignAdapter.Holder(itemView);
        }else {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_venue_list_kitkat, parent, false);
            return new DesignAdapter.Holder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {


            if (list.get(position).isSelected()){
                holder.card.setBackgroundColor(ContextCompat.getColor(mContext,R.color.textColorRed));
                holder.text1.setTextColor(ContextCompat.getColor(mContext,R.color.textColorWhite));
                holder.text2.setTextColor(ContextCompat.getColor(mContext,R.color.textColorWhite));
                holder.text2.setVisibility(View.GONE);
                holder.text2.setText("Selected");
                holder.text1.setText(list.get(position).getDsignName());
                holder.item_consulting_image.setVisibility(View.VISIBLE);

            }else {
                holder.card.setBackgroundColor(ContextCompat.getColor(mContext,R.color.textColorWhite));
                holder.text1.setTextColor(ContextCompat.getColor(mContext,R.color.textColorGrey));
                holder.text2.setVisibility(View.GONE);
                holder.text1.setText(list.get(position).getDsignName());
                holder.text2.setText("--");
                holder.item_consulting_image.setVisibility(View.GONE);
            }

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onDesignClick(list.get(position).getValues(),list.get(position).isSelected(),position,list.get(position).getDesignID(),list.get(position).getDsignName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        LinearLayout card;
       TextView text1, text2;
       ImageView item_consulting_image;

        RelativeLayout root;

        public Holder(View view) {
            super(view);
            card = (LinearLayout)view.findViewById(R.id.venueFilterCard);
            text1 = (TextView)view.findViewById(R.id.item_venue_filter_text1);
            text2 = (TextView)view.findViewById(R.id.item_venue_filter_text2);
            root = (RelativeLayout) view.findViewById(R.id.venueFilterRoot);
            item_consulting_image = (ImageView) view.findViewById(R.id.item_consulting_image);
            text2.setVisibility(View.GONE);
        }


    }
    public  interface DesignCallback {
        void onDesignClick(List<Values> data, boolean isSelected,int Position, String designId, String Title);
    }

    public void setSelected(int position){
        for (int i = 0; i<list.size();i++){
            if (i==position){
                list.get(i).setSelected(true);
            }else {
                list.get(i).setSelected(false);
            }
        }
    }
    public void setMultiSelect(int position){
        if(list.get(position).isSelected()){
            list.get(position).setSelected(false);
        }else {
            list.get(position).setSelected(true);
        }

        notifyDataSetChanged();
    }

    public String getSelected(){
        String selected = "";
        for (int i = 0; i< list.size(); i++){
            if (list.get(i).isSelected()){
                selected += list.get(i).getDesignID() + ",";
            }
        }
        if (selected.length()>1)
            selected = selected.substring(0,selected.length()-1);
        return  selected;
    }

}