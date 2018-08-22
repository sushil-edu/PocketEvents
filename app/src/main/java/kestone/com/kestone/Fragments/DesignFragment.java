package kestone.com.kestone.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import java.util.List;

import it.gmariotti.recyclerview.adapter.AlphaAnimatorAdapter;
import kestone.com.kestone.Adapters.DesignFragment.DesignAdapter;
import kestone.com.kestone.Adapters.DesignFragment.DesignDialogueAdapter;
import kestone.com.kestone.MODEL.Design.RESPONSE.DesignResponse;
import kestone.com.kestone.MODEL.Design.RESPONSE.Values;
import kestone.com.kestone.MODEL.DesignEmail.REQUEST.DesignEmailRequest;
import kestone.com.kestone.MODEL.DesignEmail.RESPONSE.DesignEmailResponse;
import kestone.com.kestone.MODEL.Manager.MySingleton;
import kestone.com.kestone.MODEL.More.REQUEST.MoreRequest;
import kestone.com.kestone.MODEL.More.RESPONSE.MoreResponse;
import kestone.com.kestone.MODEL.SaveVenue.REQUEST.SaveVenueRequest;
import kestone.com.kestone.MODEL.SaveVenue.RESPONSE.SaveEventResponse;
import kestone.com.kestone.R;
import kestone.com.kestone.UI.AppController;
import kestone.com.kestone.UI.CitySearchActivity;
import kestone.com.kestone.Utilities.CONSTANTS;
import kestone.com.kestone.Utilities.GeneralUtils;
import kestone.com.kestone.Utilities.GenericRequest;
import kestone.com.kestone.Utilities.GridSpacingItemDecoration;
import kestone.com.kestone.Utilities.PrefEntities;
import kestone.com.kestone.Utilities.StorageUtilities;

/**
 * Created by karan on 10/12/2017.
 */

public class DesignFragment extends Fragment implements DesignAdapter.DesignCallback, View.OnClickListener {

    TextView venueText, hallText;
    TextView save, saveExit;
    RecyclerView recyclerView;
    DesignAdapter designAdapter;
    AlphaAnimatorAdapter animatorAdapter;
    DesignResponse data;
    Dialog dialog, filterSaved;
    StorageUtilities storage;
    ProgressDialog progressDialog;

    String designID = "";
    TextView venueFilter_layout_city;
    TextView next, previous;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_design, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        storage = new StorageUtilities(getActivity());
        updateActivityUI();
        SetupFilterSavedDialogue();

        venueFilter_layout_city = (TextView) view.findViewById(R.id.venueFilterChangeCity);
        venueFilter_layout_city.setOnClickListener(this);

        if (getArguments() != null) {
            data = (DesignResponse) getArguments().getSerializable("data");
        }
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        recyclerView = (RecyclerView) view.findViewById(R.id.design_recycler);
        venueText = (TextView) view.findViewById(R.id.design_venueText);
        venueText.setText(storage.loadEventName());
        //venueText.setText(data.getResponse().get(0).getVenueName());
        hallText = (TextView) view.findViewById(R.id.design_hallText);
        hallText.setText(data.getResponse().get(0).getHallName());
        save = (TextView) view.findViewById(R.id.designSave);
        save.setOnClickListener(this);
        saveExit = (TextView) view.findViewById(R.id.designSaveExit);
        saveExit.setOnClickListener(this);
        designAdapter = new DesignAdapter(getActivity(), data.getResponse().get(0).getPayload(), this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        animatorAdapter = new AlphaAnimatorAdapter(designAdapter, recyclerView);
        recyclerView.setAdapter(animatorAdapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    void updateActivityUI() {
        Intent intent = new Intent("updateUI");
        intent.putExtra("frag", 3);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public void onDesignClick(List<Values> data, boolean isSelected, final int Position, final String DesignId, String Title) {
        dialog.setContentView(R.layout.dialogue_design);
        dialog.setCancelable(true);
        final RecyclerViewPager recyclerView = (RecyclerViewPager) dialog.findViewById(R.id.designRV);
        TextView selected = (TextView) dialog.findViewById(R.id.designSelect);
        TextView email = (TextView) dialog.findViewById(R.id.designEmail);
        next = (TextView) dialog.findViewById(R.id.next);
        previous = (TextView) dialog.findViewById(R.id.previous);

        TextView title = (TextView) dialog.findViewById(R.id.designN);
        title.setText(Title +" Designs");
        final RecyclerView.LayoutManager lm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        DesignDialogueAdapter adapter = new DesignDialogueAdapter(getActivity(), data);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapter);

        //   Toast.makeText(getActivity(),String.valueOf(data.size()),Toast.LENGTH_LONG).show();
        if (data.size() <= 1) {
            next.setVisibility(View.INVISIBLE);
            previous.setVisibility(View.INVISIBLE);
        } else {
            next.setVisibility(View.VISIBLE);
            previous.setVisibility(View.VISIBLE);
        }


        if (isSelected) {
            selected.setText("Unselect");
            selected.setBackgroundColor(getResources().getColor(R.color.textColorRed));
            selected.setTextColor(getResources().getColor(R.color.textColorWhite));
        } else {
            selected.setText("Select");
            selected.setBackgroundColor(getResources().getColor(R.color.btnColorGrey));
            selected.setTextColor(getResources().getColor(R.color.textColorWhite));
        }


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int indexp = recyclerView.getCurrentPosition();
                recyclerView.smoothScrollToPosition(indexp + 1);

            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int indexm = recyclerView.getCurrentPosition();
                recyclerView.smoothScrollToPosition(indexm - 1);
            }
        });
        selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                designAdapter.setMultiSelect(Position);
                dialog.dismiss();

                designID = designAdapter.getSelected();
                if (!designID.equals("")) {
                    save.setBackgroundColor(getResources().getColor(R.color.textColorRed));
                } else {
                    save.setBackgroundColor(getResources().getColor(R.color.btnColorGrey));
                }
            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (GeneralUtils.isNetworkAvailable(getActivity())) {
                    SendEmail(DesignId, storage.loadID());
                } else {
                    GeneralUtils.displayNetworkAlert(getActivity(), false);
                }
            }
        });

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }


    void SendEmail(String DesignID, String UserID) {
        dialog.dismiss();
        progressDialog.show();
        GenericRequest<DesignEmailResponse> request = new GenericRequest<DesignEmailResponse>(Request.Method.POST, CONSTANTS.URL_DESIGN_EMAIL, DesignEmailResponse.class,
                new DesignEmailRequest(DesignID, UserID), new Response.Listener<DesignEmailResponse>() {
            @Override
            public void onResponse(DesignEmailResponse response) {
                progressDialog.dismiss();

                final Dialog notSelected = new Dialog(getActivity());
                notSelected.setCancelable(true);
                notSelected.requestWindowFeature(Window.FEATURE_NO_TITLE);
                notSelected.setContentView(R.layout.dialogue_event_saved);

                TextView messageTitle = (TextView) notSelected.findViewById(R.id.messageTitle);
                TextView messageBody = (TextView) notSelected.findViewById(R.id.messageBody);
                ImageView dialog_icon = (ImageView) notSelected.findViewById(R.id.dialog_icon);
//                messageTitle.setText(response.getResponse().get(0).getMessage());
//                messageBody.setText(response.getResponse().get(0).getMessage());
                messageTitle.setText("Email Artwork");
                messageBody.setText("Artwork has been sent to your Email ID.");

                dialog_icon.setImageResource(R.drawable.tick_inside_a_circle);
                TextView gotIt = (TextView) notSelected.findViewById(R.id.eventSaved);
                gotIt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        notSelected.dismiss();
                    }
                });
                notSelected.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                notSelected.show();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.venueFilterChangeCity:
                Intent i;
                i = new Intent(getActivity(), CitySearchActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);

                break;
            case R.id.designSave:
                designID = designAdapter.getSelected();
                if (!designID.equals("")) {
//                        new AlertDialog.Builder(getActivity())
//                                .setMessage(getString(R.string.SaveFilter))
//                                .setNegativeButton("No", null)
//                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface arg0, int arg1) {
//                                       if (GeneralUtils.isNetworkAvailable(getActivity())){
//                                          SaveFilters(storage.loadEventName(), false, false, storage.loadEventDate());
//                                       }
//                                       else {
//                                           GeneralUtils.displayNetworkAlert(getActivity(),false);
//                                       }
//                                    }
//                                }).create().show();

//                    final Dialog dialog = new Dialog(getContext());
//                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    dialog.setContentView(R.layout.dialog_save_and_proceed);
//                    TextView yes = (TextView) dialog.findViewById(R.id.yes);
//                    yes.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            if (GeneralUtils.isNetworkAvailable(getActivity())){
//                                SaveFilters(storage.loadEventName(), false, false, storage.loadEventDate());
//                            }
//                            else {
//                                GeneralUtils.displayNetworkAlert(getActivity(),false);
//                            }
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

                    SaveFilters(storage.loadEventName(), false, false, storage.loadEventDate());

                } else {
                    final Dialog notSelected = new Dialog(getActivity());
                    notSelected.setCancelable(true);
                    notSelected.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    notSelected.setContentView(R.layout.dialogue_event_saved);

                    TextView messageTitle = (TextView) notSelected.findViewById(R.id.messageTitle);
                    TextView messageBody = (TextView) notSelected.findViewById(R.id.messageBody);
                    ImageView dialog_icon = (ImageView) notSelected.findViewById(R.id.dialog_icon);
                    messageTitle.setText("Select Design");
                    messageBody.setText("Please select your design \n elements");
                    dialog_icon.setImageResource(R.drawable.forget_icon);
                    TextView gotIt = (TextView) notSelected.findViewById(R.id.eventSaved);
                    gotIt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            notSelected.dismiss();
                        }
                    });
                    notSelected.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    notSelected.show();
                }
                break;
            case R.id.designSaveExit:
                designID = designAdapter.getSelected();
                if (!designID.equals("")) {

//                        new AlertDialog.Builder(getActivity())
//                                .setMessage(getString(R.string.SaveExit))
//                                .setNegativeButton("No", null)
//                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//
//                                    public void onClick(DialogInterface arg0, int arg1) {
//                                        if (GeneralUtils.isNetworkAvailable(getActivity())){
//                                            SaveFilters(storage.loadEventName(), true, false,storage.loadEventDate());
//                                        }
//                                        else {
//                                            GeneralUtils.displayNetworkAlert(getActivity(),false);
//                                        }
//                                    }
//                                }).create().show();

                    final Dialog dialog = new Dialog(getContext());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_save_and_exit);
                    TextView yes = (TextView) dialog.findViewById(R.id.yes);
                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (GeneralUtils.isNetworkAvailable(getActivity())) {
                                SaveFilters(storage.loadEventName(), true, false, storage.loadEventDate());
                            } else {
                                GeneralUtils.displayNetworkAlert(getActivity(), false);
                            }
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


                    // }
                } else {

                    final Dialog notSelected = new Dialog(getActivity());
                    notSelected.setCancelable(true);
                    notSelected.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    notSelected.setContentView(R.layout.dialogue_event_saved);

                    TextView messageTitle = (TextView) notSelected.findViewById(R.id.messageTitle);
                    TextView messageBody = (TextView) notSelected.findViewById(R.id.messageBody);
                    ImageView dialog_icon = (ImageView) notSelected.findViewById(R.id.dialog_icon);
                    messageTitle.setText("Select Design");
                    messageBody.setText("Please select your design \n elements");
                    dialog_icon.setImageResource(R.drawable.forget_icon);
                    TextView gotIt = (TextView) notSelected.findViewById(R.id.eventSaved);
                    gotIt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            notSelected.dismiss();
                        }
                    });
                    notSelected.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    notSelected.show();
                }
                break;
        }
    }


    void SaveFilters(final String name, final boolean exit, boolean NEW, String date) {
        dialog.dismiss();

        progressDialog.show();
        GenericRequest<SaveEventResponse> request = new GenericRequest<SaveEventResponse>(Request.Method.POST, CONSTANTS.URL_SAVE_EVENT, SaveEventResponse.class,
                new SaveVenueRequest(storage.loadID(), name, "", storage.loadCityID(), storage.loadEventID(), date, storage.LoadKey(PrefEntities.HALLID), storage.LoadKey(PrefEntities.VENUEID), "0", designID, ""), new Response.Listener<SaveEventResponse>() {
            @Override
            public void onResponse(SaveEventResponse response) {
                progressDialog.dismiss();
                dialog.dismiss();
                if (Boolean.parseBoolean(response.getResponse().get(0).getStatus())) {

                    if (exit) {
                        Intent intent = new Intent(getActivity(), CitySearchActivity.class);
                        startActivity(intent);
                    } else {
                        //filterSaved.show();

                        if (GeneralUtils.isNetworkAvailable(getActivity())) {
                            GetMore(storage.loadID(), storage.loadEventID());

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
                progressDialog.dismiss();
                dialog.dismiss();
                GeneralUtils.ShowAlert(getActivity(), getString(R.string.VolleyTimeout));
            }
        });

        AppController.getInstance().addToRequestQueue(request);
        //Toast.makeText(getActivity(),designID,Toast.LENGTH_LONG).show();

        // }

    }

    void openMoreFragment() {

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.replace(R.id.venue_filter_placeholder, new MoreFragment(), CONSTANTS.FRAGMENT_MORE);
        getActivity().getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        ft.commit();
    }

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

    void GetMore(String UserID, String eventID) {
        Log.d("UserID", UserID);
        Log.d("eventID", eventID);
        progressDialog.show();
        GenericRequest<MoreResponse> request = new GenericRequest<MoreResponse>(Request.Method.POST, CONSTANTS.URL_MORE, MoreResponse.class,
                new MoreRequest(eventID, UserID), new Response.Listener<MoreResponse>() {
            @Override
            public void onResponse(MoreResponse response) {
                progressDialog.dismiss();
                if (Boolean.parseBoolean(response.getResponse().get(0).getStatus())) {
                    if (MySingleton.getInstance() == null) {
                        MySingleton.initInstance();
                    }
                    MySingleton.getInstance().setMoreResponse(response);
                    storage.storeMoreData(response);
                    storage.storeMoreDataDefault(response);

                    final Dialog setUpSuccess = new Dialog(getActivity());
                    setUpSuccess.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    setUpSuccess.setCancelable(true);
                    setUpSuccess.setContentView(R.layout.dialog_design_selected_successfuly);

                    TextView procceed = (TextView) setUpSuccess.findViewById(R.id.proceed);
                    procceed.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setUpSuccess.dismiss();
                            openMoreFragment();
                        }

                    });
                    setUpSuccess.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    setUpSuccess.show();

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

}
