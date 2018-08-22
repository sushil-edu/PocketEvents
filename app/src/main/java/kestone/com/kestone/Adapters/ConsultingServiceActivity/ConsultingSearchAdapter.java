package kestone.com.kestone.Adapters.ConsultingServiceActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.services.common.CommonUtils;
import kestone.com.kestone.MODEL.EventSearch.REQUEST.EventSearchRequest;
import kestone.com.kestone.MODEL.EventSearch.RESPONSE.EventSearchResponse;
import kestone.com.kestone.MODEL.EventSearch.RESPONSE.EventSearchResult;
import kestone.com.kestone.MODEL.EventSearch.RESPONSE.Events;
import kestone.com.kestone.R;
import kestone.com.kestone.UI.AppController;
import kestone.com.kestone.Utilities.CONSTANTS;
import kestone.com.kestone.Utilities.GeneralUtils;
import kestone.com.kestone.Utilities.GenericRequest;
import kestone.com.kestone.Utilities.StorageUtilities;

/**
 * Created by karan on 10/24/2017.
 */

public class ConsultingSearchAdapter extends BaseAdapter implements Filterable {
    private LayoutInflater inflater = null;
    private Context mContext;
    private List<EventSearchResult> resultList = new ArrayList<>();
    private List<Events> events = new ArrayList<>();
    private List<Events> eventsTemp = new ArrayList<>();
    StorageUtilities storage;


    public ConsultingSearchAdapter(Context context) {
        mContext = context;
        storage = new StorageUtilities(context);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Object getItem(int i) {
        return events.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.item_event_search, viewGroup, false);
        }
        TextView name = (TextView) view.findViewById(R.id.text1);
        TextView date = (TextView) view.findViewById(R.id.text2);
        Events e = (Events) getItem(i);
        name.setText(e.getEventName());
        date.setText("Create Date : "+ e.getCreateDate());
        return view;
    }

    @Override
    public Filter getFilter() {
       Filter filter = new Filter() {
           @Override
           protected FilterResults performFiltering(CharSequence constraint) {
               FilterResults filterResults = new FilterResults();
               if (constraint != null) {
                   List<Events> Data = findEvent(mContext, constraint.toString().trim());
                   // Assign the data to the FilterResults
                   filterResults.values = Data;
                   filterResults.count = Data.size();
               }else {
                   notifyDataSetInvalidated();
                   eventsTemp.clear();
               }
               return filterResults;
           }

           @Override
           protected void publishResults(CharSequence charSequence, FilterResults results) {
//               if (results != null && results.count > 0) {
//                   events.clear();
//                   events = (List<Events>) results.values;
//                   notifyDataSetChanged();
//               }
           }
       };
       return filter;
    }

    List<Events> findEvent(final Context context, String search){
        GenericRequest<EventSearchResponse> request = new GenericRequest<EventSearchResponse>(Request.Method.POST, CONSTANTS.URL_EVENTSEARCH, EventSearchResponse.class,
                new EventSearchRequest(storage.loadID(), search), new Response.Listener<EventSearchResponse>() {
            @Override
            public void onResponse(EventSearchResponse response) {
                resultList = response.getResponse();
                if (Boolean.valueOf(resultList.get(0).getStatus())){
                    for (int i = 0; i < resultList.size(); i++) {
                        eventsTemp = resultList.get(i).getEvents();
                    }
                    events = eventsTemp;
                    notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        AppController.getInstance().addToRequestQueue(request);
        return eventsTemp;
    }
}
