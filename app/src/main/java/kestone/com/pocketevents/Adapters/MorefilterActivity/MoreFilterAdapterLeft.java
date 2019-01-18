package kestone.com.pocketevents.Adapters.MorefilterActivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.StateSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SingleSelector;
import com.bignerdranch.android.multiselector.SwappingHolder;

import kestone.com.pocketevents.MODEL.Manager.MySingleton;
import kestone.com.pocketevents.R;

/**
 * Created by karan on 7/10/2017.
 */

public class MoreFilterAdapterLeft extends RecyclerView.Adapter<MoreFilterAdapterLeft.Holder> {
    private Context mcontext;

    LayoutInflater inflater;

    int selectedValue;

    MultiSelector multiSelector = new SingleSelector();

    MoreFilterLeftCallBack moreFilterLeftCallBack;




    public MoreFilterAdapterLeft(Context context, int sel){
        selectedValue = sel;
        this.moreFilterLeftCallBack = ((MoreFilterLeftCallBack)context);
        mcontext = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        multiSelector.setSelectable(true);
    }


    public class Holder extends SwappingHolder {
        TextView textView;
        LinearLayout card;


        public Holder(View view) {
            super(view, multiSelector);
            textView = (TextView)view.findViewById(R.id.more_filter_left_text);
            card = (LinearLayout)view.findViewById(R.id.more_filter_left_card);
        }
    }

    @Override
    public MoreFilterAdapterLeft.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_more_filter_left, parent, false);
        return new MoreFilterAdapterLeft.Holder(itemView);
    }

    @Override
    public void onBindViewHolder(final MoreFilterAdapterLeft.Holder holder, final int position) {
        holder.setSelectionModeBackgroundDrawable(getHighlightedBackground());
        holder.textView.setText(MySingleton.getInstance().getPayload2().get(position).getFilterName());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mcontext,"POSTION : "+ String.valueOf(position),Toast.LENGTH_SHORT).show();
                if (MySingleton.getInstance().getPayload2().get(position).getSelectiontype().equals("RadioButton")){
                    moreFilterLeftCallBack.onClickCallBack(position, 0);
                }else {
                    moreFilterLeftCallBack.onClickCallBack(position, 1);
                }
                multiSelector.setSelected(position,0,true);
            }
        });

        if (position==selectedValue){
            holder.textView.setTextColor(Color.RED);
        }



    }

    @Override
    public int getItemCount() {
        return MySingleton.getInstance().getPayload2().size();
    }


    public interface MoreFilterLeftCallBack {
        void onClickCallBack(int i, int selection);
    }

    private StateListDrawable getHighlightedBackground() {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{16843518}, (Drawable)null);
        stateListDrawable.addState(StateSet.WILD_CARD, (Drawable)null);
        return stateListDrawable;
    }


}
