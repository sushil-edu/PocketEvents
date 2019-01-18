package kestone.com.pocketevents.Adapters.VenueFragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import kestone.com.pocketevents.MODEL.HallDetails.HallDetails;
import kestone.com.pocketevents.MODEL.Manager.MySingleton;
import kestone.com.pocketevents.R;

/**
 * Created by karan on 7/7/2017.
 */

public class VenueSmallFilterAdapter extends RecyclerView.Adapter<VenueSmallFilterAdapter.Holder> {

    private Context mcontext;
    LayoutInflater inflater;

    VenueSmallFilterAdapterCallBack venueSmallFilterAdapterCallBack;


    public VenueSmallFilterAdapter(Context context, VenueSmallFilterAdapterCallBack callBack) {
        venueSmallFilterAdapterCallBack = callBack;

        mcontext = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public class Holder extends RecyclerView.ViewHolder {

        TextView text1, text2;
        RelativeLayout card;

        public Holder(View view) {
            super(view);
            text1 = (TextView) view.findViewById(R.id.smallFilterText1);
            text2 = (TextView) view.findViewById(R.id.smallFilterText2);
            card = (RelativeLayout) view.findViewById(R.id.smallFilterCard);
        }
    }

    @Override
    public VenueSmallFilterAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_small_filter_cards, parent, false);
        return new VenueSmallFilterAdapter.Holder(itemView);
    }

    @Override
    public void onBindViewHolder(VenueSmallFilterAdapter.Holder holder, final int position) {
        if (position > MySingleton.getInstance().getPayload1().size() - 1) {
            holder.text1.setText("More Filters");
            holder.text2.setText("More Filters");
            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    venueSmallFilterAdapterCallBack.moreFilterCallback();
                }
            });
        } else {
            holder.text1.setText(MySingleton.getInstance().getPayload1().get(position).getFilterName());

            if (MySingleton.getInstance().getPayload1().get(position).isSelected()) {
                if (MySingleton.getInstance().getPayload1().get(position).getStyle().equals("2")) {

                    if (MySingleton.getInstance().getPayload1().get(position).getFilterName().equals("Hall Capacity")) {
//                        holder.text2.setText(((Integer.parseInt(MySingleton.getInstance().getPayload1().get(position).getRanges().getLowerLimit()))
//                                + (Integer.parseInt(MySingleton.getInstance().getPayload1().get(position).getRanges().getUpperLimit())))/2+"");
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
                        if (MySingleton.getInstance().getPayload1().get(position).getFilterName().equals("Seating Style")) {
                            HallDetails.setSeatingStyle(MySingleton.getInstance().getPayload1().get(position).getValues().get(i).getNameLabel());
                        }
                        if (MySingleton.getInstance().getPayload1().get(position).getValues().get(i).isSelected()) {
                            holder.text2.setText(MySingleton.getInstance().getPayload1().get(position).getValues().get(i).getNameLabel());
                            break;
                        }
                    }
                }

            } else {
                holder.text2.setText("--");
            }

            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (MySingleton.getInstance().getPayload1().get(position).getStyle().equals("0")) {
                        if (MySingleton.getInstance().getPayload1().get(position).getSelectiontype().equals("RadioButton")) {
                            venueSmallFilterAdapterCallBack.onClickCallBack(position, 0, 0);
                        } else {
                            venueSmallFilterAdapterCallBack.onClickCallBack(position, 0, 1);
                        }

                    } else if (MySingleton.getInstance().getPayload1().get(position).getStyle().equals("1")) {
                        if (MySingleton.getInstance().getPayload1().get(position).getSelectiontype().equals("RadioButton")) {
                            venueSmallFilterAdapterCallBack.onClickCallBack(position, 1, 0);
                        } else {
                            venueSmallFilterAdapterCallBack.onClickCallBack(position, 1, 1);
                        }


                    } else {
                        venueSmallFilterAdapterCallBack.onClickCallBack(position, 2, 1);
                    }

                }
            });
        }

    }

    public interface VenueSmallFilterAdapterCallBack {
        void onClickCallBack(int i, int mode, int selection);

        void moreFilterCallback();
    }

    @Override
    public int getItemCount() {
        return MySingleton.getInstance().getPayload1().size() + 1;
    }
}
