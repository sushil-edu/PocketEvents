package kestone.com.kestone.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import kestone.com.kestone.MODEL.ReferalRewardList.RESPONSE.MyReferralRewardListResult;
import kestone.com.kestone.MODEL.ReferalRewardList.RESPONSE.PayloadItemList;
import kestone.com.kestone.R;

/**
 * Created by Xyz on 1/3/2018.
 */

public class MyEarningAdapter extends RecyclerView.Adapter<MyEarningAdapter.Holder> {

    Context mcontext;
    List<PayloadItemList> list;

    public MyEarningAdapter(Context context, List<MyReferralRewardListResult> response) {
        mcontext=context;
        list=response.get(0).getPayload();
    }

    @Override
    public MyEarningAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_earning,parent,false);
        return new MyEarningAdapter.Holder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyEarningAdapter.Holder holder, final int position) {
    holder.all_event_more_click.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        holder.dispatch_status_layout.setVisibility(View.GONE);
    }
});
    holder.my_last_earning_point.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        //holder.dispatch_status_layout.setVisibility(View.VISIBLE);

        if (holder.dispatch_status_layout.getVisibility() == View.VISIBLE){
            holder.dispatch_status_layout.setVisibility(View.GONE);
            list.get(position).setExpanded(false);
        }else {
            holder.dispatch_status_layout.setVisibility(View.VISIBLE);

            list.get(position).setExpanded(true);
        }
    }
});
        if (list.get(position).isExpanded()){
            holder.dispatch_status_layout.setVisibility(View.VISIBLE);
        }else {
            holder.dispatch_status_layout.setVisibility(View.GONE);

        }
    holder.earnmethod.setText(list.get(position).getEarnmethod());
    holder.earn_date.setText(list.get(position).getEarndate());
    holder.earnPoint.setText(list.get(position).getEarningPoint());
    holder.invoiceAmount.setText("Invoice Amount : INR "+list.get(position).getInvoiceamount());
    holder.dispatchStatus.setText(list.get(position).getDispatchstatus());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder{

        ImageView all_event_more_click;
        LinearLayout my_last_earning_point,dispatch_status_layout;
        TextView earnmethod,earn_date,earnPoint,invoiceAmount,dispatchStatus;
        public Holder(View itemView) {
            super(itemView);
            earnmethod=(TextView)itemView.findViewById(R.id.earnmethod);
            earn_date=(TextView)itemView.findViewById(R.id.earn_date);
            earnPoint=(TextView)itemView.findViewById(R.id.earn_point);
            invoiceAmount=(TextView)itemView.findViewById(R.id.invoice_amount);
            dispatchStatus=(TextView)itemView.findViewById(R.id.dispatchStatus);

            my_last_earning_point=(LinearLayout) itemView.findViewById(R.id.my_last_earning_layout);
            dispatch_status_layout=(LinearLayout) itemView.findViewById(R.id.dispatch_status_layout);
            all_event_more_click=(ImageView)itemView.findViewById(R.id.all_event_more_click);

        }
    }
}
