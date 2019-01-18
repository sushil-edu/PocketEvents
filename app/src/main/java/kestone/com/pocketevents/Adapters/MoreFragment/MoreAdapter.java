package kestone.com.pocketevents.Adapters.MoreFragment;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import kestone.com.pocketevents.MODEL.Manager.MySingleton;
import kestone.com.pocketevents.R;
import kestone.com.pocketevents.Utilities.GeneralUtils;

/**
 * Created by karan on 10/17/2017.
 */

public class MoreAdapter extends RecyclerView.Adapter<MoreAdapter.Holder> {


    Context mContext;
    MoreAdapterCallback callback;

    public MoreAdapter(Context context, MoreAdapterCallback moreAdapterCallback) {
        mContext = context;
        callback = moreAdapterCallback;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (GeneralUtils.hasLollipop()){
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_venue_list, parent, false);

            return new MoreAdapter.Holder(itemView);
        }else {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_venue_list_kitkat, parent, false);

            return new MoreAdapter.Holder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        holder.text2.setVisibility(View.GONE);
        holder.text1.setText("Select\n"+MySingleton.getInstance().getMoreResponse().getResponse().get(0).getPayload().get(position).getMoreName());
        if (MySingleton.getInstance().getMoreResponse().getResponse().get(0).getPayload().get(position).getCount() >0){
            holder.card.setBackgroundColor(ContextCompat.getColor(mContext,R.color.textColorRed));
            holder.text1.setTextColor(ContextCompat.getColor(mContext,R.color.textColorWhite));
        }else {
            holder.card.setBackgroundColor(ContextCompat.getColor(mContext,R.color.textColorWhite));
            holder.text1.setTextColor(ContextCompat.getColor(mContext,R.color.textColorGrey));
        }
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               MySingleton.getInstance().setMorePOS(position);
                MySingleton.getInstance().setLabelMore(MySingleton.getInstance().getMoreResponse().getResponse().get(0).getPayload().get(position).getMoreName());
                callback.OnClick();
            }
        });
    }

    @Override
    public int getItemCount() {
        return MySingleton.getInstance().getMoreResponse().getResponse().get(0).getPayload().size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        LinearLayout card;
        TextView text1, text2;

        RelativeLayout root;

        public Holder(View view) {
            super(view);
            card = (LinearLayout)view.findViewById(R.id.venueFilterCard);
            text1 = (TextView)view.findViewById(R.id.item_venue_filter_text1);
            text2 = (TextView)view.findViewById(R.id.item_venue_filter_text2);
            root = (RelativeLayout)view.findViewById(R.id.venueFilterRoot);
        }


    }
    public  interface MoreAdapterCallback{
        void OnClick();
    }
}
