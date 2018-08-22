package kestone.com.kestone.UI;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import kestone.com.kestone.Adapters.MorefilterActivity.MoreFilterAdapterLeft;
import kestone.com.kestone.Adapters.MorefilterActivity.MoreFilterAdapterRight;
import kestone.com.kestone.MODEL.Filters.RESPONSE.GetVenueFiltersResponse;
import kestone.com.kestone.MODEL.Manager.FilterManager;
import kestone.com.kestone.MODEL.Manager.MySingleton;
import kestone.com.kestone.MODEL.Venue.REQUEST.VenueRequest;
import kestone.com.kestone.MODEL.Venue.RESPONSE.GetVenueListResponse;
import kestone.com.kestone.R;
import kestone.com.kestone.Utilities.CONSTANTS;
import kestone.com.kestone.Utilities.GeneralUtils;
import kestone.com.kestone.Utilities.GenericRequest;
import kestone.com.kestone.Utilities.StorageUtilities;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MoreFilterActivity extends AppCompatActivity implements MoreFilterAdapterLeft.MoreFilterLeftCallBack, View.OnClickListener, MoreFilterAdapterRight.MoreFilterRightCallBack {

    RecyclerView rvLeft, rvRight;
    GetVenueFiltersResponse filters;

    TextView apply,clear;

    StorageUtilities storage;

    boolean noSelection = true;

    int activitySource;

    MoreFilterAdapterLeft moreFilterAdapterLeft;

    MoreFilterAdapterRight moreFilterAdapterRight;

    ProgressDialog progressDialog;

    LinearLayout root;

    FilterManager filterManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_filter);
        moreFilterAdapterRight = new MoreFilterAdapterRight(MoreFilterActivity.this, 0, 0,0);
        RecyclerView.LayoutManager layoutManagerR = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        activitySource = getIntent().getIntExtra("activitySource",0);

        filterManager = new FilterManager(MoreFilterActivity.this);

        storage = new StorageUtilities(getApplicationContext());

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Getting Venues.....");

        apply = (TextView) findViewById(R.id.moreFilterApply);
        clear = (TextView) findViewById(R.id.morefilterClear);
        root = (LinearLayout)findViewById(R.id.MorefilterRoot);
        apply.setOnClickListener(this);
        clear.setOnClickListener(this);

        noSelection = true;
        rvLeft = (RecyclerView) findViewById(R.id.morefilter_rv_left);
        rvRight = (RecyclerView) findViewById(R.id.moreFilter_rv_right);
        moreFilterAdapterLeft = new MoreFilterAdapterLeft(MoreFilterActivity.this,0);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        rvLeft.setLayoutManager(layoutManager);
        rvLeft.setAdapter(moreFilterAdapterLeft);

        rvRight.setAdapter(moreFilterAdapterRight);
        rvRight.setLayoutManager(layoutManagerR);


    }


    @Override
    public void onClickCallBack(int i, int selection) {
        //noSelection = false;
        moreFilterAdapterRight.getSelectedItems();
        moreFilterAdapterRight = new MoreFilterAdapterRight(MoreFilterActivity.this, selection, i,1);
        moreFilterAdapterLeft = new MoreFilterAdapterLeft(MoreFilterActivity.this,i);
        rvLeft.setAdapter(moreFilterAdapterLeft);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        rvRight.setLayoutManager(layoutManager);
        rvRight.setAdapter(moreFilterAdapterRight);
    }

    @Override
    public void onBackPressed() {
        moreFilterAdapterRight.getSelectedItems();

        if (noSelection){
            MoreFilterActivity.super.onBackPressed();
        }else {
            new AlertDialog.Builder(MoreFilterActivity.this)
                    .setMessage("Save Filter Selections?")
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            MoreFilterActivity.super.onBackPressed();
                        }
                    })
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            moreFilterAdapterRight.saveState();
                            MoreFilterActivity.super.onBackPressed();

                        }
                    }).create().show();
        }



    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()){
            case R.id.moreFilterApply :
                moreFilterAdapterRight.getSelectedItems();
                moreFilterAdapterRight.saveState();
                switch (activitySource){
                    case 0 :
                        i = new Intent(MoreFilterActivity.this,VenueFilterActivity.class);
                        startActivity(i);
                        break;
                    case 1 :
                        if (GeneralUtils.isNetworkAvailable(MoreFilterActivity.this)){
                            progressDialog.show();
                            GetVenues();
                        }
                        else {
                            GeneralUtils.displayNetworkAlert(MoreFilterActivity.this,false);
                        }
                        break;
                }
                break;

            case R.id.morefilterClear :
                moreFilterAdapterRight.getSelectedItems();

                noSelection=true;
                final Dialog clearAll = new Dialog(MoreFilterActivity.this);
                clearAll.requestWindowFeature(Window.FEATURE_NO_TITLE);
                clearAll.setCancelable(true);
                clearAll.setContentView(R.layout.dialog_clear_all_filter);
                TextView no = (TextView) clearAll.findViewById(R.id.no);
                TextView yes = (TextView) clearAll.findViewById(R.id.yes);
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MySingleton.getInstance().resetAll();
                        storage.storePayload3(storage.loadPayload2());
                        //storage.storePayload1(storage.loadPayload1Original());
                        moreFilterAdapterRight = new MoreFilterAdapterRight(MoreFilterActivity.this, 0, 0,0);
                        rvRight.setAdapter(moreFilterAdapterRight);
                        rvLeft.setAdapter(moreFilterAdapterLeft);
                        clearAll.dismiss();
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        clearAll.dismiss();
//
                    }
                });
                clearAll.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                clearAll.show();

                break;
        }
    }


    @Override
    public void onClick(int POSITION, int position, int selection) {
        noSelection = false;
        moreFilterAdapterRight.getSelectedItems();
        moreFilterAdapterRight = new MoreFilterAdapterRight(MoreFilterActivity.this, selection, POSITION,1);
        rvRight.setAdapter(moreFilterAdapterRight);

    }

    void GetVenues(){
        GenericRequest<GetVenueListResponse> request = new GenericRequest<GetVenueListResponse>(Request.Method.POST, CONSTANTS.URL_GET_VENUE_LIST, GetVenueListResponse.class, new VenueRequest("1", "0", "10", filterManager.getSelectedFilters()),
                new Response.Listener<GetVenueListResponse>() {
                    @Override
                    public void onResponse(GetVenueListResponse response) {
                        progressDialog.dismiss();
                        Log.d("VENUE RESPONSE",response.toString());
                        if (Boolean.valueOf(response.getResponse().get(0).getStatus())) {
                            //Toast.makeText(getApplicationContext(),String.valueOf(response.getResponse().get(0).getPayload().size()),Toast.LENGTH_SHORT).show();
                            MySingleton.getInstance().setVenues(response);
                            Intent i = new Intent(MoreFilterActivity.this,VenueFilterActivity.class);
                            i.putExtra("venues", response);
                            startActivity(i);
                        }else {
                            Snackbar snackbar = Snackbar
                                    .make(root,response.getResponse().get(0).getMessage(), Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                GeneralUtils.ShowAlert(MoreFilterActivity.this,getString(R.string.VolleyTimeout));
                progressDialog.dismiss();
            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }



}
