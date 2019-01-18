package kestone.com.pocketevents.Adapters.VenueFilterFragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kestone.com.pocketevents.MODEL.Filters.RESPONSE.Payload;
import kestone.com.pocketevents.MODEL.HallDetails.HallDetails;
import kestone.com.pocketevents.MODEL.Manager.MySingleton;
import kestone.com.pocketevents.R;
import kestone.com.pocketevents.Utilities.GeneralUtils;

public class VenueFilterAdapter extends RecyclerView.Adapter<VenueFilterAdapter.Holder> {

    private Context mcontext;
    LayoutInflater inflater;


    List<Payload> list = new ArrayList<>();


    VenueFilterAdapterCallBack venueFilterAdapterCallBack;


    public VenueFilterAdapter(Context context, VenueFilterAdapterCallBack callBack) {
        mcontext = context;
        venueFilterAdapterCallBack = callBack;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    public class Holder extends RecyclerView.ViewHolder {

        LinearLayout card;
        TextView text1, text2;

        RelativeLayout root;

        public Holder(View view) {
            super(view);
            card = (LinearLayout) view.findViewById(R.id.venueFilterCard);
            text1 = (TextView) view.findViewById(R.id.item_venue_filter_text1);
            text2 = (TextView) view.findViewById(R.id.item_venue_filter_text2);
            root = (RelativeLayout) view.findViewById(R.id.venueFilterRoot);
        }
    }

    @Override
    public VenueFilterAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (GeneralUtils.hasLollipop()) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_venue_list, parent, false);

            return new VenueFilterAdapter.Holder(itemView);
        } else {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_venue_list_kitkat, parent, false);

            return new VenueFilterAdapter.Holder(itemView);
        }

    }

    @Override
    public void onBindViewHolder(final VenueFilterAdapter.Holder holder, final int position) {
        holder.text1.setText(MySingleton.getInstance().getPayload1().get(position).getFilterName());

        Log.e("Filter Name ", MySingleton.getInstance().getPayload1().get( position ).getFilterName());

        if (MySingleton.getInstance().getPayload1().get(position).isSelected()) {
            if (MySingleton.getInstance().getPayload1().get(position).getStyle().equals("2")) {
                if (MySingleton.getInstance().getPayload1().get(position).getFilterName().equals("Hall Capacity")) {

                    int capacity = ((Integer.parseInt(MySingleton.getInstance().getPayload1().get(position).getRanges().getLowerLimit())) +
                            (Integer.parseInt(MySingleton.getInstance().getPayload1().get(position).getRanges().getUpperLimit()))) / 2;
                    if(capacity<=50) {
//                        holder.text2.setText( capacity + "" );
                        holder.text2.setText( 50 + "" );
                    }else if(capacity<=100) {
                        holder.text2.setText( 100 + "" );
                    }else if(capacity>100) {
                        holder.text2.setText( 150 + "" );
                    }
                } else {
                    holder.text2.setText(MySingleton.getInstance().getPayload1().get(position).getRanges().getLowerLimit() + "-" + MySingleton.getInstance().getPayload1().get(position).getRanges().getUpperLimit());
                }
            } else {
                for (int i = 0; i < MySingleton.getInstance().getPayload1().get(position).getValues().size(); i++) {
                    Log.e("Style ", MySingleton.getInstance().getPayload1().get(position).getValues().get(i).getNameLabel());
                    if (MySingleton.getInstance().getPayload1().get(position).getFilterName().equals("Seating Style")) {
                        HallDetails.setSeatingStyle(MySingleton.getInstance().getPayload1().get(position).getValues().get(i).getNameLabel());
                        holder.text2.setText(MySingleton.getInstance().getPayload1().get(position).getValues().get(i).getNameLabel());
                    }

                    if (MySingleton.getInstance().getPayload1().get(position).getValues().get(i).isSelected()) {
                        holder.text2.setText(MySingleton.getInstance().getPayload1().get(position).getValues().get(i).getNameLabel());
                        break;
                    }
                }
            }


        } else {
            holder.text2.setText("--");
            HallDetails.setSeatingStyle("");

        }

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MySingleton.getInstance().getPayload1().get(position).getStyle().equals("0")) {
                    if (MySingleton.getInstance().getPayload1().get(position).getSelectiontype().equals("RadioButton")) {
                        venueFilterAdapterCallBack.onClickCallBack(position, 0, 0, holder.text1.getText().toString(), holder.text2.getText().toString());
                    } else {
                        venueFilterAdapterCallBack.onClickCallBack(position, 0, 1, holder.text1.getText().toString(), holder.text2.getText().toString());
                    }

                } else if (MySingleton.getInstance().getPayload1().get(position).getStyle().equals("1")) {
                    if (MySingleton.getInstance().getPayload1().get(position).getSelectiontype().equals("RadioButton")) {
                        venueFilterAdapterCallBack.onClickCallBack(position, 1, 0, holder.text1.getText().toString(), holder.text2.getText().toString());
                    } else {
                        venueFilterAdapterCallBack.onClickCallBack(position, 1, 1, holder.text1.getText().toString(), holder.text2.getText().toString());
                    }


                } else {
                    venueFilterAdapterCallBack.onClickCallBack(position, 2, 1, holder.text1.getText().toString(), holder.text2.getText().toString());
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return MySingleton.getInstance().getPayload1().size();
    }


    public interface VenueFilterAdapterCallBack {
        void onClickCallBack(int i, int mode, int selection, String text, String text2);

    }


}
