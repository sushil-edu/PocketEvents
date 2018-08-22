package kestone.com.kestone.Adapters.MyOrdersActivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kestone.com.kestone.MODEL.MyOrders.RESPONSE.PayloadMyOrders;
import kestone.com.kestone.R;

/**
 * Created by karan on 10/24/2017.
 */

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.Holder> implements Filterable {
    Context mcontext;
    List<PayloadMyOrders> list;
    List<PayloadMyOrders> mFilterList;
    ValueFilter valueFilter;
    MyOrdersAdapterCallBack callBack;

    public MyOrdersAdapter(Context context, List<PayloadMyOrders> data) {
        list = data;
        mFilterList = data;
        mcontext = context;
        callBack = (MyOrdersAdapterCallBack) context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_my_orders, parent, false);
        return new MyOrdersAdapter.Holder(itemView);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        if (!list.get(position).getVenue().equals("")) {
            holder.venue.setText("₹ " + list.get(position).getVenue());
            holder.venue.setVisibility(View.VISIBLE);
            holder.Venue.setVisibility(View.VISIBLE);
        } else {
            holder.venue.setVisibility(View.GONE);
            holder.Venue.setVisibility(View.GONE);
        }

        if (!list.get(position).getSetUp().equals("")) {
            holder.setup.setText("₹ " + list.get(position).getSetUp());
            holder.setup.setVisibility(View.VISIBLE);
            holder.Setup.setVisibility(View.VISIBLE);
        } else {
            holder.setup.setVisibility(View.GONE);
            holder.Setup.setVisibility(View.GONE);
        }

        if (!list.get(position).getDesign().equals("")) {
            holder.design.setText("₹ " + list.get(position).getDesign());
            holder.design.setVisibility(View.VISIBLE);
            holder.Design.setVisibility(View.VISIBLE);
        } else {
            holder.design.setVisibility(View.GONE);
            holder.Design.setVisibility(View.GONE);
        }

        if (!list.get(position).getArtist().equals("")) {
            int totalAmount = Integer.parseInt(list.get(position).getArtist())
                    + Integer.parseInt(list.get(position).getActivities())
                    + Integer.parseInt(list.get(position).getGiveaway());
            holder.artists.setText("₹ " + totalAmount);
            holder.artists.setVisibility(View.VISIBLE);
        } else {
            holder.artists.setVisibility(View.GONE);
        }

        if (!list.get(position).getTax().equals("")) {
            holder.tax.setText(list.get(position).getTax());
            holder.tax.setVisibility(View.VISIBLE);
        } else {
            holder.tax.setVisibility(View.GONE);
        }
        holder.name.setText(list.get(position).getEventName());
        holder.details.setText(list.get(position).getCityName() + " | " + list.get(position).getCreateDate() + " | " + "₹" + list.get(position).getTotalAmount() +
                "\nOrderID - " + list.get(position).getOrderID());


        holder.topLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.expandableView.getVisibility() == View.VISIBLE) {
                    holder.expandableView.setVisibility(View.GONE);
                    holder.showMore.setText("More");
                    list.get(position).setExpanded(false);
                } else {
                    holder.expandableView.setVisibility(View.VISIBLE);
                    holder.showMore.setText("Less");
                    list.get(position).setExpanded(true);
                }
            }
        });

        if (list.get(position).isExpanded()) {
            holder.expandableView.setVisibility(View.VISIBLE);
            holder.showMore.setText("Less");
        } else {
            holder.expandableView.setVisibility(View.GONE);
            holder.showMore.setText("More");
        }
        holder.rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).getRating().equals("0"))
                    callBack.OnRatingClicked(list.get(position).getInvoiceNo());
            }
        });
        holder.ratingBar.setRating(Float.parseFloat(list.get(position).getRating()));


        holder.email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.OnEmailClicked(list.get(position).getInvoiceNo());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    public class Holder extends RecyclerView.ViewHolder {
        LinearLayout expandableView;
        TextView showMore, details, name, venue, setup, design, artists, tax;
        TextView status, email;
        LinearLayout card, topLl;
        View Venue, Setup, Design, Giveaways;
        FrameLayout rating;
        RatingBar ratingBar;

        public Holder(View view) {
            super(view);
            expandableView = (LinearLayout) view.findViewById(R.id.myOrderExpandable);
            showMore = (TextView) view.findViewById(R.id.myOrderMoreClick);
            details = (TextView) view.findViewById(R.id.myOrderDetails);
            name = (TextView) view.findViewById(R.id.myOrderName);
            venue = (TextView) view.findViewById(R.id.myOrderVenue);
            setup = (TextView) view.findViewById(R.id.myOrderSetup);
            design = (TextView) view.findViewById(R.id.myOrderDesign);
            artists = (TextView) view.findViewById(R.id.myOrderArtists);
            tax = (TextView) view.findViewById(R.id.myOrderTax);
            card = (LinearLayout) view.findViewById(R.id.card);
            status = (TextView) view.findViewById(R.id.myOrderOrderBTN);
            email = (TextView) view.findViewById(R.id.myOrderEmailBTN);
            rating = (FrameLayout) view.findViewById(R.id.myOrdersRatingClick);
            ratingBar = (RatingBar) view.findViewById(R.id.myOrdersRatingBar);
            topLl = (LinearLayout) view.findViewById(R.id.topLl);

            Venue = view.findViewById(R.id.view_venue);
            Setup = view.findViewById(R.id.view_setup);
            Design = view.findViewById(R.id.view_design);
            Giveaways = view.findViewById(R.id.view_giveaways);
        }
    }


    private class ValueFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            PayloadMyOrders filterHash;

            if (constraint != null && constraint.length() > 0) {
                List<PayloadMyOrders> filterList = new ArrayList<PayloadMyOrders>();

                for (int i = 0; i < mFilterList.size(); i++) {
                    if (mFilterList.get(i).getEventName().toLowerCase().contains(constraint)) {
                        filterHash = mFilterList.get(i);
                        filterList.add(filterHash);
                    }

                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mFilterList.size();
                results.values = mFilterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            list = (ArrayList<PayloadMyOrders>) results.values;
            notifyDataSetChanged();
        }
    }

    public interface MyOrdersAdapterCallBack {
        void OnEmailClicked(String InvoiceID);

        void OnRatingClicked(String InvoiceID);
    }


}
