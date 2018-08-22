package kestone.com.kestone.Adapters.DialogueAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import kestone.com.kestone.MODEL.HallDetails.HallDetails;
import kestone.com.kestone.MODEL.Venue.RESPONSE.HallSizes;
import kestone.com.kestone.R;

/**
 * Created by karan on 7/14/2017.
 */

public class SelectHallSizeAdapter extends BaseAdapter {
    LayoutInflater inflater;
    List<HallSizes> list;
    Context mContext;

    public SelectHallSizeAdapter(Context context, List<HallSizes> l) {
        mContext = context;
        list = l;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertview, ViewGroup viewGroup) {
        View view = convertview;


        if (convertview == null) {
            view = inflater.inflate(R.layout.select_hall_listview_item, viewGroup, false);
        }

        TextView textView1 = (TextView) view.findViewById(R.id.select_hall_size_text1);
        TextView textView2 = (TextView) view.findViewById(R.id.select_hall_size_text2);

        // textView1.setText(list.get(i).getSizeName()+" ( "+list.get(i).getDimensions()+" )");
        textView1.setText("Hall Area & Dimensions");
        HallDetails.setHallArea(list.get(i).getSize());
        textView2.setText(list.get(i).getSize());


        return view;
    }
}
