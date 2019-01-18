package kestone.com.pocketevents.Adapters.MorefilterActivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.StateSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SingleSelector;
import com.bignerdranch.android.multiselector.SwappingHolder;

import java.util.List;

import kestone.com.pocketevents.MODEL.Filters.RESPONSE.Payload;
import kestone.com.pocketevents.MODEL.Manager.MySingleton;
import kestone.com.pocketevents.R;
import kestone.com.pocketevents.Utilities.StorageUtilities;

/**
 * Created by karan on 7/10/2017.
 */

public class MoreFilterAdapterRight extends RecyclerView.Adapter<MoreFilterAdapterRight.Holder> {

    private Context mcontext;
    LayoutInflater inflater;

    StorageUtilities storage;

    int sel;

    int POSITION;

    MoreFilterRightCallBack moreFilterRightCallBack;


    MultiSelector multiSelector;



    public MoreFilterAdapterRight(Context context,int selection, int p, int mode){
        mcontext = context;
        sel = selection;
        storage = new StorageUtilities(context);
        this.moreFilterRightCallBack = ((MoreFilterRightCallBack) context);
        if (mode==0)
            initPayload2();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (selection == 0){
            multiSelector = new SingleSelector();
        }else {
            multiSelector = new MultiSelector();
        }
        multiSelector.setSelectable(true);

        POSITION = p;
    }

    public class Holder extends SwappingHolder {
        TextView textView;
        LinearLayout card;
        ImageView check;


        public Holder(View view) {
            super(view, multiSelector);
            textView = (TextView)view.findViewById(R.id.more_filter_right_text);
            card = (LinearLayout)view.findViewById(R.id.moreFilter_right_root);
            check = (ImageView)view.findViewById(R.id.more_filter_right_check);
        }
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        holder.setSelectionModeBackgroundDrawable(getHighlightedBackground());
        holder.textView.setText(MySingleton.getInstance().getPayload2().get(POSITION).getValues().get(position).getNameLabel());
        //holder.textView.setText(storage.loadPayload2().get(POSITION).getValues().get(position).getNameLabel());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mcontext,"POSITION : " + String.valueOf(POSITION) + "  postion :"+ String.valueOf(position),Toast.LENGTH_SHORT).show();
                if (!multiSelector.isSelected(position,0)){
                    multiSelector.setSelected(position,0,true);
                    //holder.textView.setTextColor(Color.RED);


                }else {
                    multiSelector.setSelected(position,0,false);
                    //holder.textView.setTextColor(Color.BLACK);
                }
                moreFilterRightCallBack.onClick(POSITION,position,sel);

            }
        });
        if (MySingleton.getInstance().getPayload2().get(POSITION).getValues().get(position).isSelected()){
            multiSelector.setSelected(position,0,true);
            holder.textView.setTextColor(Color.RED);
            holder.check.setBackground(mcontext.getResources().getDrawable(R.drawable.check_red));
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_more_filter_right, parent, false);
        return new MoreFilterAdapterRight.Holder(itemView);
    }

    @Override
    public int getItemCount() {
        return MySingleton.getInstance().getPayload2().get(POSITION).getValues().size();
    }

    private StateListDrawable getHighlightedBackground() {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{16843518}, (Drawable)null);
        stateListDrawable.addState(StateSet.WILD_CARD, (Drawable)null);
        return stateListDrawable;
    }

    public void getSelectedItems(){
        int count = 0;
        for (int i = 0; i< MySingleton.getInstance().getPayload2().get(POSITION).getValues().size(); i++){
            if (multiSelector.isSelected(i,0)){
                MySingleton.getInstance().getPayload2().get(POSITION).getValues().get(i).setSelected(true);
                MySingleton.getInstance().getPayload2().get(POSITION).setSelected(true);
                MySingleton.getInstance().getPayload2().get(POSITION).setSelectedValueLabel(MySingleton.getInstance().getPayload2().get(POSITION).getValues().get(i).getNameLabel());

                count++;
            }else {
                MySingleton.getInstance().getPayload2().get(POSITION).getValues().get(i).setSelected(false);

            }
        }
        if (count==0){
            MySingleton.getInstance().getPayload2().get(POSITION).setSelected(false);
        }

    }

    public  void initPayload2(){
        MySingleton.getInstance().setPayload2(storage.loadPayload3());

    }

    public void saveState(){
        List<Payload> temp = MySingleton.getInstance().getPayload2();

        storage.storePayload3(temp);
    }

    public interface MoreFilterRightCallBack{
        void onClick(int POSITION, int position, int selection);
    }
}
