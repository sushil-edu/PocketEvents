package kestone.com.kestone.Adapters.VenueFragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import kestone.com.kestone.MODEL.Venue.RESPONSE.Attributes;
import kestone.com.kestone.R;

/**
 * Created by pankaj on 3/26/18.
 */

public class VenueCardInnerRVAdapter extends RecyclerView.Adapter<VenueCardInnerRVAdapter.MyHolder> {


    private Context mcontext;
    List<Attributes> attributes;


    public VenueCardInnerRVAdapter(Context context, List<Attributes> l) {
        attributes = l;
        mcontext = context;
    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.venu_card_inner_rv, parent, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        holder.textView1.setText(attributes.get(position).getAttrName());
        if (position == 1) {
            holder.textView2.setText("INR "+attributes.get(position).getAttrValue());
        } else if (position == 2) {
            holder.textView2.setText(attributes.get(position).getAttrValue()+ " KM");
        } else {
            holder.textView2.setText(attributes.get(position).getAttrValue());
        }

        if (position + 1 == attributes.size()) {
            holder.divider.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return attributes.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {

        TextView textView1, textView2;
        View divider;

        public MyHolder(View itemView) {
            super(itemView);
            textView1 = (TextView) itemView.findViewById(R.id.venue_card_inner_list_text1);
            textView2 = (TextView) itemView.findViewById(R.id.venue_card_inner_list_text2);
            divider = itemView.findViewById(R.id.divider);
        }
    }

}
