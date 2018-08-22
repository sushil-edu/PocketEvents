package kestone.com.kestone.Adapters.DialogueAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import kestone.com.kestone.MODEL.HallDetails.HallDetails;
import kestone.com.kestone.MODEL.Venue.RESPONSE.Capacity;
import kestone.com.kestone.R;

/**
 * Created by karan on 7/14/2017.
 */

public class SelectHallCapacityAdapter extends RecyclerView.Adapter<SelectHallCapacityAdapter.Holder>{
    LayoutInflater inflater;

    Context mContext;

    List<Capacity> list;


    public SelectHallCapacityAdapter(Context context, List<Capacity> l) {
        list = l;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
    }

    public class Holder extends RecyclerView.ViewHolder{
        TextView textView1, textView2;

        public Holder(View view) {
            super(view);
            textView1 = (TextView)view.findViewById(R.id.select_hall_capacity_text1);
            textView2 = (TextView)view.findViewById(R.id.select_hall_capacity_text2);
        }
    }

    @Override
    public SelectHallCapacityAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.select_hall_capacity_item, parent, false);
        return new SelectHallCapacityAdapter.Holder(itemView);
    }

    @Override
    public void onBindViewHolder(SelectHallCapacityAdapter.Holder holder, int position) {
        holder.textView1.setText(list.get(position).getSeatingName());
        holder.textView2.setText(list.get(position).getSeatingCapacity() + " PAX");

        if(list.get(position).getSeatingName().equals(HallDetails.getSeatingStyle())){
            HallDetails.setHallCapacity(list.get(position).getSeatingCapacity() + " PAX");
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

