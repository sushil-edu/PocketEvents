package kestone.com.kestone.Adapters.VenueFragment;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SingleSelector;
import com.bumptech.glide.Glide;

import java.util.Arrays;

import kestone.com.kestone.MODEL.Manager.MySingleton;
import kestone.com.kestone.MODEL.Venue.RESPONSE.Halls;
import kestone.com.kestone.MODEL.Venue.RESPONSE.Payload;
import kestone.com.kestone.R;
import kestone.com.kestone.Utilities.StorageUtilities;

/**
 * Created by karan on 7/7/2017.
 */

public class VenueCardInnerHallAdapter extends RecyclerView.Adapter<VenueCardInnerHallAdapter.Holder> {


    private Context mcontext;
    LayoutInflater inflater;

    String hName;

    int selected = 9999;

    int POSITION;

    MultiSelector selector;

    InnerHallAdapterCallBack innerHallAdapterCallBack;

    StorageUtilities storage;


    public VenueCardInnerHallAdapter(Context context, int p, String hotelName, InnerHallAdapterCallBack callBack) {
        innerHallAdapterCallBack = callBack;
        hName = hotelName;
        selector = new SingleSelector();
        POSITION = p;
        storage = new StorageUtilities(context);
        mcontext = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    public class Holder extends RecyclerView.ViewHolder {

        TextView text1, addToCompareTv, selectTv;
        LinearLayout card;
        ImageView hallIv;
        RelativeLayout cornerRl;

        public Holder(View view) {
            super(view);
            text1 = (TextView) view.findViewById(R.id.halls_text);
            card = (LinearLayout) view.findViewById(R.id.innerHallsCard);
            hallIv = (ImageView) view.findViewById(R.id.hallIv);
            cornerRl = (RelativeLayout) view.findViewById(R.id.cornerRl);
            addToCompareTv = (TextView) view.findViewById(R.id.addToCompareTv);
            selectTv = (TextView) view.findViewById(R.id.selectTv);
        }
    }


    @Override
    public VenueCardInnerHallAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_inner_halls, parent, false);
        return new VenueCardInnerHallAdapter.Holder(itemView);
    }

    @Override
    public void onBindViewHolder(VenueCardInnerHallAdapter.Holder holder, final int position) {

        if (MySingleton.getInstance().getSelectedHallVenuePosition() == POSITION && MySingleton.getInstance().getSelectedHallPosition() == position) {
            //holder.card.setBackground(mcontext.getResources().getDrawable(R.drawable.btn_design_grey_solid));
            holder.text1.setTextColor(ContextCompat.getColor(mcontext, R.color.textColorBlack));
            holder.cornerRl.setVisibility(View.VISIBLE);
        } else {
            // holder.card.setBackground(mcontext.getResources().getDrawable(R.drawable.btn_design_grey_solid));
            holder.text1.setTextColor(ContextCompat.getColor(mcontext, R.color.textColorBlack));
            holder.cornerRl.setVisibility(View.GONE);
        }

        if (Boolean.valueOf(MySingleton.getInstance().getVenues().getResponse().get(0).getPayload().get(POSITION).getHalls().get(position).getIsRecommended())) {
            holder.card.setBackground(mcontext.getResources().getDrawable(R.drawable.hall_bg));
            holder.text1.setTextColor(ContextCompat.getColor(mcontext, R.color.textColorBlack));
        }

        holder.text1.setText(styleString(MySingleton.getInstance().getVenues().getResponse().get(0).getPayload().get(POSITION).getHalls().get(position).getHallName()));
        if (MySingleton.getInstance().getVenues().getResponse().get(0).getPayload().get(POSITION).getHalls().get(position).getHallImages().length > 0) {
            Glide.with(mcontext).load(MySingleton.getInstance().getVenues().getResponse().get(0).getPayload().get(POSITION).getHalls().get(position).getHallImages()[0]).crossFade().placeholder(R.drawable.placeholder).into(holder.hallIv);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                innerHallAdapterCallBack.onClickHall(MySingleton.getInstance().getVenues().getResponse().get(0).getPayload().get(POSITION).getHalls().get(position), hName, POSITION, position,
                        MySingleton.getInstance().getVenues().getResponse().get(0).getPayload().get(POSITION), false);
                selector.setSelected(position, 0, true);
                selected = position;
            }
        });

        holder.addToCompareTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                innerHallAdapterCallBack.onClickCompare(MySingleton.getInstance().getVenues().getResponse().get(0).getPayload().get(POSITION).getHalls().get(position), hName, POSITION, position,
                        MySingleton.getInstance().getVenues().getResponse().get(0).getPayload().get(POSITION), false);
                selector.setSelected(position, 0, true);
                selected = position;
            }
        });

        holder.selectTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                innerHallAdapterCallBack.onClickSelect(MySingleton.getInstance().getVenues().getResponse().get(0).getPayload().get(POSITION).getHalls().get(position), hName, POSITION, position,
//                        MySingleton.getInstance().getVenues().getResponse().get(0).getPayload().get(POSITION), false);
//                selector.setSelected(position, 0, true);
//                selected = position;

                innerHallAdapterCallBack.onClickHall(MySingleton.getInstance().getVenues().getResponse().get(0).getPayload().get(POSITION).getHalls().get(position), hName, POSITION, position,
                        MySingleton.getInstance().getVenues().getResponse().get(0).getPayload().get(POSITION), false);
                selector.setSelected(position, 0, true);
                selected = position;

            }
        });


    }

    @Override
    public int getItemCount() {
        return MySingleton.getInstance().getVenues().getResponse().get(0).getPayload().get(POSITION).getHalls().size();
    }

    public interface InnerHallAdapterCallBack {
        void onClickHall(Halls halls, String HotelName, int POSITION, int position, Payload payload, boolean refresh);

        void onClickCompare(Halls halls, String HotelName, int POSITION, int position, Payload payload, boolean refresh);

        void onClickSelect(Halls halls, String HotelName, int POSITION, int position, Payload payload, boolean refresh);
    }

    void OpenDialogue(int pos, int POS) {
        MySingleton.getInstance().setSelectedHallVenuePosition(POS);
        MySingleton.getInstance().setSelectedHallPosition(pos);
        storage.storeHallID(MySingleton.getInstance().getVenues().getResponse().get(0).getPayload().get(POS).getHalls().get(pos).getHallId());
        storage.storeVenueID(MySingleton.getInstance().getVenues().getResponse().get(0).getPayload().get(POS).getVenueID());
//        innerHallAdapterCallBack.onClickHall(MySingleton.getInstance().getVenues().getResponse().get(0).getPayload().get(POS).getHalls().get(pos), hName,POS,pos,
//                MySingleton.getInstance().getVenues().getResponse().get(0).getPayload().get(POS),true);
        selector.setSelected(pos, 0, true);
        selected = pos;
    }

    private String styleString(String str) {
        str = str.toLowerCase();
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
