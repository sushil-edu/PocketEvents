package kestone.com.kestone.Adapters.VenueFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import kestone.com.kestone.MODEL.Venue.RESPONSE.Attributes;
import kestone.com.kestone.R;

/**
 * Created by karan on 7/7/2017.
 */

public class VenueCardInnerListAdapter extends BaseAdapter {

    private Context mcontext;
    HashMap<String, String> map = new HashMap<>();

    List<Attributes> attributes;
    private LayoutInflater inflater = null;

    public VenueCardInnerListAdapter(Context context, List<Attributes> l)
    {
        attributes = l;
        mcontext = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return attributes.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertview, ViewGroup viewGroup) {
        View view = convertview;


        if (convertview == null) {
            view = inflater.inflate(R.layout.venu_card_inner_list, viewGroup,false);}

        TextView textView1 = (TextView)view.findViewById(R.id.venue_card_inner_list_text1);
        TextView textView2 = (TextView)view.findViewById(R.id.venue_card_inner_list_text2);

        textView1.setText(attributes.get(i).getAttrName());
        textView2.setText(attributes.get(i).getAttrValue());
        return view;
    }
}
