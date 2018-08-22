package kestone.com.kestone.Adapters.DialogueAdapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import kestone.com.kestone.MODEL.Filters.RESPONSE.Value;
import kestone.com.kestone.MODEL.HallDetails.HallDetails;
import kestone.com.kestone.MODEL.Manager.MySingleton;
import kestone.com.kestone.R;
import kestone.com.kestone.Utilities.StorageUtilities;

/**
 * Created by karan on 7/10/2017.
 */

public class FilterDialogueAdpaterStyleB extends RecyclerView.Adapter<FilterDialogueAdpaterStyleB.Holder> {
    List<Value> list = new ArrayList<>();

    Context mContext;

    LayoutInflater inflater;

    int POSITION;

    StorageUtilities storage;

    int sel;


    public FilterDialogueAdpaterStyleB(Context context, int p, int selection ){

        mContext = context;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        POSITION = p;

        sel = selection;

        storage = new StorageUtilities(context);

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dialogue_item_style_b, parent, false);
        return new FilterDialogueAdpaterStyleB.Holder(itemView);
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        Glide.with(mContext).load(MySingleton.getInstance().getPayload1().get(POSITION).getValues().get(position).getImageIcon().trim()).override(350,350).crossFade().placeholder(R.drawable.placeholder).into(holder.imageView);
        holder.textView.setText(MySingleton.getInstance().getPayload1().get(POSITION).getValues().get(position).getDescriptionText());
        holder.textView2.setText(MySingleton.getInstance().getPayload1().get(POSITION).getValues().get(position).getNameLabel());

//        if (MySingleton.getInstance().getPayload1().get(POSITION).getValues().get(position).isSelected()){
////            multiSelector.setSelected(position,0,true);
//            holder.card.setBackground(ContextCompat.getDrawable(mContext,R.color.btnColorGrey));
//            holder.textView.setTextColor(ContextCompat.getColor(mContext,R.color.textColorWhite));
//            holder.textView2.setTextColor(ContextCompat.getColor(mContext,R.color.textColorWhite));
//        }

    }

    @Override
    public int getItemCount() {
        return MySingleton.getInstance().getPayload1().get(POSITION).getValues().size();
    }


    public class Holder extends RecyclerView.ViewHolder {
        TextView textView , textView2;
        ImageView imageView;
        LinearLayout card;


        public Holder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.dialogue_b_textView);
            textView2 = (TextView) view.findViewById(R.id.dialogue_b_textView2);
            imageView = (ImageView)view.findViewById(R.id.dialogue_b_imageView);
            card = (LinearLayout)view.findViewById(R.id.dialogue_b_card);


        }


    }


    public void getSelection(int position){
        int count = 0;
        if (sel == 0){
            for (int i = 0; i<MySingleton.getInstance().getPayload1().get(POSITION).getValues().size();i++){
                if (i==position){
                    MySingleton.getInstance().getPayload1().get(POSITION).getValues().get(i).setSelected(true);
                    MySingleton.getInstance().getPayload1().get(POSITION).setSelected(true);
                    MySingleton.getInstance().getPayload1().get(POSITION).setSelectedValueLabel(MySingleton.getInstance().getPayload1().get(POSITION).getValues().get(i).getNameLabel());
                }else {
                    MySingleton.getInstance().getPayload1().get(POSITION).getValues().get(i).setSelected(false);
                }

            }
        }else {
            if (MySingleton.getInstance().getPayload1().get(POSITION).getValues().get(position).isSelected()){
                MySingleton.getInstance().getPayload1().get(POSITION).getValues().get(position).setSelected(false);
                MySingleton.getInstance().getPayload1().get(POSITION).setSelected(false);
            }else {
                MySingleton.getInstance().getPayload1().get(POSITION).getValues().get(position).setSelected(true);
                MySingleton.getInstance().getPayload1().get(POSITION).setSelected(true);
                MySingleton.getInstance().getPayload1().get(POSITION).setSelectedValueLabel(MySingleton.getInstance().getPayload1().get(POSITION).getValues().get(position).getNameLabel());
            }
            for (int i = 0;i<MySingleton.getInstance().getPayload1().get(POSITION).getValues().size();i++){
                if (MySingleton.getInstance().getPayload1().get(POSITION).getValues().get(i).isSelected()){
                    count++;
                }
            }
            if (count==0){
                MySingleton.getInstance().getPayload1().get(POSITION).setSelected(false);
            }
        }
        storage.storePayload1(MySingleton.getInstance().getPayload1());
    }
}
