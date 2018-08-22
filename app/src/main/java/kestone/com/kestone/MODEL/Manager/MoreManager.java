package kestone.com.kestone.MODEL.Manager;

import android.content.Context;

import kestone.com.kestone.MODEL.More.RESPONSE.MoreResponse;
import kestone.com.kestone.Utilities.StorageUtilities;

/**
 * Created by karan on 10/30/2017.
 */

public class MoreManager {

    StorageUtilities storage;

    Context mContext;

    MoreResponse data;

    public MoreManager(Context mContext) {
        this.mContext = mContext;
        storage = new StorageUtilities(mContext);
    }

    public String getSelectedFilters(){
        data = storage.loadMoreData();
        String selectedFilters = "";
        for (int i = 0; i<data.getResponse().get(0).getPayload().size();i++){

            for (int j = 0;j < data.getResponse().get(0).getPayload().get(i).getMoreData().size();j++){
                if (data.getResponse().get(0).getPayload().get(i).getMoreData().get(j).isSelected()){
                    selectedFilters += "; FilterID " + data.getResponse().get(0).getPayload().get(i).getMoreData().get(j).getValueid() + " ; FilterValue ";

                    for (int k = 0; k < data.getResponse().get(0).getPayload().get(i).getMoreData().get(j).getValues().size();k++){
                        if (data.getResponse().get(0).getPayload().get(i).getMoreData().get(j).getValues().get(k).isSelected()){
                            selectedFilters += data.getResponse().get(0).getPayload().get(i).getMoreData().get(j).getValues().get(k).getOptionID();
                        }
                    }
                }
            }
        }
        if (selectedFilters.length()>1)
            selectedFilters = selectedFilters.substring(1, selectedFilters.length());
        return selectedFilters;
    }
}
// ; FilterID 1 ; FilterValue 1,0