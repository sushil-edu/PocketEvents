package kestone.com.pocketevents.Adapters.DialogueAdapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.StateSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SingleSelector;
import com.bignerdranch.android.multiselector.SwappingHolder;

import java.util.ArrayList;
import java.util.List;

import kestone.com.pocketevents.MODEL.Manager.MySingleton;
import kestone.com.pocketevents.MODEL.Manager.SelectedFilters;
import kestone.com.pocketevents.MODEL.Filters.RESPONSE.Value;
import kestone.com.pocketevents.R;
import kestone.com.pocketevents.Utilities.StorageUtilities;

public class FilterDialogueAdapterStyleA extends RecyclerView.Adapter<FilterDialogueAdapterStyleA.Holder> {

    List<Value> list = new ArrayList<>();


    Context mContext;

    List<SelectedFilters> selectedFiltersList;

    MultiSelector multiSelector;

    LayoutInflater inflater;

    StorageUtilities storage;

    int POSITION;


    public FilterDialogueAdapterStyleA(Context context, int p, int selection) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        list = v;
        POSITION = p;

        storage = new StorageUtilities(context);
        mContext = context;
        if (selection == 0) {
            multiSelector = new SingleSelector();
        } else {
            multiSelector = new MultiSelector();
        }
        multiSelector.setSelectable(true);


    }


    public class Holder extends SwappingHolder {
        TextView textView;
        LinearLayout cardView;


        public Holder(View view) {
            super(view, multiSelector);
            textView = (TextView) view.findViewById(R.id.dialogue_a_text);
            cardView = (LinearLayout) view.findViewById(R.id.dialogue_a_card);
            // Holder.this.setSelectionModeBackgroundDrawable();

        }
    }

    @Override
    public FilterDialogueAdapterStyleA.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        multiSelector.setSelectable(true);
        View itemView;

        switch (viewType) {
            case 1:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.dialogue_item_style_a_2, parent, false);
                return new FilterDialogueAdapterStyleA.Holder(itemView);

            case 2:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.dialogue_item_style_a, parent, false);
                return new FilterDialogueAdapterStyleA.Holder(itemView);

            default:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.dialogue_item_style_a, parent, false);
                return new FilterDialogueAdapterStyleA.Holder(itemView);
        }


    }

    @Override
    public void onBindViewHolder(final FilterDialogueAdapterStyleA.Holder holder, final int position) {
        holder.setSelectionModeBackgroundDrawable(getHighlightedBackground());
        Log.d("Value", MySingleton.getInstance().getPayload1().get(POSITION).getValues().get(position).getNameLabel());
        holder.textView.setText(MySingleton.getInstance().getPayload1().get(POSITION).getValues().get(position).getNameLabel());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!multiSelector.isSelected(position, 0)) {
                    multiSelector.setSelected(position, 0, true);
                    // holder.cardView.setBackground(mContext.getResources().getDrawable(R.drawable.btn_design_selected));
                } else {
                    multiSelector.setSelected(position, 0, false);
                    // holder.cardView.setBackground(mContext.getResources().getDrawable(R.drawable.btn_design_not_selected));
                }

                sendMessage(getSelectedItemsCounnt());

            }
        });

        if (MySingleton.getInstance().getPayload1().get(POSITION).getValues().get(position).isSelected()) {
            multiSelector.setSelected(position, 0, true);
            sendMessage(getSelectedItemsCounnt());
        }

    }

    // Send an Intent with an action named "custom-event-name". The Intent
    // sent should
    // be received by the ReceiverActivity.
    private void sendMessage(int count) {
        Log.d("sender", "Broadcasting message");
        Intent intent = new Intent("custom-event-name");
        // You can also include some extra data.
        if (count > 0) {
            intent.putExtra("Flag", "1");
        } else {
            intent.putExtra("Flag", "0");
        }
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

    @Override
    public int getItemCount() {
        return MySingleton.getInstance().getPayload1().get(POSITION).getValues().size();
    }

    public void getSelectedItems() {
        int count = 0;
        for (int i = 0; i < MySingleton.getInstance().getPayload1().get(POSITION).getValues().size(); i++) {
            if (multiSelector.isSelected(i, 0)) {
                MySingleton.getInstance().getPayload1().get(POSITION).getValues().get(i).setSelected(true);
                MySingleton.getInstance().getPayload1().get(POSITION).setSelected(true);
                MySingleton.getInstance().getPayload1().get(POSITION).setSelectedValueLabel(MySingleton.getInstance().getPayload1().get(POSITION).getValues().get(i).getNameLabel());
                count++;
            } else {
                MySingleton.getInstance().getPayload1().get(POSITION).getValues().get(i).setSelected(false);
            }
        }
        if (count == 0) {
            MySingleton.getInstance().getPayload1().get(POSITION).setSelected(false);
        }
        storage.storePayload1(MySingleton.getInstance().getPayload1());
    }

    public int getSelectedItemsCounnt() {
        int count = 0;
        for (int i = 0; i < MySingleton.getInstance().getPayload1().get(POSITION).getValues().size(); i++) {
            if (multiSelector.isSelected(i, 0)) {
                count++;
            }
        }

        return count;
    }

    private StateListDrawable getHighlightedBackground() {
        ColorDrawable colorDrawable = new ColorDrawable(ContextCompat.getColor(mContext, R.color.btnColorGrey));
        ColorDrawable colorDrawable2 = new ColorDrawable(ContextCompat.getColor(mContext, R.color.selection_grey));
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{16843518}, colorDrawable);
        stateListDrawable.addState(StateSet.WILD_CARD, colorDrawable2);
        return stateListDrawable;
    }

    @Override
    public int getItemViewType(int position) {
        if (MySingleton.getInstance().getPayload1().get(POSITION).getFilterId().equals("4")) {
            return 1;
        } else {
            return 2;
        }
    }
}
