package kestone.com.kestone.MODEL.Manager;

import android.content.Context;
import android.util.Log;

import kestone.com.kestone.Utilities.StorageUtilities;

/**
 * Created by karan on 7/11/2017.
 */

public class FilterManager {

    StorageUtilities storage;

    Context mContext;

    public FilterManager(Context context) {
        storage = new StorageUtilities(context);
        mContext = context;
    }

    public String getSelectedFilters() {

        String selectedFilters = "";
        for (int i = 0; i < storage.loadPayload1().size(); i++) {
            if (storage.loadPayload1().get(i).isSelected()) {

                selectedFilters += "; FilterID " + storage.loadPayload1().get(i).getFilterId() + " ; FilterValue ";

                if (storage.loadPayload1().get(i).getStyle().equals("2")) {
                    selectedFilters += storage.loadPayload1().get(i).getRanges().getLowerLimit() + "-" + storage.loadPayload1().get(i).getRanges().getUpperLimit();
                } else {
                    for (int j = 0; j < storage.loadPayload1().get(i).getValues().size(); j++) {
                        if (storage.loadPayload1().get(i).getValues().get(j).isSelected()) {
                            //Toast.makeText(getApplicationContext(),storage.loadPayload1().get(i).getFilterName() + "  " + storage.loadPayload1().get(i).getValues().get(j).getNameLabel(),Toast.LENGTH_SHORT).show();
                            if (storage.loadPayload1().get(i).getFilterName().equalsIgnoreCase("Hall Capacity")) {
                                //selectedFilters += storage.loadPayload1().get(i).getValues().get(j).getNameLabel() + ",";
                                Log.d("LowerLimit",storage.loadPayload1().get(i).getRanges().getLowerLimit());
                                selectedFilters += storage.loadPayload1().get(i).getRanges().getLowerLimit() + "-" + storage.loadPayload1().get(i).getRanges().getUpperLimit();
                            } else {
                                selectedFilters += storage.loadPayload1().get(i).getValues().get(j).getId() + ",";
                            }
                        }
                    }
                }


                if (selectedFilters.length() > 2 && selectedFilters.endsWith(","))
                    selectedFilters = selectedFilters.substring(0, selectedFilters.length() - 1);

            }
        }


        for (int i = 0; i < storage.loadPayload3().size(); i++) {

            if (storage.loadPayload3().get(i).isSelected()) {

                selectedFilters += "; FilterID " + storage.loadPayload3().get(i).getFilterId() + " ; FilterValue ";

                for (int j = 0; j < storage.loadPayload3().get(i).getValues().size(); j++) {
                    if (storage.loadPayload3().get(i).getValues().get(j).isSelected()) {
                        //Toast.makeText(getApplicationContext(),storage.loadPayload3().get(i).getFilterName() + "  " + storage.loadPayload3().get(i).getValues().get(j).getNameLabel(),Toast.LENGTH_SHORT).show();

                        selectedFilters += storage.loadPayload3().get(i).getValues().get(j).getId() + ",";
                    }
                }
                if (selectedFilters.length() > 2)
                    selectedFilters = selectedFilters.substring(0, selectedFilters.length() - 1);
            }
        }
        if (selectedFilters.length() > 2)
            selectedFilters = selectedFilters.substring(1, selectedFilters.length());
        Log.d("SELECTED FILTERS : ", selectedFilters);
        //Toast.makeText(mContext,selectedFilters,Toast.LENGTH_LONG).show();
        return selectedFilters;
    }
}
