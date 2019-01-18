package kestone.com.pocketevents.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import it.gmariotti.recyclerview.adapter.AlphaAnimatorAdapter;
import kestone.com.pocketevents.Adapters.MoreFragment.MoreAdapter;
import kestone.com.pocketevents.Adapters.MoreFragment.MoreTrendingAdapter;
import kestone.com.pocketevents.MODEL.Consulting.ConsultingResponse;
import kestone.com.pocketevents.MODEL.EventEmail.REQUEST.EventEmailRequest;
import kestone.com.pocketevents.MODEL.EventEmail.RESPONSE.EventEmailResponse;
import kestone.com.pocketevents.MODEL.GetSavedVenue.REQUEST.GetSavedVenueRequest;
import kestone.com.pocketevents.MODEL.GetSavedVenue.RESPONSE.GetSavedEventListResponse;
import kestone.com.pocketevents.MODEL.Manager.MoreManager;
import kestone.com.pocketevents.MODEL.Manager.MySingleton;
import kestone.com.pocketevents.MODEL.SaveVenue.REQUEST.SaveVenueRequest;
import kestone.com.pocketevents.MODEL.SaveVenue.RESPONSE.SaveEventResponse;
import kestone.com.pocketevents.R;
import kestone.com.pocketevents.UI.AllEventsActivity;
import kestone.com.pocketevents.UI.AppController;
import kestone.com.pocketevents.UI.CitySearchActivity;
import kestone.com.pocketevents.UI.ConsultingServiceActivity;
import kestone.com.pocketevents.UI.MoreActivity;
import kestone.com.pocketevents.Utilities.CONSTANTS;
import kestone.com.pocketevents.Utilities.GeneralUtils;
import kestone.com.pocketevents.Utilities.GenericRequest;
import kestone.com.pocketevents.Utilities.StorageUtilities;


public class MoreFragment extends Fragment implements MoreAdapter.MoreAdapterCallback, View.OnClickListener {
    RecyclerView rv1;
    RecyclerView rv2;
    TextView save, saveExit;
    TabLayout allTabs;
    TextView venueText, hallText;
    MoreAdapter moreAdapter;
    Dialog dialog, filterSaved;
    ProgressDialog progressDialog;
    AlphaAnimatorAdapter animatorAdapter, animatorTrendingAdapter;
    MoreTrendingAdapter moreTrendingAdapter;
    StorageUtilities storage;
    TextView venueFilter_layout_city;
    MoreManager moreManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_more, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateActivityUI();
        storage = new StorageUtilities(getActivity());
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getString(R.string.PleaseWait));
        progressDialog.setCancelable(false);
        moreManager = new MoreManager(getActivity());
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        SetupFilterSavedDialogue();
        allTabs = (TabLayout)view.findViewById(R.id.tabs);
        venueFilter_layout_city=(TextView) view.findViewById(R.id.venueFilterChangeCity);
        venueFilter_layout_city.setOnClickListener(this);

        // set font size as per your requirement

        save = (TextView) view.findViewById(R.id.MoreSave);
        save.setOnClickListener(this);
        saveExit = (TextView) view.findViewById(R.id.MoreSaveExit);
        saveExit.setOnClickListener(this);
        moreAdapter = new MoreAdapter(getActivity(),this);
        moreTrendingAdapter = new MoreTrendingAdapter(getActivity(),0);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        LinearLayoutManager lm2 = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        rv1 = (RecyclerView)view.findViewById(R.id.more_recycler1);
        rv1.setLayoutManager(lm);
        animatorAdapter = new AlphaAnimatorAdapter(moreAdapter,rv1);
        rv1.setAdapter(animatorAdapter);
        rv2 = (RecyclerView)view.findViewById(R.id.more_recycler2);
        rv2.setLayoutManager(lm2);
       // VideoStoryItemAdapter newAdapter= new VideoStoryItemAdapter();
        animatorTrendingAdapter = new AlphaAnimatorAdapter(moreTrendingAdapter,rv2);
        rv2.setAdapter(animatorTrendingAdapter);

       // rv2.setAdapter(newAdapter);
        venueText = (TextView)view.findViewById(R.id.more_venueText);
        //venueText.setText(MySingleton.getInstance().getMoreResponse().getResponse().get(0).getVanueName());
        venueText.setText(storage.loadEventName());
        hallText = (TextView) view.findViewById(R.id.more_HallText);
        hallText.setText(MySingleton.getInstance().getMoreResponse().getResponse().get(0).getHallName());

        setupTabLayout();

        TextView tabOne = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab_one_label, null);
        tabOne.setText(MySingleton.getInstance().getMoreResponse().getResponse().get(0).getTrending().get(0).getMoreName());
        tabOne.setTextSize(12);
        allTabs.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab_one_label, null);
        tabTwo.setText(MySingleton.getInstance().getMoreResponse().getResponse().get(0).getTrending().get(1).getMoreName());
        tabTwo.setTextSize(12);
        allTabs.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab_one_label, null);
        tabThree.setText(MySingleton.getInstance().getMoreResponse().getResponse().get(0).getTrending().get(2).getMoreName());
        tabThree.setTextSize(12);
        allTabs.getTabAt(2).setCustomView(tabThree);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private void setupTabLayout() {

        allTabs.addTab(allTabs.newTab().setText(MySingleton.getInstance().getMoreResponse().getResponse().get(0).getTrending().get(0).getMoreName()),true);
        allTabs.addTab(allTabs.newTab().setText(MySingleton.getInstance().getMoreResponse().getResponse().get(0).getTrending().get(1).getMoreName()));
        allTabs.addTab(allTabs.newTab().setText(MySingleton.getInstance().getMoreResponse().getResponse().get(0).getTrending().get(2).getMoreName()));
        allTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0 :
                        if (MySingleton.getInstance().getMoreResponse().getResponse().get(0).getTrending().get(0).getTrendingValues().size()>0){
                            moreTrendingAdapter = new MoreTrendingAdapter(getActivity(),0);
                            animatorTrendingAdapter = new AlphaAnimatorAdapter(moreTrendingAdapter,rv2);
                            rv2.setAdapter(animatorTrendingAdapter);
                            break;
                        }else {
                            rv2.setAdapter(null);
                        }
                    case 1 :
                        if (MySingleton.getInstance().getMoreResponse().getResponse().get(0).getTrending().get(1).getTrendingValues().size()>0){
                            moreTrendingAdapter = new MoreTrendingAdapter(getActivity(),1);
                            animatorTrendingAdapter = new AlphaAnimatorAdapter(moreTrendingAdapter,rv2);
                            rv2.setAdapter(animatorTrendingAdapter);
                            break;
                        }else {
                            rv2.setAdapter(null);
                        }

                    case 2 :
                        if (MySingleton.getInstance().getMoreResponse().getResponse().get(0).getTrending().get(2).getTrendingValues().size()>0){
                            moreTrendingAdapter = new MoreTrendingAdapter(getActivity(),2);
                            animatorTrendingAdapter = new AlphaAnimatorAdapter(moreTrendingAdapter,rv2);
                            rv2.setAdapter(animatorTrendingAdapter);
                            break;
                        }else {
                            rv2.setAdapter(null);
                        }

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    void updateActivityUI() {
        Intent intent = new Intent("updateUI");
        intent.putExtra("frag", 4);

        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
    }


    @Override
    public void OnClick() {
        Intent intent = new Intent(getActivity(), MoreActivity.class);
        getActivity().startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.venueFilterChangeCity:
                Intent i;
                i = new Intent(getActivity(), CitySearchActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                break;


            case R.id.MoreSave :

                        if (GeneralUtils.isNetworkAvailable(getActivity())){
                            SaveFilters(storage.loadEventName(), false, false, storage.loadEventDate());

//                            new AlertDialog.Builder(getActivity())
//
//                                    .setMessage(getString(R.string.SaveFilter))
//                                    .setNegativeButton("No", null)
//                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//
//                                        public void onClick(DialogInterface arg0, int arg1) {
//                                            SaveFilters(storage.loadEventName(), false, false, storage.loadEventDate());
//                                        }
//                                    }).create().show();


//                            final Dialog dialog = new Dialog(getContext());
//                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                            dialog.setContentView(R.layout.dialog_save_and_proceed);
//                            TextView yes = (TextView) dialog.findViewById(R.id.yes);
//                            yes.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    SaveFilters(storage.loadEventName(), false, false, storage.loadEventDate());
//                                    dialog.cancel();
//                                }
//                            });
//
//                            TextView no = (TextView) dialog.findViewById(R.id.no);
//                            no.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    dialog.cancel();
//                                }
//                            });
//
//                            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                            dialog.show();


                        }
                        else {
                            GeneralUtils.displayNetworkAlert(getActivity(),false);
                        }
                    // filterManager.getSelectedFilters();

                break;
            case R.id.MoreSaveExit :
                if (GeneralUtils.isNetworkAvailable(getActivity())){

//                        new AlertDialog.Builder(getActivity())
//                                .setMessage(getString(R.string.SaveExit))
//                                .setNegativeButton("No", null)
//                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface arg0, int arg1) {
//                                        SaveFilters(storage.loadEventName(), true, false, storage.loadEventDate());
//                                    }
//                                }).create().show();

                    final Dialog dialog = new Dialog(getContext());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_save_and_exit);
                    TextView yes = (TextView) dialog.findViewById(R.id.yes);
                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            SaveFilters(storage.loadEventName(), true, false, storage.loadEventDate());
                            dialog.cancel();
                        }
                    });

                    TextView no = (TextView) dialog.findViewById(R.id.no);
                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    });

                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.show();



                }
                else {
                    GeneralUtils.displayNetworkAlert(getActivity(),false);
                }
                break;
        }
    }

    void SetupFilterSavedDialogue(){
        filterSaved = new Dialog(getActivity());
        filterSaved.setCancelable(true);
        filterSaved.setContentView(R.layout.dialogue_event_saved);
        TextView gotIt = (TextView) filterSaved.findViewById(R.id.eventSaved);
        gotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterSaved.dismiss();
            }
        });
        filterSaved.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }




    void SaveFilters(final String name, final boolean exit, boolean NEW, String date){
        dialog.dismiss();
        progressDialog.show();
            GenericRequest<SaveEventResponse> request = new GenericRequest<SaveEventResponse>(Request.Method.POST, CONSTANTS.URL_SAVE_EVENT, SaveEventResponse.class, new SaveVenueRequest(storage.loadID(),name,"",storage.loadCityID(),storage.loadEventID(),date,"0","0","0","0",moreManager.getSelectedFilters()), new Response.Listener<SaveEventResponse>() {
                @Override
                public void onResponse(SaveEventResponse response) {
                    progressDialog.dismiss();
                    dialog.dismiss();
                    if (Boolean.parseBoolean(response.getResponse().get(0).getStatus())){
                        Email(storage.loadEventID(), storage.loadID());
                        if (exit){
                            Intent intent = new Intent(getActivity(), CitySearchActivity.class);
                            startActivity(intent);
                        }else {
                            final Dialog successDialog = new Dialog(getActivity());
                            successDialog.setCancelable(true);
                            successDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            successDialog.setContentView(R.layout.dialog_success_on_more_fragment);

                            TextView messageTitle=(TextView)successDialog.findViewById(R.id.messageTitle);
                            TextView messageBody=(TextView)successDialog.findViewById(R.id.messageBody);

                            TextView consulting = (TextView) successDialog.findViewById(R.id.eventSaved);
                            TextView saveandexit = (TextView) successDialog.findViewById(R.id.maybelater);
                            saveandexit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
//                                    Intent intent = new Intent(getActivity(), CitySearchActivity.class);
//                                    Intent intent = new Intent(getActivity(), AllEventsActivity.class);
//                                    startActivity(intent);
                                    GetallEvents();
                                }
                            });

                            consulting.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    successDialog.dismiss();
                                    getCounsultingPricing();

                                }

                            });
                            successDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                            successDialog.show();

                            // filterSaved.show();

                        }
                    }else {
                        GeneralUtils.ShowAlert(getActivity(),response.getResponse().get(0).getMesssage());
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    dialog.dismiss();
                    GeneralUtils.ShowAlert(getActivity(),getString(R.string.VolleyTimeout));

                }
            });

            AppController.getInstance().addToRequestQueue(request);

    }



    void getCounsultingPricing() {
        progressDialog.show();
        GenericRequest<ConsultingResponse> request = new GenericRequest<ConsultingResponse>(Request.Method.POST, CONSTANTS.URL_CONSULTING_PRICE, ConsultingResponse.class,
                null, new Response.Listener<ConsultingResponse>() {
            @Override
            public void onResponse(ConsultingResponse response) {
                progressDialog.dismiss();
                if (Boolean.valueOf(response.getResponse().get(0).getStatus())) {

                    Intent intent = new Intent(getActivity(), ConsultingServiceActivity.class);
                    intent.putExtra("data", response);
                    intent.putExtra("ComeFrom","MoreFragment");
                    intent.putExtra("eventId",storage.loadEventID());
                    intent.putExtra("eventName",storage.loadEventName());
                    startActivity(intent);

                } else {
                    GeneralUtils.ShowAlert(getActivity(), response.getResponse().get(0).getMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                GeneralUtils.ShowAlert(getActivity(),getString(R.string.VolleyTimeout));
                progressDialog.dismiss();

            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }


    @Override
    public void onResume() {
        super.onResume();
        moreAdapter.notifyDataSetChanged();
    }

    void GetallEvents() {
        GenericRequest<GetSavedEventListResponse> request = new GenericRequest<GetSavedEventListResponse>(Request.Method.POST, CONSTANTS.URL_GET_SAVED_EVENT_LIST, GetSavedEventListResponse.class,
                new GetSavedVenueRequest(storage.loadID())
                , new Response.Listener<GetSavedEventListResponse>() {
            @Override
            public void onResponse(GetSavedEventListResponse response) {
                progressDialog.dismiss();
                if (Boolean.valueOf(response.getResponse().get(0).getStatus())) {
                    Intent i = new Intent(getActivity(), AllEventsActivity.class);
                    i.putExtra("events", response);
                    startActivity(i);
                    getActivity().finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                GeneralUtils.ShowAlert(getActivity(), getString(R.string.VolleyTimeout));
            }
        });

        AppController.getInstance().addToRequestQueue(request);
//        request.setRetryPolicy(new DefaultRetryPolicy(
//                500,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    void Email(String EventID, String UserID) {
        progressDialog.show();
        GenericRequest<EventEmailResponse> request = new GenericRequest<EventEmailResponse>( Request.Method.POST, CONSTANTS.URL_EMAIL_ALLEVENTS, EventEmailResponse.class,
                new EventEmailRequest( EventID, UserID ), new Response.Listener<EventEmailResponse>() {
            @Override
            public void onResponse(EventEmailResponse response) {
                progressDialog.dismiss();
                Log.e("Response ", response.getResponse().get( 0 ).getCode());
//                GeneralUtils.CustomDialogSucessWithImage( AllEventsActivity.this, "Email Configuration Sent", "Event configuration has been sent to your Email ID" );
//                 GeneralUtils.ShowAlert(getActivity(),"BOQ sent to Registered Email Account");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                GeneralUtils.ShowAlert( getActivity(), getString( R.string.VolleyTimeout ) );
            }
        } );
        AppController.getInstance().addToRequestQueue( request );
        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
}
