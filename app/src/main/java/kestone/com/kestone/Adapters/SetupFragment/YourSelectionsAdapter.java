package kestone.com.kestone.Adapters.SetupFragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import kestone.com.kestone.MODEL.Setup.RESPONSE.SelectedFilters;
import kestone.com.kestone.R;

/**
 * Created by karan on 8/25/2017.
 */

public class YourSelectionsAdapter extends RecyclerView.Adapter<YourSelectionsAdapter.Holder> {

    String[] s1 = {"Seating", "Capacity", "Hall Area"};

    String[] s2 = {"Round Table", "150-200 Pax", "1320 Sq Ft"};

    LayoutInflater inflater;

    List<SelectedFilters> data;

    Context mContext;

    public YourSelectionsAdapter(Context Context, List<SelectedFilters> selectedFilters) {
        mContext = Context;
        data = selectedFilters;
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView text1, text2;
        View divider;

        public Holder(View view) {
            super(view);
            text1 = (TextView) view.findViewById(R.id.smallFilterText1);
            text2 = (TextView) view.findViewById(R.id.smallFilterText2);
            divider = view.findViewById(R.id.divider);
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_small_filter_cards, parent, false);
        return new YourSelectionsAdapter.Holder(itemView);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.text1.setText(data.get(position).getLabel());
        if (data.get(position).getLabel().equals("Hall Capacity")) {
            String arr[] = data.get(position).getSelectedValue().split("-");
            int avg = (Integer.parseInt(arr[0])+Integer.parseInt(arr[1]))/2;

            holder.text2.setText(avg+" PAX");

        }else if (data.get(position).getLabel().equals("Hall Area")) {
            holder.text2.setText(data.get(position).getSelectedValue().replace("Sqft","Sq. Ft."));
        }
        else {
            holder.text2.setText(data.get(position).getSelectedValue());
        }
        if (position == data.size() - 1) {
            holder.divider.setVisibility(View.GONE);
        } else {
            holder.divider.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


}
