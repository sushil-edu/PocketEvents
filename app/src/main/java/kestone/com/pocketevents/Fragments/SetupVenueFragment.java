package kestone.com.pocketevents.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import kestone.com.pocketevents.Adapters.SetupFragment.SetupAdapter;
import kestone.com.pocketevents.Adapters.SetupFragment.YourSelectionsAdapter;
import kestone.com.pocketevents.MODEL.ContactUs.ContactUsResponse;
import kestone.com.pocketevents.MODEL.Design.REQUEST.DesignRequest;
import kestone.com.pocketevents.MODEL.Design.RESPONSE.DesignResponse;
import kestone.com.pocketevents.MODEL.SaveVenue.REQUEST.SaveVenueRequest;
import kestone.com.pocketevents.MODEL.SaveVenue.RESPONSE.SaveEventResponse;
import kestone.com.pocketevents.MODEL.Setup.Detail;
import kestone.com.pocketevents.MODEL.Setup.RESPONSE.Data;
import kestone.com.pocketevents.MODEL.Setup.RESPONSE.Details;
import kestone.com.pocketevents.MODEL.Setup.RESPONSE.SetupResponse;
import kestone.com.pocketevents.MODEL.Setup.SetUpDetails;
import kestone.com.pocketevents.MODEL.Setup.SetupAPIResult;
import kestone.com.pocketevents.MODEL.Theme.REQUEST.DesignRequestTheme;
import kestone.com.pocketevents.MODEL.Theme.RESPONSE.DesignResponseTheme;
import kestone.com.pocketevents.MODEL.Theme.RESPONSE.ThemeResponse;
import kestone.com.pocketevents.MODEL.Theme.RESPONSE.ThemeResult;
import kestone.com.pocketevents.R;
import kestone.com.pocketevents.UI.AppController;
import kestone.com.pocketevents.UI.CitySearchActivity;
import kestone.com.pocketevents.UI.ContactUsActivity;
import kestone.com.pocketevents.UI.SetupActivity;
import kestone.com.pocketevents.Utilities.CONSTANTS;
import kestone.com.pocketevents.Utilities.GeneralUtils;
import kestone.com.pocketevents.Utilities.GenericRequest;
import kestone.com.pocketevents.Utilities.PrefEntities;
import kestone.com.pocketevents.Utilities.StorageUtilities;

/**
 * Created by karan on 8/25/2017.
 */

public class SetupVenueFragment extends Fragment implements SetupAdapter.SetupAdapterCallBack, View.OnClickListener {

    SetupAdapter setupAdapter;
    YourSelectionsAdapter yourSelectionsAdapter;
    TextView venueName, hallName;
    RecyclerView rv1;
    StorageUtilities storage;
    RecyclerViewPager rv2;
    TextView save, saveExit;
    Dialog dialog, filterSaved;
    ProgressDialog progressDialog;
    String setupId = null;
    int selectedPosition = -1;
    TextView choose_city;
    ImageView hallIv;
    SetUpDetails setUpDetails;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_setup, container, false);
        setupId = null;
        updateActivityUI();
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        SetupFilterSavedDialogue();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait");
        save = (TextView) rootView.findViewById(R.id.setupSave);
        hallIv = (ImageView) rootView.findViewById(R.id.hallIv);
        save.setOnClickListener(this);
        saveExit = (TextView) rootView.findViewById(R.id.setupSaveExit);
        saveExit.setOnClickListener(this);
        rv1 = (RecyclerView) rootView.findViewById(R.id.fragment_setup_rv1);
        storage = new StorageUtilities(getActivity());
        rv2 = (RecyclerViewPager) rootView.findViewById(R.id.fragment_setup_rv2);
        venueName = (TextView) rootView.findViewById(R.id.fragment_setup_VenueName);
        hallName = (TextView) rootView.findViewById(R.id.fragment_setup_HallName);
        if ((getArguments() != null)) {
            SetupResponse response = (SetupResponse) getArguments().getSerializable("data");
            setupAdapter = new SetupAdapter(this, response.getResponse().get(0).getPayload().get(0).getData(), getActivity());
            yourSelectionsAdapter = new YourSelectionsAdapter(getActivity(), response.getResponse().get(0).getPayload().get(0).getSelectedFilter());
            venueName.setText(response.getResponse().get(0).getPayload().get(0).getVenueName());
            hallName.setText(response.getResponse().get(0).getPayload().get(0).getHallName());

            if (response.getResponse().get(0).getPayload().get(0).getHallImage().length() > 0) {
                Glide.with(getContext()).load(response.getResponse().get(0).getPayload().get(0).getHallImage())
                        .crossFade().placeholder(R.drawable.placeholder)
                        .into(hallIv);
            }

        }
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);

        LinearLayoutManager layout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        rv1.setLayoutManager(layoutManager);
        rv2.setLayoutManager(layout);
        rv1.setAdapter(yourSelectionsAdapter);
        rv2.setAdapter(setupAdapter);


        return rootView;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void onSelectClick(Data data, int position) {
        //storage.StoreKey(PrefEntities.SETUPID,data.getSetupId());
        if (selectedPosition == -1) {
            setupAdapter.SelectSetup(position);
            setupAdapter.notifyDataSetChanged();
            setupId = data.getSetupId();
            selectedPosition = position;
            save.setBackgroundColor(getResources().getColor(R.color.textColorRed));
        } else {
            setupAdapter.SelectSetup(-1);
            setupAdapter.notifyDataSetChanged();
            setupId = null;
            selectedPosition = -1;
            save.setBackgroundColor(getResources().getColor(R.color.btnColorGrey));

        }
    }

    @Override
    public void onDetailsClicked(List<Details> details, String[] images, String banner) {
        Log.e( "SetUp Name ", banner );
//                Toast.makeText(getActivity(),"SetUp Name "+banner,Toast.LENGTH_LONG).show();
//        Intent intent = new Intent(getActivity(), SetupActivity.class);
//        intent.putExtra("data", (Serializable) details);
//        intent.putExtra("images", images);
//        intent.putExtra("banner", banner);
//        startActivity(intent);
    }

    @Override
    public void onSetUpDetails(String banner) {
        List<Detail> details = null;
        String[] images = new String[0];
        setUpDetails = new Gson().fromJson( new ReadJson().loadJSONFromAsset( getActivity(), "get_setup_detail.json" ), SetUpDetails.class );
        Log.e("API RESULT ", ""+setUpDetails.getSetupAPIResult().get( 0 ).getCode());
        for(int d =0;d<setUpDetails.getSetupAPIResult().get( 0 ).getPayload().get( 0 ).getData().size();d++){
            if(setUpDetails.getSetupAPIResult().get( 0 ).getPayload().get( 0 ).getData().get( d ).getSetupName().equalsIgnoreCase( banner)){
                details=setUpDetails.getSetupAPIResult().get( 0 ).getPayload().get( 0 ).getData().get( d ).getDetails();
                images= setUpDetails.getSetupAPIResult().get( 0 ).getPayload().get( 0 ).getData().get( d ).getPackageImages().toArray( new String[0] );
                break;
            }
        }

        Intent intent = new Intent(getActivity(), SetupActivity.class);
        intent.putExtra("data", (Serializable) details );
        intent.putExtra("images", images);
        intent.putExtra("banner", banner);
        startActivity(intent);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.setupSave:


                if (setupId != null) {
                    Log.d("setupId", setupId + "");

//                    new AlertDialog.Builder(getActivity())
//                            .setMessage(getString(R.string.SaveFilter))
//                            .setNegativeButton("No", null)
//                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//
//                                public void onClick(DialogInterface arg0, int arg1) {
//                                    SaveFilters(storage.loadEventName(), false, false, storage.loadEventDate());
//                                }
//                            }).create().show();

                    SaveFilters(storage.loadEventName(), false, false, storage.loadEventDate());

//                    final Dialog dialog = new Dialog(getContext());
//                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    dialog.setContentView(R.layout.dialog_save_and_proceed);
//                    TextView yes = (TextView) dialog.findViewById(R.id.yes);
//                    yes.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            SaveFilters(storage.loadEventName(), false, false, storage.loadEventDate());
//                            dialog.cancel();
//                        }
//                    });
//
//                    TextView no = (TextView) dialog.findViewById(R.id.no);
//                    no.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            dialog.cancel();
//                        }
//                    });
//
//                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                    dialog.show();

                    //    }
                    // filterManager.getSelectedFilters();
                } else {
                    GeneralUtils.ShowAlert(getActivity(), "Please Select a Setup first");
                }
                break;
            case R.id.setupSaveExit:
                if (setupId != null) {

//                    new AlertDialog.Builder(getActivity())
//                            .setMessage(getString(R.string.SaveExit))
//                            .setNegativeButton("No", null)
//                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//
//                                public void onClick(DialogInterface arg0, int arg1) {
//                                    SaveFilters(storage.loadEventName(), true, false, storage.loadEventDate());
//                                }
//                            }).create().show();

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

                    //  }
                } else {
                    GeneralUtils.ShowAlert(getActivity(), "Please Select a Setup first");
                }
                break;
        }

    }


    void SaveFilters(final String name, final boolean exit, boolean NEW, String date) {
        dialog.dismiss();
        progressDialog.show();

        if (GeneralUtils.isNetworkAvailable(getActivity())) {
            GenericRequest<SaveEventResponse> request = new GenericRequest<SaveEventResponse>(Request.Method.POST,
                    CONSTANTS.URL_SAVE_EVENT, SaveEventResponse.class, new SaveVenueRequest(storage.loadID(),
                    name, "", storage.loadCityID(), storage.loadEventID(), date,
                    storage.LoadKey(PrefEntities.HALLID), storage.LoadKey(PrefEntities.VENUEID),
                    setupId, "0", ""), new Response.Listener<SaveEventResponse>() {
                @Override
                public void onResponse(SaveEventResponse response) {
                    progressDialog.dismiss();
                    dialog.dismiss();
                    if (Boolean.parseBoolean(response.getResponse().get(0).getStatus())) {

                        if (exit) {
                            Intent intent = new Intent(getActivity(), CitySearchActivity.class);
                            startActivity(intent);
                        } else {
                            //  filterSaved.show();
                            Design(storage.loadEventID());

                        }
                    } else {
                        GeneralUtils.ShowAlert(getActivity(), response.getResponse().get(0).getMesssage());
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    dialog.dismiss();
                    GeneralUtils.ShowAlert(getActivity(), getString(R.string.VolleyTimeout));
                }
            });

            AppController.getInstance().addToRequestQueue(request);
            request.setRetryPolicy( new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            ) );
        } else {
            GeneralUtils.displayNetworkAlert(getActivity(), false);
        }

    }

    //  }

    void SetupFilterSavedDialogue() {
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

    void updateActivityUI() {
        Intent intent = new Intent("updateUI");
        intent.putExtra("frag", 2);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
    }

//    void Design(String EventID) {
//
//        if (GeneralUtils.isNetworkAvailable(getActivity())) {
//            progressDialog.show();
//            GenericRequest<DesignResponse> request = new GenericRequest<DesignResponse>(Request.Method.POST, CONSTANTS.URL_DESIGN, DesignResponse.class,
//                    new DesignRequest(EventID), new Response.Listener<DesignResponse>() {
//                @Override
//                public void onResponse(final DesignResponse response) {
//                    progressDialog.dismiss();
//
//                    if (Boolean.parseBoolean(response.getResponse().get(0).getStatus())) {
//                        final Dialog setUpSuccess = new Dialog(getActivity());
//                        setUpSuccess.setCancelable(true);
//                        setUpSuccess.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                        setUpSuccess.setContentView(R.layout.dialog_set_up_selected_sucessfully);
//
//                        TextView procceed = (TextView) setUpSuccess.findViewById(R.id.proceed);
//
//                        procceed.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                setUpSuccess.dismiss();
//
//                                DesignFragment fragment = new DesignFragment();
//                                Bundle bundle = new Bundle();
//                                bundle.putSerializable("data", response);
//                                fragment.setArguments(bundle);
//                                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                                ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
//                                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                                ft.replace(R.id.venue_filter_placeholder, fragment, CONSTANTS.FRAGMENT_DESIGN);
//                                getActivity().getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                                ft.commit();
//                            }
//
//                        });
//                        setUpSuccess.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                        setUpSuccess.show();
//
//
//                    } else {
//                        GeneralUtils.ShowAlert(getActivity(), response.getResponse().get(0).getMessage());
//                    }
//
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    progressDialog.dismiss();
//                    GeneralUtils.ShowAlert(getActivity(), getString(R.string.VolleyTimeout));
//                }
//            });
//
//            AppController.getInstance().addToRequestQueue(request);
//
//        } else {
//            GeneralUtils.displayNetworkAlert(getActivity(), false);
//        }
//
//    }

    void Design(String EventID) {
        if (GeneralUtils.isNetworkAvailable(getActivity())) {
            progressDialog.show();
            GenericRequest<ThemeResponse> request = new GenericRequest<ThemeResponse>(Request.Method.GET, CONSTANTS.URL_DESIGN_THEME, ThemeResponse.class,
                    null,new Response.Listener<ThemeResponse>() {
                @Override
                public void onResponse(final ThemeResponse response) {
                    progressDialog.dismiss();
                    Log.e("Size ", ""+response.getGetThemeResult().size());
                    if (response.getGetThemeResult().size()>0) {
                        final Dialog setUpSuccess = new Dialog(getActivity());
                        setUpSuccess.setCancelable(true);
                        setUpSuccess.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        setUpSuccess.setContentView(R.layout.dialog_set_up_selected_sucessfully);

                        TextView procceed = (TextView) setUpSuccess.findViewById(R.id.proceed);

                        procceed.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                setUpSuccess.dismiss();

                                DesignFragment fragment = new DesignFragment();
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("data", response );
                                fragment.setArguments(bundle);
                                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
                                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                ft.replace(R.id.venue_filter_placeholder, fragment, CONSTANTS.FRAGMENT_DESIGN);
                                getActivity().getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                ft.commit();
                            }

                        });
                        setUpSuccess.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        setUpSuccess.show();


                    } else {
                        GeneralUtils.ShowAlert(getActivity(), "Theme not available");
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

        } else {
            GeneralUtils.displayNetworkAlert(getActivity(), false);
        }

    }

    private class ReadJson {
        public String loadJSONFromAsset(Activity activity, String fileName) {
            String json=null;
            try {
                InputStream is = activity.getAssets().open( fileName);
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read( buffer );
                is.close();
                json = new String( buffer, "UTF-8" );
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
            return json;
        }
    }
}

//        10-06 14:06:16.555 29161-29161/kestone.com.kestone D/userId: 18
//        10-06 14:06:16.556 29161-29161/kestone.com.kestone D/eventName: Gbb
//        10-06 14:06:16.556 29161-29161/kestone.com.kestone D/selectedFilters:
//        10-06 14:06:16.556 29161-29161/kestone.com.kestone D/cityId: 1
//        10-06 14:06:16.556 29161-29161/kestone.com.kestone D/EventID: 1219
//        10-06 14:06:16.556 29161-29161/kestone.com.kestone D/HallId: 3
//        10-06 14:06:16.556 29161-29161/kestone.com.kestone D/VenueId: 1
//        10-06 14:06:16.556 29161-29161/kestone.com.kestone D/eventDate: 2017-10-6
//        10-06 14:06:16.556 29161-29161/kestone.com.kestone D/SetupId: 1
