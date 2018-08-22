package kestone.com.kestone.Adapters.DialogueAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import kestone.com.kestone.R;

/**
 * Created by karan on 7/14/2017.
 */

public class PhoneNumbersAdapter extends BaseAdapter {
    LayoutInflater inflater;
    String[] phone;

    public PhoneNumbersAdapter(Context context,String[] p) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        phone = p;

    }

    @Override
    public int getCount() {
        return phone.length;
    }

    @Override
    public Object getItem(int i) {
        return phone[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertview, ViewGroup viewGroup) {
        View view = convertview;


        if (convertview == null) {
            view = inflater.inflate(R.layout.item_phone_list, viewGroup,false);}

        TextView textView1 = (TextView)view.findViewById(R.id.phone_lv_item);

        textView1.setText(phone[i]);


        return view;
    }

}
