package kestone.com.kestone.Adapters.VenueFragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import java.util.Locale;

import kestone.com.kestone.Fragments.VenueFragment;
import kestone.com.kestone.MODEL.Manager.MySingleton;
import kestone.com.kestone.MODEL.Venue.RESPONSE.Payload;
import kestone.com.kestone.R;

/**
 * Created by karan on 7/5/2017.
 */

public class VenueCardAdapter extends RecyclerView.Adapter<VenueCardAdapter.Holder> {

    private Context mcontext;

    LayoutInflater inflater;
    TextView textView;


    //VenueCardInnerListAdapter venueCardInnerListAdapter;
    VenueCardInnerRVAdapter venueCardInnerRVAdapter;


    VenueCardInnerHallAdapter venueCardInnerHallAdapter;

    VenueCardAdapterCallBack venueCardAdapterCallBack;

    VenueCardInnerHallAdapter.InnerHallAdapterCallBack hallCallback;


    public class Holder extends RecyclerView.ViewHolder {
        TextView venueName, venueAddress, venueHalls, keystoneRating, tripadvisorRating, numberOfImages;
        TextView btnCall, btnCompare, btnMap;
        RecyclerView filterList;
        RecyclerView hallGrid;
        ImageView isrecomended, image;
        RatingBar kestone;

        public Holder(View view) {
            super(view);
            venueName = (TextView) view.findViewById(R.id.card_venue_name);
            venueAddress = (TextView) view.findViewById(R.id.card_venue_add);
            venueHalls = (TextView) view.findViewById(R.id.card_hall_matching);
            keystoneRating = (TextView) view.findViewById(R.id.card_venue_kestone_rating);
            //tripadvisorRating = (TextView) view.findViewById(R.id.card_venue_tripadvisor_rating);
            isrecomended = (ImageView) view.findViewById(R.id.card_venue_isrecomended);
            image = (ImageView) view.findViewById(R.id.venue_card_thumb);
            numberOfImages = (TextView) view.findViewById(R.id.venue_card_text_numberOfImages);
            kestone = (RatingBar) view.findViewById(R.id.card_kestone_rating);

            btnCall = (TextView) view.findViewById(R.id.venue_card_btn_call);
            btnCompare = (TextView) view.findViewById(R.id.venue_card_btn_compare);

            btnMap = (TextView) view.findViewById(R.id.venue_card_btn_map);

            filterList = (RecyclerView) view.findViewById(R.id.card_list_details);
            hallGrid = (RecyclerView) view.findViewById(R.id.card_rv_halls);

        }
    }

    public VenueCardAdapter(Context context, VenueCardAdapterCallBack callBack, VenueCardInnerHallAdapter.InnerHallAdapterCallBack call, TextView textView) {
        venueCardAdapterCallBack = callBack;
        hallCallback = call;

        mcontext = context;
        this.textView = textView;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public VenueCardAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.venue_cards, parent, false);
        return new VenueCardAdapter.Holder(itemView);
    }

    @Override
    public void onBindViewHolder(VenueCardAdapter.Holder holder, final int position) {
        venueCardInnerHallAdapter = new VenueCardInnerHallAdapter(mcontext, position,
                MySingleton.getInstance().getVenues().getResponse().get(0).getPayload().get(position).getVenueName(), hallCallback);

        //holder.venueHalls.setText("Halls at " + MySingleton.getInstance().getVenues().getResponse().get(0).getPayload().get(position).getVenueName() + " matching your criteria");
        holder.venueHalls.setText("Halls at this hotel matching your criteria");

        //venueCardInnerListAdapter = new VenueCardInnerListAdapter(mcontext, MySingleton.getInstance().getVenues().getResponse().get(0).getPayload().get(position).getAttributes());
        venueCardInnerRVAdapter = new VenueCardInnerRVAdapter(mcontext, MySingleton.getInstance().getVenues().getResponse().get(0).getPayload().get(position).getAttributes());

        holder.venueName.setText(styleString(MySingleton.getInstance().getVenues().getResponse().get(0).getPayload().get(position).getVenueName()));


        holder.venueAddress.setText(MySingleton.getInstance().getVenues().getResponse().get(0).getPayload().get(position).getVenueAddress());
        if (MySingleton.getInstance().getVenues().getResponse().get(0).getPayload().get(position).getVenueImages().length == 0) {
            holder.numberOfImages.setText("No Images");
        } else if (MySingleton.getInstance().getVenues().getResponse().get(0).getPayload().get(position).getVenueImages().length == 1) {
            holder.numberOfImages.setText("1 Image");
        } else {
            holder.numberOfImages.setText(String.valueOf(MySingleton.getInstance().getVenues().getResponse().get(0).getPayload().get(position).getVenueImages().length) + " Images");
        }

        if (MySingleton.getInstance().getVenues().getResponse().get(0).getPayload().get(position).isAddedToCompare()) {
            holder.btnCompare.setEnabled(false);
            holder.btnCompare.setBackground(mcontext.getResources().getDrawable(R.drawable.btn_design_solid));
            holder.btnCompare.setTextColor(mcontext.getResources().getColor(R.color.textColorWhite));
        } else {
            holder.btnCompare.setEnabled(true);
            holder.btnCompare.setBackground(mcontext.getResources().getDrawable(R.drawable.btn_design_stroke));
            holder.btnCompare.setTextColor(mcontext.getResources().getColor(R.color.textColorRed));
        }
        if (MySingleton.getInstance().getVenues().getResponse().get(0).getPayload().get(position).getVenueImages().length > 0 && MySingleton.getInstance().getVenues().getResponse().get(0).getPayload().get(position).getVenueImages() != null) {
            Glide.with(mcontext).load(MySingleton.getInstance().getVenues().getResponse().get(0).getPayload().get(position).getVenueImages()[0]).crossFade().placeholder(R.drawable.placeholder).into(holder.image);
        }

        holder.keystoneRating.setText(MySingleton.getInstance().getVenues().getResponse().get(0).getPayload().get(position).getKeystoneRating());
        holder.kestone.setRating(Float.valueOf(MySingleton.getInstance().getVenues().getResponse().get(0).getPayload().get(position).getKeystoneRating()));

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                venueCardAdapterCallBack.onImageClick(MySingleton.getInstance().getVenues().getResponse().get(0).getPayload().get(position).getVenueImages());
            }
        });


//        holder.btnSelect.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                venueCardAdapterCallBack.onSelectClick(MySingleton.getInstance().getVenues().getResponse().get(0).getPayload().get(position).getVenueID(), MySingleton.getInstance().getVenues().getResponse().get(0).getPayload().get(position));
//            }
//        });

        //holder.filterList.setAdapter(venueCardInnerListAdapter);
        holder.filterList.setLayoutManager(new LinearLayoutManager(mcontext, LinearLayoutManager.HORIZONTAL, false));
        holder.filterList.setAdapter(venueCardInnerRVAdapter);

        if (Boolean.valueOf(MySingleton.getInstance().getVenues().getResponse().get(0).getPayload().get(position).getIsRecomended())) {
            holder.isrecomended.setVisibility(View.VISIBLE);
        }
        RecyclerViewPager.OnItemTouchListener onItemTouchListener = new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                int action = e.getAction();
                switch (action) {
                    case MotionEvent.ACTION_MOVE:
                        rv.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        };

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mcontext, LinearLayoutManager.HORIZONTAL, false);
        holder.hallGrid.setLayoutManager(layoutManager);
        holder.hallGrid.setItemAnimator(new DefaultItemAnimator());
        holder.hallGrid.setAdapter(venueCardInnerHallAdapter);
        holder.hallGrid.setNestedScrollingEnabled(false);
        holder.hallGrid.addOnItemTouchListener(onItemTouchListener);
        layoutManager.scrollToPosition(MySingleton.getInstance().getSelectedHallPosition());

        holder.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                venueCardAdapterCallBack.onPhoneClick(MySingleton.getInstance().getVenues().getResponse().get(0).getPayload().get(position).getVenuePhone());
            }
        });

        holder.btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String uri = String.format(Locale.ENGLISH, "geo:%s,%s", MySingleton.getInstance().getVenues().getResponse().get(0).getPayload().get(position).getVenueLat(), MySingleton.getInstance().getVenues().getResponse().get(0).getPayload().get(position).getVenueLon());
                String uri = String.format(Locale.ENGLISH, "geo:%s,%s?z=25&q=%s", MySingleton.getInstance().getVenues().getResponse().get(0).getPayload().get(position).getVenueLat(), MySingleton.getInstance().getVenues().getResponse().get(0).getPayload().get(position).getVenueLon(), MySingleton.getInstance().getVenues().getResponse().get(0).getPayload().get(position).getVenueName());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                mcontext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        Log.d("Venue Size",MySingleton.getInstance().getVenues().getResponse().get(0).getPayload().size()+"");
        return MySingleton.getInstance().getVenues().getResponse().get(0).getPayload().size();
    }

    public interface VenueCardAdapterCallBack {
        void onPhoneClick(String[] phone);

        void onAddtoCompareClick(Payload payload, int position);

        void onImageClick(String[] images);

        void onSelectClick(String VenueId, Payload venues);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    public void OpenHallDialogue(int POSITION, int position) {
        venueCardInnerHallAdapter.OpenDialogue(position, POSITION);
    }

    private String styleString(String str) {
        str = str.toLowerCase();
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }


}
