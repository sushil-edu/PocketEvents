package kestone.com.pocketevents.Fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.UnicodeSetSpanner;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import kestone.com.pocketevents.Adapters.DialogueAdapters.PhoneNumbersAdapter;
import kestone.com.pocketevents.Adapters.DialogueAdapters.SelectHallCapacityAdapter;
import kestone.com.pocketevents.Adapters.SelectedVenueFragment.HallDetailsAdapter;
import kestone.com.pocketevents.Adapters.SelectedVenueFragment.SelectedVenueHallAdapter;
import kestone.com.pocketevents.Adapters.SetupFragment.YourSelectionsAdapter;
import kestone.com.pocketevents.Adapters.VenueFragment.VenueCardInnerListAdapter;
import kestone.com.pocketevents.Adapters.VenueFragment.VenueCardInnerRVAdapter;
import kestone.com.pocketevents.Adapters.VenueFragment.VenueImagesAdapter;
import kestone.com.pocketevents.MODEL.Manager.FilterManager;
import kestone.com.pocketevents.MODEL.Manager.MySingleton;
import kestone.com.pocketevents.MODEL.SaveVenue.REQUEST.SaveVenueRequest;
import kestone.com.pocketevents.MODEL.SaveVenue.RESPONSE.SaveEventResponse;
import kestone.com.pocketevents.MODEL.Setup.REQUEST.SetupRequest;
import kestone.com.pocketevents.MODEL.Setup.RESPONSE.SetupResponse;
import kestone.com.pocketevents.MODEL.Venue.RESPONSE.Attributes;
import kestone.com.pocketevents.MODEL.Venue.RESPONSE.Halls;
import kestone.com.pocketevents.R;
import kestone.com.pocketevents.UI.AppController;
import kestone.com.pocketevents.UI.CitySearchActivity;
import kestone.com.pocketevents.Utilities.CONSTANTS;
import kestone.com.pocketevents.Utilities.GeneralUtils;
import kestone.com.pocketevents.Utilities.GenericRequest;
import kestone.com.pocketevents.Utilities.PrefEntities;
import kestone.com.pocketevents.Utilities.StorageUtilities;

/**
 * Created by karan on 8/10/2017.
 */

public class SelectedVenueFragment extends Fragment implements View.OnClickListener {
    TextView venueName, venueAddress, keystoneRating;
    TextView save, saveExit, fragment_setup_HallName, fragment_setup_VenueName;
    RelativeLayout btnMap, btnCall;
    RatingBar kestoneRatingBar;
    RecyclerView filterList, fragment_setup_rv1;
    RecyclerView hallGrid;
    ImageView isrecomended, image, hallIv;
    LinearLayout root;
    FilterManager filterManager;
    String[] images;
    ProgressDialog progressDialog;
    Dialog dialog, filterSaved;

    int mYear, mMonth, mDay;
    int y1, m1, d1;
    int y2, m2, d2;
    List<Halls> halls = new ArrayList<>();
    String CurrentDate;
    String eventDate="";


    VenueCardInnerListAdapter innerListAdapter;

    SelectedVenueHallAdapter selectedVenueHallAdapter;

    StorageUtilities storage;
    TextView venueFilterChangeCity;
    int pos;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        storage = new StorageUtilities(getActivity());
        filterManager = new FilterManager(getActivity());
        SetupFilterSavedDialogue();
        updateActivityUI();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        View view = inflater.inflate(R.layout.fragment_selected_venue_new, container, false);
        venueName = (TextView) view.findViewById(R.id.scard_venue_name);
        kestoneRatingBar = (RatingBar) view.findViewById(R.id.scard_kestone_rating);
        root = (LinearLayout) view.findViewById(R.id.svenueroot);
        venueAddress = (TextView) view.findViewById(R.id.scard_venue_add);
        keystoneRating = (TextView) view.findViewById(R.id.scard_venue_kestone_rating);
        fragment_setup_HallName = (TextView) view.findViewById(R.id.fragment_setup_HallName);
        fragment_setup_VenueName = (TextView) view.findViewById(R.id.fragment_setup_VenueName);
        hallIv = (ImageView) view.findViewById(R.id.hallIv);
        fragment_setup_rv1 = (RecyclerView) view.findViewById(R.id.fragment_setup_rv1);

        //tripadvisorRating = (TextView)view.findViewById(R.id.scard_venue_tripadvisor_rating);
        isrecomended = (ImageView) view.findViewById(R.id.scard_venue_isrecomended);
        image = (ImageView) view.findViewById(R.id.svenue_card_thumb);
        image.setOnClickListener(this);
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        final java.util.Calendar c = java.util.Calendar.getInstance();
        mYear = c.get(java.util.Calendar.YEAR);
        mMonth = c.get(java.util.Calendar.MONTH);
        mDay = c.get(java.util.Calendar.DAY_OF_MONTH);

        y1 = mYear;
        m1 = mMonth;
        d1 = mDay;
        CurrentDate = new SimpleDateFormat("yyyy/MM/dd").format(new Date());

        btnCall = (RelativeLayout) view.findViewById(R.id.svenue_card_btn_call);
        btnCall.setOnClickListener(this);
        btnMap = (RelativeLayout) view.findViewById(R.id.svenue_card_btn_map);
        btnMap.setOnClickListener(this);
        save = (TextView) view.findViewById(R.id.svenueFilterSave);
        save.setOnClickListener(this);
        saveExit = (TextView) view.findViewById(R.id.svenueFilterSaveAndExit);
        saveExit.setOnClickListener(this);

        filterList = (RecyclerView) view.findViewById(R.id.scard_list_details);
        //hallGrid = (RecyclerView)view.findViewById(R.id.scard_rv_halls);
        innerListAdapter = new VenueCardInnerListAdapter(getActivity(), (List) getArguments().getSerializable("vAttributes"));
        selectedVenueHallAdapter = new SelectedVenueHallAdapter(getActivity(), (List) getArguments().getSerializable("vHalls"), pos);
        //filterList.setAdapter(innerListAdapter);
        VenueCardInnerRVAdapter venueCardInnerRVAdapter = new VenueCardInnerRVAdapter(getContext(), (List) getArguments().getSerializable("vAttributes"));
        filterList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        filterList.setAdapter(venueCardInnerRVAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//        hallGrid.setLayoutManager(layoutManager);
//        hallGrid.setItemAnimator(new DefaultItemAnimator());
//        hallGrid.setAdapter(selectedVenueHallAdapter);
        List<Halls> hallsList = ((List) getArguments().getSerializable("vHalls"));

//        for (int i = 0; i< ((List) getArguments().getSerializable("vAttributes")).size();i++){
//            if (i == MySingleton.getInstance().getSelectedHallPosition())
//                halls.add(( getArguments().getSerializable("vAttributes").get(i)));
//        }

        Halls halls = hallsList.get(MySingleton.getInstance().getSelectedHallPosition());
        fragment_setup_HallName.setText(halls.getHallName());
        fragment_setup_VenueName.setText(getArguments().getString("vName"));
        Glide.with(getActivity()).load(halls.getHallImages()[0]).crossFade().placeholder(R.drawable.placeholder).into(hallIv);

        LinearLayoutManager layout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        fragment_setup_rv1.setLayoutManager(layoutManager);
        HallDetailsAdapter hallDetailsAdapter = new HallDetailsAdapter(getActivity(), halls.getCapacities());
        fragment_setup_rv1.setAdapter(hallDetailsAdapter);


        Log.d("SelectedHall", halls.getHallName());
        images = getArguments().getStringArray("vImages");
        venueName.setText(getArguments().getString("vName"));
        venueAddress.setText(getArguments().getString("vAdd"));
        keystoneRating.setText(getArguments().getString("vKestoneRating"));
        kestoneRatingBar.setRating(Float.valueOf(getArguments().getString("vKestoneRating")));
        pos = getArguments().getInt("position");

        Glide.with(getActivity()).load(images[0]).crossFade().placeholder(R.drawable.placeholder).into(image);
        return view;


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.venueFilterChangeCity:

                Intent i;
                i = new Intent(getActivity(), CitySearchActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                break;


            case R.id.svenue_card_thumb:
                if (images.length > 0) {
                    dialog = new Dialog(getActivity());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(true);
                    dialog.setContentView(R.layout.dialogue_venue_images);
                    RecyclerViewPager rv = (RecyclerViewPager) dialog.findViewById(R.id.venue_images_dialogue_recycler);
                    VenueImagesAdapter adapter = new VenueImagesAdapter(getActivity(), images);
                    RecyclerView.LayoutManager lm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
                    rv.setLayoutManager(lm);
                    rv.setAdapter(adapter);
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog.show();
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    alert.setTitle(getString(R.string.app_name));
                    alert.setMessage(getString(R.string.NoImagesToDisplay));
                    alert.setCancelable(true);
                    final AlertDialog alertDialog = alert.create();
                    alertDialog.show();
                }
                break;


            case R.id.svenue_card_btn_call:
                dialog = new Dialog(getActivity());
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialogue_phone);
                ListView phoneList = (ListView) dialog.findViewById(R.id.phone_lv);
                PhoneNumbersAdapter adapter = new PhoneNumbersAdapter(getActivity(), getArguments().getStringArray("vPhone"));
                phoneList.setAdapter(adapter);
                phoneList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + adapterView.getItemAtPosition(i)));
                        startActivity(intent);
                    }
                });
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();
                break;


            case R.id.svenue_card_btn_map:

                String uri = String.format(Locale.ENGLISH, "geo:%s,%s?z=25&q=%s", getArguments().get("vLat"), getArguments().get("vLon"), getArguments().get("vName"));
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
                break;

            case R.id.svenueFilterSave:
                if (storage.loadEventID().equals("9999")) {
                    dialog = new Dialog(getActivity());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(true);
                    dialog.setContentView(R.layout.dialogue_enter_event_name);
                    final EditText enterEventName = (EditText) dialog.findViewById(R.id.enterEventName);
                    final EditText enterEventDate = (EditText) dialog.findViewById(R.id.enterEventDate);

                    enterEventDate.setOnClickListener(new View.OnClickListener() {
                        int mYear, mMonth, mDay;

                        @SuppressLint("NewApi")
                        @Override
                        public void onClick(View view) {

                            if (GeneralUtils.hasKitKat()) {
                                final java.util.Calendar c = java.util.Calendar.getInstance();
                                mYear = c.get(java.util.Calendar.YEAR);
                                mMonth = c.get(java.util.Calendar.MONTH);
                                mDay = c.get(java.util.Calendar.DAY_OF_MONTH);
                            } else {
                                final android.icu.util.Calendar c = android.icu.util.Calendar.getInstance();
                                mYear = c.get(android.icu.util.Calendar.YEAR);
                                mMonth = c.get(android.icu.util.Calendar.MONTH);
                                mDay = c.get(android.icu.util.Calendar.DAY_OF_MONTH);
                            }
                            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                                    y2 = year;
                                    m2 = monthOfYear+1;
                                    d2 = dayOfMonth;
                                    enterEventDate.setText("");
                                    String selected_date = d2+"/"+m2+"/"+y2;
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                    Date strDate=null;
                                    eventDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                    try {
                                        strDate = sdf.parse( selected_date);
                                        if(new Date(  ).compareTo( strDate )==-1){
                                            enterEventDate.setText(eventDate);
                                        } else {
                                            GeneralUtils.ShowAlert(getActivity(), "Please Select Valid Date");
                                            enterEventDate.setText("");
                                        }
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
//                                    if (y1 <= y2) {
//                                        if (m1 <= m2 || y1 < y2) {
//
//                                            if (d1 <= d2 || m1 < m2 ) {
//                                                enterEventDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
//
//                                            } else {
//                                                GeneralUtils.ShowAlert(getActivity(), "Please Select Valid Date");
//                                                enterEventDate.setText("");
//                                            }
//                                        } else {
//                                            GeneralUtils.ShowAlert(getActivity(), "Please Select Valid Date");
//                                            enterEventDate.setText("");
//                                        }
//                                    } else {
//                                        GeneralUtils.ShowAlert(getActivity(), "Please Select Valid Date");
//                                        enterEventDate.setText("");
//                                    }

                                    //  enterEventDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
//                                            enterEventDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                                }
                            }, mYear, mMonth, mDay);
                            datePickerDialog.show();
                        }
                    });
                    TextView save = (TextView) dialog.findViewById(R.id.saveEventButton);
                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (enterEventName.getText().length() > 3) {
                                if (!eventDate.isEmpty()) {
                                    SaveFilters(enterEventName.getText().toString(), false, true, eventDate);
                                } else {
                                    GeneralUtils.ShowAlert(getActivity(), "Enter Event Date");
                                }
                            } else {
                                GeneralUtils.ShowAlert(getActivity(), "Event Name should atleast be 4 Characters");
                            }


                        }
                    });
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.show();

                } else {
                    new AlertDialog.Builder(getActivity())
                            .setMessage(getString(R.string.SaveFilter))
                            .setNegativeButton("No", null)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface arg0, int arg1) {
                                    SaveFilters(storage.loadEventName(), false, false, storage.loadEventDate());
                                }
                            }).create().show();

                }
                // filterManager.getSelectedFilters();
                break;

            case R.id.svenueFilterSaveAndExit:
                if (storage.loadEventID().equals("9999")) {
                    //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialogue_enter_event_name);
                    final EditText enterEventName = (EditText) dialog.findViewById(R.id.enterEventName);
                    final EditText enterEventDate = (EditText) dialog.findViewById(R.id.enterEventDate);

                    enterEventDate.setOnClickListener(new View.OnClickListener() {
                        int mYear, mMonth, mDay;

                        @Override
                        public void onClick(View view) {

                            if (GeneralUtils.hasKitKat()) {
                                final java.util.Calendar c = java.util.Calendar.getInstance();
                                mYear = c.get(java.util.Calendar.YEAR);
                                mMonth = c.get(java.util.Calendar.MONTH);
                                mDay = c.get(java.util.Calendar.DAY_OF_MONTH);
                            } else {
                                final android.icu.util.Calendar c = android.icu.util.Calendar.getInstance();
                                mYear = c.get(android.icu.util.Calendar.YEAR);
                                mMonth = c.get(android.icu.util.Calendar.MONTH);
                                mDay = c.get(android.icu.util.Calendar.DAY_OF_MONTH);
                            }
                            final DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

                                    String selected_date = ""+y2+"/"+m2+"/"+d2;
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                                    eventDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                    Date strDate=null;
                                    try {
                                        strDate = sdf.parse( selected_date.toString().trim() );
                                        if(new Date(  ).compareTo( strDate )==-1){
                                            enterEventDate.setText(eventDate);

                                        } else {
                                            GeneralUtils.ShowAlert(getActivity(), "Please Select Valid Date");
                                            enterEventDate.setText("");
                                        }
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }


//                                    y2 = year;
//                                    m2 = monthOfYear;
//                                    d2 = dayOfMonth;
//                                    enterEventDate.setText("");
//                                    if (y1 <= y2) {
//                                        if (m1 <= m2 || y1 < y2) {
//                                            if (d1 <= d2 || m1 < m2) {
//                                                enterEventDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
//
//                                            } else {
//                                                GeneralUtils.ShowAlert(getActivity(), "Please Select Valid Date");
//                                                enterEventDate.setText("");
//                                            }
//                                        } else {
//                                            GeneralUtils.ShowAlert(getActivity(), "Please Select Valid Date");
//                                            enterEventDate.setText("");
//                                        }
//                                    } else {
//                                        GeneralUtils.ShowAlert(getActivity(), "Please Select Valid Date");
//                                        enterEventDate.setText("");
//                                    }

                                    // enterEventDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                }
                            }, mYear, mMonth, mDay);
                            datePickerDialog.show();
                        }
                    });

                    TextView save = (TextView) dialog.findViewById(R.id.saveEventButton);
                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            if (enterEventName.getText().length() > 3) {
                                if (!eventDate.isEmpty()) {
                                    SaveFilters(enterEventName.getText().toString(), true, true, eventDate);
                                } else {
                                    GeneralUtils.ShowAlert(getActivity(), "Enter Event Date");
                                }
                            } else {
                                GeneralUtils.ShowAlert(getActivity(), "Event Name should atleast be 4 Characters");
                            }
                        }
                    });
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.show();

                } else {
                    new AlertDialog.Builder(getActivity())
                            .setMessage(getString(R.string.SaveExit))
                            .setNegativeButton("No", null)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface arg0, int arg1) {
                                    SaveFilters(storage.loadEventName(), true, false, "");
                                }
                            }).create().show();

                }
                break;
        }
    }


    void SaveFilters(final String name, final boolean exit, boolean NEW, String date) {
        dialog.dismiss();

        if (NEW) {
            GenericRequest<SaveEventResponse> request = new GenericRequest<SaveEventResponse>(Request.Method.POST, CONSTANTS.URL_SAVE_EVENT, SaveEventResponse.class, new SaveVenueRequest(storage.loadID(), name, filterManager.getSelectedFilters(), storage.loadCityID(), "0", date, storage.LoadKey(PrefEntities.HALLID), storage.LoadKey(PrefEntities.VENUEID), "0", "0", ""), new Response.Listener<SaveEventResponse>() {
                @Override
                public void onResponse(final SaveEventResponse response) {
                    if (Boolean.parseBoolean(response.getResponse().get(0).getStatus())) {
                        //Toast.makeText(getApplicationContext(),response.getResponse().get(0).getMesssage(),Toast.LENGTH_LONG).show();
                        storage.storeEventName(name);
                        storage.storeEventID(response.getResponse().get(0).getPayload().get(0).getEventId());
                        storage.storeEventDate(response.getResponse().get(0).getPayload().get(0).getEventDate());
                        filterSaved.show();

                        if (exit) {
                            Intent intent = new Intent(getActivity(), CitySearchActivity.class);
                            startActivity(intent);
                        } else {
                            if (GeneralUtils.isNetworkAvailable(getActivity())) {

                                final Dialog VenuesetUpSuccess = new Dialog(getActivity());
                                VenuesetUpSuccess.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                VenuesetUpSuccess.setCancelable(true);
                                VenuesetUpSuccess.setContentView(R.layout.dialogue_venue_saved_sucessfully);
                                TextView procceed = (TextView) VenuesetUpSuccess.findViewById(R.id.proceed);
                                procceed.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        Setup(response.getResponse().get(0).getPayload().get(0).getEventId(), storage.LoadKey(PrefEntities.VENUEID), storage.LoadKey(PrefEntities.HALLID), storage.loadCityID());

                                        VenuesetUpSuccess.dismiss();
                                    }

                                });
                                VenuesetUpSuccess.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                                VenuesetUpSuccess.show();

                                // Setup(response.getResponse().get(0).getPayload().get(0).getEventId(),storage.LoadKey(PrefEntities.VENUEID),storage.LoadKey(PrefEntities.HALLID),storage.loadCityID());
                            } else {
                                GeneralUtils.displayNetworkAlert(getActivity(), false);
                            }
                        }

                    } else {
                        GeneralUtils.ShowAlert(getActivity(), response.getResponse().get(0).getMesssage());
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    GeneralUtils.ShowAlert(getActivity(), getString(R.string.VolleyTimeout));

                }
            });

            AppController.getInstance().addToRequestQueue(request);
            request.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        } else {

            if (GeneralUtils.isNetworkAvailable(getActivity())) {
                GenericRequest<SaveEventResponse> request = new GenericRequest<SaveEventResponse>(Request.Method.POST, CONSTANTS.URL_SAVE_EVENT, SaveEventResponse.class, new SaveVenueRequest(storage.loadID(), name, filterManager.getSelectedFilters(), storage.loadCityID(), storage.loadEventID(), date, storage.LoadKey(PrefEntities.HALLID), storage.LoadKey(PrefEntities.VENUEID), "0", "0", ""), new Response.Listener<SaveEventResponse>() {
                    @Override
                    public void onResponse(final SaveEventResponse response) {
                        dialog.dismiss();
                        if (Boolean.valueOf(response.getResponse().get(0).getStatus())) {
                            //Toast.makeText(getApplicationContext(),response.getResponse().get(0).getMesssage(),Toast.LENGTH_LONG).show();
                            storage.storeEventName(name);
                            storage.storeEventID(response.getResponse().get(0).getPayload().get(0).getEventId());
                            storage.storeEventDate(response.getResponse().get(0).getPayload().get(0).getEventDate());
                            filterSaved.show();

                            if (exit) {
                                Intent intent = new Intent(getActivity(), CitySearchActivity.class);
                                startActivity(intent);
                            } else {

                                final Dialog VenuesetUpSuccess = new Dialog(getActivity());
                                VenuesetUpSuccess.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                VenuesetUpSuccess.setCancelable(true);
                                VenuesetUpSuccess.setContentView(R.layout.dialogue_venue_saved_sucessfully);
                                TextView procceed = (TextView) VenuesetUpSuccess.findViewById(R.id.proceed);
                                procceed.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        Setup(response.getResponse().get(0).getPayload().get(0).getEventId(), storage.LoadKey(PrefEntities.VENUEID), storage.LoadKey(PrefEntities.HALLID), storage.loadCityID());

                                        VenuesetUpSuccess.dismiss();
                                    }

                                });
                                VenuesetUpSuccess.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                                VenuesetUpSuccess.show();


                            }
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        GeneralUtils.ShowAlert(getActivity(), getString(R.string.VolleyTimeout));

                    }
                });

                AppController.getInstance().addToRequestQueue(request);
                request.setRetryPolicy(new DefaultRetryPolicy(
                        0,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            } else {
                GeneralUtils.displayNetworkAlert(getActivity(), false);
            }


        }

    }


    void Setup(String eventID, String venueID, String hallID, String cityID) {
        progressDialog.show();
        GenericRequest<SetupResponse> request = new GenericRequest<SetupResponse>(Request.Method.POST, CONSTANTS.URL_SETUP, SetupResponse.class,
                new SetupRequest(eventID, venueID, hallID, cityID), new Response.Listener<SetupResponse>() {
            @Override
            public void onResponse(SetupResponse response) {
                progressDialog.dismiss();
                if (Boolean.valueOf(response.getResponse().get(0).getStatus())) {
                    SetupVenueFragment setupVenueFragment = new SetupVenueFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data", (Serializable) response);
                    setupVenueFragment.setArguments(bundle);

                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.replace(R.id.venue_filter_placeholder, setupVenueFragment, CONSTANTS.FRAGMENT_SETUP);
                    getActivity().getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    ft.commit();
                } else {
                    GeneralUtils.ShowAlert(getActivity(), response.getResponse().get(0).getMessage());
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
    }

    void SetupFilterSavedDialogue() {
        filterSaved = new Dialog(getActivity());
        filterSaved.requestWindowFeature(Window.FEATURE_NO_TITLE);
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

    void updateActivityUI() {
        Intent intent = new Intent("updateUI");
        intent.putExtra("frag", 1);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }


}
