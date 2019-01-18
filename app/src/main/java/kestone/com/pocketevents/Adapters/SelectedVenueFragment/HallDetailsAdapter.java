package kestone.com.pocketevents.Adapters.SelectedVenueFragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import kestone.com.pocketevents.MODEL.HallDetails.HallDetails;
import kestone.com.pocketevents.MODEL.Venue.RESPONSE.Capacity;
import kestone.com.pocketevents.R;

public class HallDetailsAdapter extends RecyclerView.Adapter<HallDetailsAdapter.MyHolder> {

    LayoutInflater inflater;

    Context mContext;

    List<Capacity> list;

    public HallDetailsAdapter(Context context, List<Capacity> l) {
        list = l;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_small_filter_cards, parent, false);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        //holder.textView1.setText(list.get(position).getSeatingName());
        //holder.textView2.setText(list.get(position).getSeatingCapacity() + " PAX");

        if (position == 0) {
            holder.textView1.setText("Seating Style");
            holder.textView2.setText(HallDetails.getSeatingStyle());
        } else if (position == 2) {
            holder.textView1.setText("Hall Area");
            String tempArr[] = HallDetails.getHallArea().split("Sq. Ft.");
            holder.textView2.setText(tempArr[0]+" Sq. Ft.");
        } else if (position == 1) {
            holder.textView1.setText("Hall Capacity");
            holder.textView2.setText(HallDetails.getHallCapacity());
        }
        if (position == list.size() - 1) {
            holder.divider.setVisibility(View.GONE);
        } else {
            holder.divider.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView textView1, textView2;
        View divider;

        public MyHolder(View view) {
            super(view);
            textView1 = (TextView) view.findViewById(R.id.smallFilterText1);
            textView2 = (TextView) view.findViewById(R.id.smallFilterText2);
            divider = view.findViewById(R.id.divider);
        }
    }
}
