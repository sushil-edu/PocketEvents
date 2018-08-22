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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SingleSelector;
import com.bignerdranch.android.multiselector.SwappingHolder;

import kestone.com.kestone.MODEL.Manager.MySingleton;
import kestone.com.kestone.R;

/**
 * Created by karan on 10/26/2017.
 */

public class MoreAdapterLeft extends RecyclerView.Adapter<MoreAdapterLeft.Holder> {

    private Context mcontext;
    MoreAdapterLeftCallBack callBack;
    MultiSelector multiSelector = new SingleSelector();
    int selected;

    public MoreAdapterLeft(Context context, int sel) {
        selected =sel;
        mcontext = context;
        callBack = (MoreAdapterLeftCallBack) context;
        multiSelector.setSelectable(true);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_more_filter_left, parent, false);
        return new MoreAdapterLeft.Holder(itemView);
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        holder.setSelectionModeBackgroundDrawable(getHighlightedBackground());
        holder.textView.setText(MySingleton.getInstance().getMoreResponse().getResponse().get(0).getPayload().get(MySingleton.getInstance().getMorePOS()).getMoreData().get(position).getValuename());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.OnClickLeft(position);
            }
        });

        if (position==selected){
            holder.textView.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return MySingleton.getInstance().getMoreResponse().getResponse().get(0).getPayload().get(MySingleton.getInstance().getMorePOS()).getMoreData().size();
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

    private StateListDrawable getHighlightedBackground() {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{16843518}, (Drawable)null);
        stateListDrawable.addState(StateSet.WILD_CARD, (Drawable)null);
        return stateListDrawable;
    }

    public interface MoreAdapterLeftCallBack {
        void OnClickLeft(int position);
    }
}
