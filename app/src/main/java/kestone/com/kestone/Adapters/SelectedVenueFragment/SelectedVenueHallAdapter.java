package kestone.com.kestone.Adapters.SelectedVenueFragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kestone.com.kestone.MODEL.Manager.MySingleton;
import kestone.com.kestone.MODEL.Venue.RESPONSE.Halls;
import kestone.com.kestone.R;

/**
 * Created by karan on 8/10/2017.
 */

public class SelectedVenueHallAdapter extends RecyclerView.Adapter<SelectedVenueHallAdapter.Holder> {

    private Context mcontext;

    LayoutInflater inflater;

    List<Halls> halls = new ArrayList<>();
    int pos;


    public class Holder extends RecyclerView.ViewHolder {

        TextView text1;
        LinearLayout card;

        public Holder(View view) {
            super(view);
            text1 = (TextView)view.findViewById(R.id.halls_text);
            card = (LinearLayout)view.findViewById(R.id.innerHallsCard);

        }
    }


    public SelectedVenueHallAdapter(Context context, List<Halls> h,int position){

        mcontext = context;
        pos=position;
        for (int i = 0; i<h.size();i++){
            if (i == MySingleton.getInstance().getSelectedHallPosition())
                halls.add(h.get(i));
        }
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_inner_halls, parent, false);
        return new SelectedVenueHallAdapter.Holder(itemView);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        holder.card.setBackgroundColor(mcontext.getResources().getColor(R.color.btnColorGrey));
        holder.text1.setText(halls.get(position).getHallName());
    }

    @Override
    public int getItemCount() {
        return halls.size();
    }



}
