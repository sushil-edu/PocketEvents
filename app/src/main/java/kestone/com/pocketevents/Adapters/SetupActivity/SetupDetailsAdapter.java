package kestone.com.pocketevents.Adapters.SetupActivity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import kestone.com.pocketevents.MODEL.Setup.Detail;
import kestone.com.pocketevents.MODEL.Setup.RESPONSE.Details;
import kestone.com.pocketevents.R;

/**
 * Created by karan on 10/5/2017.
 */

public class SetupDetailsAdapter extends RecyclerView.Adapter<SetupDetailsAdapter.Holder> {
//    List<Details> data;
    List<Detail> data;

    public SetupDetailsAdapter(List<Detail> details) {
        data = details;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_setup, parent, false);
        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.text1.setText(data.get(position).getLabel());
        holder.text2.setText(data.get(position).getValues());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView text1, text2;

        public Holder(View view) {
            super(view);

            text1 = (TextView)view.findViewById(R.id.SetupAttributes);
            text2 = (TextView)view.findViewById(R.id.SetupValues);
        }
    }
}
