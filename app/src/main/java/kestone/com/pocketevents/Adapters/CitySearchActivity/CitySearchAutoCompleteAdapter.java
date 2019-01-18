package kestone.com.pocketevents.Adapters.CitySearchActivity;

import android.content.Context;
import android.util.Log;
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
import kestone.com.pocketevents.UI.AppController;
import kestone.com.pocketevents.MODEL.CitySearch.REQUEST.SendCitySearch;
import kestone.com.pocketevents.MODEL.CitySearch.RESPONSE.City;
import kestone.com.pocketevents.MODEL.CitySearch.RESPONSE.CityNameListResult;
import kestone.com.pocketevents.MODEL.CitySearch.RESPONSE.CityResult;
import kestone.com.pocketevents.R;
import kestone.com.pocketevents.Utilities.CONSTANTS;
import kestone.com.pocketevents.Utilities.GenericRequest;
import kestone.com.pocketevents.Utilities.StorageUtilities;

/**
 * Created by karan on 7/6/2017.
 */

public class CitySearchAutoCompleteAdapter extends BaseAdapter implements Filterable {

    private LayoutInflater inflater = null;
    private Context mContext;
    private List<CityNameListResult> resultList = new ArrayList<>();
    private List<City> cities = new ArrayList<>();
    private List<City> cityTemp = new ArrayList<>();
    StorageUtilities storage;
    public CitySearchAutoCompleteAdapter(Context context){
        mContext = context;
        storage = new StorageUtilities(context);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return cities.size();
    }

    @Override
    public Object getItem(int position) {
        return cities.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.autocomplete_text_item, viewGroup, false);
        }
        TextView title = (TextView) view.findViewById(R.id.city_search_result_list_text);
        City c = (City) getItem(i);
        title.setText(c.getCityName());
        return view;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                   // Toast.makeText(mContext,constraint,Toast.LENGTH_SHORT).show();
                    List<City> data = findCity(mContext, constraint.toString().trim());
                    filterResults.values = data;
                    filterResults.count = data.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
//                if (results != null && results.count > 0) {
//                    cities = (List<City>) results.values;
//                    notifyDataSetChanged();
//                }else {
//                    notifyDataSetInvalidated();
//                    cityTemp.clear();
//                }
            }};
        return filter;
    }


    /**
     * Returns a search result for the given book title.
     */
    private List<City> findCity(final Context context, String citySearchTerm) {

        GenericRequest<CityResult> request = new GenericRequest<CityResult>(Request.Method.POST, CONSTANTS.URL_GET_CITY, CityResult.class, new SendCitySearch(citySearchTerm, "1"),
                new Response.Listener<CityResult>() {
            @Override
            public void onResponse(CityResult response) {

                resultList = response.getResults();
                if (Boolean.valueOf(resultList.get(0).getStatus())) {
                    for (int i = 0; i < resultList.size(); i++) {
                        cities = resultList.get(i).getPayload();
                        notifyDataSetChanged();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        AppController.getInstance().addToRequestQueue(request);

        return cityTemp;
    }
}
