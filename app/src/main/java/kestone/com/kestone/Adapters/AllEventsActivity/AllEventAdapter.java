package kestone.com.kestone.Adapters.AllEventsActivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.silencedut.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

import kestone.com.kestone.MODEL.EventEmail.REQUEST.EventEmailRequest;
import kestone.com.kestone.MODEL.EventEmail.RESPONSE.EventEmailResponse;
import kestone.com.kestone.MODEL.GetSavedVenue.RESPONSE.GetSavedEventListResponse;
import kestone.com.kestone.MODEL.GetSavedVenue.RESPONSE.Payload;
import kestone.com.kestone.R;
import kestone.com.kestone.UI.AllEventsActivity;
import kestone.com.kestone.UI.AppController;
import kestone.com.kestone.Utilities.CONSTANTS;
import kestone.com.kestone.Utilities.GeneralUtils;
import kestone.com.kestone.Utilities.GenericRequest;

/**
 * Created by karan on 8/30/2017.
 */
public class AllEventAdapter extends RecyclerView.Adapter<AllEventAdapter.Holder> implements Filterable {
    Context mContext;
    AllEventAdapterCallBack allEventAdapterCallBack;
    List<Payload> list;
    List<Payload> mFilterList;

    ValueFilter valueFilter;

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    public class Holder extends RecyclerView.ViewHolder {
        LinearLayout expandableView,fourthLl,thirdLl,secondLl,firstLl;
        RelativeLayout click;
        TextView showMore, details, name, venue, setup, design, more, tvEdit;
        TextView delete, email, eventConfiguration;
        ExpandableLayout expandableLl;

        public Holder(View view) {
            super(view);
            expandableView = (LinearLayout) view.findViewById(R.id.all_events_expandable_layout);
            showMore = (TextView) view.findViewById(R.id.all_event_more_click);
            details = (TextView) view.findViewById(R.id.allEventDetail);
            name = (TextView) view.findViewById(R.id.allEventName);
            venue = (TextView) view.findViewById(R.id.allEventVenue);
            setup = (TextView) view.findViewById(R.id.allEventSetup);
            design = (TextView) view.findViewById(R.id.allEventDesign);
            more = (TextView) view.findViewById(R.id.allEventMore);
            tvEdit = (TextView) view.findViewById(R.id.tvEdit);
            tvEdit.setVisibility( View.GONE );
            delete = (TextView) view.findViewById(R.id.allEventDelete_btn);
            email = (TextView) view.findViewById(R.id.allEventEmail_btn);
            eventConfiguration = (TextView) view.findViewById(R.id.tvEventConfiguration);
            click = (RelativeLayout) view.findViewById(R.id.rl_click);
            expandableLl = (ExpandableLayout) view.findViewById(R.id.expandableLl);
            firstLl = (LinearLayout) view.findViewById(R.id.firstLl);
            secondLl = (LinearLayout) view.findViewById(R.id.secondLl);
            thirdLl = (LinearLayout) view.findViewById(R.id.thirdLl);
            fourthLl = (LinearLayout) view.findViewById(R.id.fourthLl);
        }
    }

    public AllEventAdapter(Context context, GetSavedEventListResponse getSavedEventListResponse) {
        mContext = context;
        setHasStableIds(true);
        this.allEventAdapterCallBack = (AllEventAdapterCallBack) context;
        list = getSavedEventListResponse.getResponse().get(0).getPayload();
        mFilterList = getSavedEventListResponse.getResponse().get(0).getPayload();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_allevent_parent, parent, false);
        return new AllEventAdapter.Holder(itemView);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        holder.name.setText(list.get(position).getEventName());
        if (!list.get(position).getMoreFilterName().equals("")) {
            holder.more.setText(list.get(position).getMoreFilterName());
            holder.more.setTextColor(mContext.getResources().getColor(R.color.text_defaul));
        }else {
            holder.more.setTextColor(mContext.getResources().getColor(R.color.textColorRed));
        }


        if (!list.get(position).getSetupID().equals("0")) {
            holder.setup.setText(list.get(position).getSetupName());
            holder.setup.setTextColor(mContext.getResources().getColor(R.color.text_defaul));
        } else {
            holder.setup.setTextColor(mContext.getResources().getColor(R.color.textColorRed));
        }


        if (!list.get(position).getDesignID().equals("0")) {
            holder.design.setText(list.get(position).getDesignName());
            holder.design.setTextColor(mContext.getResources().getColor(R.color.text_defaul));
        } else {
            holder.design.setTextColor(mContext.getResources().getColor(R.color.textColorRed));
        }


        if (list.get(position).getHallId().equals("0")) {
            holder.venue.setText("Configure This");

        } else {
            holder.venue.setText(list.get(position).getVenueName() + " | " + list.get(position).getHallName());
        }
//        holder.expandableView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                allEventAdapterCallBack.OnEventClicked(list.get(position));
//            }
//        });


        holder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allEventAdapterCallBack.OnEventClicked(list.get(position),0);
            }
        });

        holder.secondLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.setup.getText().toString().equals( "Configure This" ))
                    allEventAdapterCallBack.OnEventClicked( list.get( position ), 1 );

            }
        });

        holder.thirdLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.design.getText().toString().equals( "Configure This" ) && !holder.setup.getText().toString().equals( "Configure This" ) )
                    allEventAdapterCallBack.OnEventClicked( list.get( position ), 2 );
            }
        });

        holder.fourthLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.more.getText().toString().equals( "Configure This" ) && !holder.design.getText().toString().equals( "Configure This" ) && !holder.setup.getText().toString().equals( "Configure This" ))
                    allEventAdapterCallBack.OnEventClicked(list.get(position),3);


            }
        });


        if (list.get(position).getVenueName().equals("")) {
            holder.details.setText(list.get(position).getCityname() + " | " + list.get(position).getCreateDate());
        } else {
            holder.details.setText(list.get(position).getCityname() + " | " + list.get(position).getVenueName() + " | " + list.get(position).getCreateDate());

        }
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext,String.valueOf(position),Toast.LENGTH_SHORT).show();
                allEventAdapterCallBack.OnDeleteClicked(list.get(position), position);
            }
        });

        holder.email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allEventAdapterCallBack.OnEmailClicked(list.get(position).getEventId());
            }
        });

        holder.expandableLl.setOnExpandListener(new ExpandableLayout.OnExpandListener() {
            @Override
            public void onExpand(boolean b) {
                if (b) {
                    holder.showMore.setText("Less");
                    holder.tvEdit.setVisibility( View.VISIBLE );
                } else {
                    holder.showMore.setText("More");
                    holder.tvEdit.setVisibility( View.GONE );
                }
            }
        });

        holder.eventConfiguration.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allEventAdapterCallBack.OnConfigurationClicked(list.get(position).getEventId());
            }
        } );

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface AllEventAdapterCallBack {
        void OnEventClicked(Payload payload,int position);

        void OnDeleteClicked(Payload payload, int position);

        void OnEmailClicked(String EventID);

        void OnConfigurationClicked(String EventID);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    private class ValueFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            Payload filterHash;

            if (constraint != null && constraint.length() > 0) {
                List<Payload> filterList = new ArrayList<Payload>();

                for (int i = 0; i < mFilterList.size(); i++) {
                    if (mFilterList.get(i).getEventName().toLowerCase().contains(constraint.toString().toLowerCase())) {
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
            list = (ArrayList<Payload>) results.values;
            notifyDataSetChanged();
        }
    }


}
