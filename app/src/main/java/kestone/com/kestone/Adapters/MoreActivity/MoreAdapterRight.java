package kestone.com.kestone.Adapters.MoreActivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v7.widget.RecyclerView;
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

import kestone.com.kestone.MODEL.Manager.MySingleton;
import kestone.com.kestone.R;
import kestone.com.kestone.Utilities.StorageUtilities;

/**
 * Created by karan on 10/26/2017.
 */

public class MoreAdapterRight extends RecyclerView.Adapter<MoreAdapterRight.Holder> {
    private Context mcontext;
    MultiSelector multiSelector = new SingleSelector();
    MoreAdapterRightcallback rightcallback;
    int selected;
    int POSITION;
    StorageUtilities storage;

    public MoreAdapterRight(Context mcontext, int selected, int POS, int Mode) {
        this.mcontext = mcontext;
        this.selected = selected;
        storage = new StorageUtilities(mcontext);
        rightcallback = (MoreAdapterRightcallback) mcontext;
        if (Mode==0)
            initData();
        POSITION = POS;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_more_filter_right, parent, false);
        return new MoreAdapterRight.Holder(itemView);
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        holder.setSelectionModeBackgroundDrawable(getHighlightedBackground());
        holder.textView.setText(MySingleton.getInstance().getMoreResponse().getResponse().get(0).getPayload().get(MySingleton.getInstance().getMorePOS()).getMoreData().get(POSITION).getValues().get(position)
        .getOptionLabel());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!multiSelector.isSelected(position,0)){
                    multiSelector.setSelected(position,0,true);
                }else {
                    multiSelector.setSelected(position,0,false);
                }
                rightcallback.OnClickRight(POSITION,position);
            }
        });

        if (MySingleton.getInstance().getMoreResponse().getResponse().get(0).getPayload().get(MySingleton.getInstance().getMorePOS()).getMoreData().get(POSITION).getValues().get(position).isSelected()){
            multiSelector.setSelected(position,0,true);
            holder.textView.setTextColor(Color.RED);
            holder.check.setBackground(mcontext.getResources().getDrawable(R.drawable.check_red));
        }
    }

    @Override
    public int getItemCount() {
        return MySingleton.getInstance().getMoreResponse().getResponse().get(0).getPayload().get(MySingleton.getInstance().getMorePOS()).getMoreData().get(POSITION).getValues().size();
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

    private StateListDrawable getHighlightedBackground() {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{16843518}, (Drawable)null);
        stateListDrawable.addState(StateSet.WILD_CARD, (Drawable)null);
        return stateListDrawable;
    }

    public void getSelectedItems(){
        int count =  0;
        for (int i = 0; i<  MySingleton.getInstance().getMoreResponse().getResponse().get(0).getPayload().get(MySingleton.getInstance().getMorePOS()).getMoreData().get(POSITION).getValues().size(); i++){
            if (multiSelector.isSelected(i,0)){
                MySingleton.getInstance().getMoreResponse().getResponse().get(0).getPayload().get(MySingleton.getInstance().getMorePOS()).getMoreData().get(POSITION).getValues().get(i).setSelected(true);
                MySingleton.getInstance().getMoreResponse().getResponse().get(0).getPayload().get(MySingleton.getInstance().getMorePOS()).getMoreData().get(POSITION).setSelected(true);

                count++;
            }else {
                MySingleton.getInstance().getMoreResponse().getResponse().get(0).getPayload().get(MySingleton.getInstance().getMorePOS()).getMoreData().get(POSITION).getValues().get(i).setSelected(false);

            }
        }
        if (count==0){
            MySingleton.getInstance().getMoreResponse().getResponse().get(0).getPayload().get(MySingleton.getInstance().getMorePOS()).getMoreData().get(POSITION).setSelected(false);
        }

        MySingleton.getInstance().getMoreResponse().getResponse().get(0).getPayload().get(MySingleton.getInstance().getMorePOS()).setCount(count);

    }

    public  interface MoreAdapterRightcallback {
        void OnClickRight(int POSITION, int position);
    }

    void initData(){
        MySingleton.getInstance().setMoreResponse(storage.loadMoreData());
    }

}
