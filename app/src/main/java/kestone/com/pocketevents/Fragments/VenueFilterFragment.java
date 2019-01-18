package kestone.com.pocketevents.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.icu.text.UnicodeSetSpanner;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.appyvet.rangebar.RangeBar;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.fabric.sdk.android.services.common.CommonUtils;
import it.gmariotti.recyclerview.adapter.AlphaAnimatorAdapter;
import kestone.com.pocketevents.Adapters.DialogueAdapters.FilterDialogueAdapterStyleA;
import kestone.com.pocketevents.Adapters.DialogueAdapters.FilterDialogueAdpaterStyleB;
import kestone.com.pocketevents.Adapters.VenueFilterFragment.FilterRangeAdapter;
import kestone.com.pocketevents.Adapters.VenueFilterFragment.VenueFilterAdapter;
import kestone.com.pocketevents.MODEL.Filters.RESPONSE.SavedRange;
import kestone.com.pocketevents.MODEL.Manager.FilterManager;
import kestone.com.pocketevents.MODEL.Manager.MySingleton;
import kestone.com.pocketevents.MODEL.SaveVenue.REQUEST.SaveVenueRequest;
import kestone.com.pocketevents.MODEL.SaveVenue.RESPONSE.SaveEventResponse;
import kestone.com.pocketevents.MODEL.Venue.REQUEST.VenueRequest;
import kestone.com.pocketevents.MODEL.Venue.RESPONSE.GetVenueListResponse;
import kestone.com.pocketevents.R;
import kestone.com.pocketevents.UI.AppController;
import kestone.com.pocketevents.UI.CitySearchActivity;
import kestone.com.pocketevents.UI.MoreFilterActivity;
import kestone.com.pocketevents.UI.VenueFilterActivity;
import kestone.com.pocketevents.Utilities.CONSTANTS;
import kestone.com.pocketevents.Utilities.GeneralUtils;
import kestone.com.pocketevents.Utilities.GenericRequest;
import kestone.com.pocketevents.Utilities.GridSpacingItemDecoration;
import kestone.com.pocketevents.Utilities.PrefEntities;
import kestone.com.pocketevents.Utilities.StorageUtilities;

/**
 * Created by karan on 8/2/2017.
 */

public class VenueFilterFragment extends Fragment implements View.OnClickListener, VenueFilterAdapter.VenueFilterAdapterCallBack {

    int min = 0, max = 9, minVal, maxVal;
    RecyclerView recyclerView, dialogueRecycler;
    RecyclerViewPager dialogueRecyclerPager;
    VenueFilterAdapter adapter;
    TextView search, dialogueSelect, dialogueBack, reset, changeCity, save, saveExit, firstTv, secondTv, thirdTv;
    MinTextWatcher minWatcher;
    MaxTextWatcher maxWatcher;

    EditText minEt, maxEt, etCapacity;

    LinearLayout moreFilterButton, root;

    FilterManager filterManager;
    TextView cityName, dialogueCategory, dialogueSubCategory;

    StorageUtilities storage;

    RangeSeekBar<Integer> rangeSeekBar;
    Dialog dialog;

    ProgressDialog progressDialog;

    AlphaAnimatorAdapter animatorAdapter;
    String textbefore;
    EditText enterEventName;


    TextView next, previous;
    int mYear, mMonth, mDay;
    int y1, m1, d1;
    int y2, m2, d2;
    int y3, m3, d3;
    String CurrentDate;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_filters, container, false);
        storage = new StorageUtilities(getActivity());
        updateActivityUI();
        cityName = (TextView) rootView.findViewById(R.id.venueFilter_CityText);
        cityName.setText(storage.loadCity());
        reset = (TextView) rootView.findViewById(R.id.venue_filter_fragment_btn_reset);
        changeCity = (TextView) rootView.findViewById(R.id.venueFilterChangeCity);
        save = (TextView) rootView.findViewById(R.id.venueFilterSave);
        saveExit = (TextView) rootView.findViewById(R.id.venueFilterSaveAndExit);

        changeCity.setOnClickListener(this);
        reset.setOnClickListener(this);
        save.setOnClickListener(this);
        saveExit.setOnClickListener(this);
        rangeSeekBar = new RangeSeekBar<Integer>(getActivity());
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.PleaseWait));

        filterManager = new FilterManager(getActivity());

        root = (LinearLayout) rootView.findViewById(R.id.Filters_root);
        final java.util.Calendar c = java.util.Calendar.getInstance();
        mYear = c.get(java.util.Calendar.YEAR);
        mMonth = c.get(java.util.Calendar.MONTH);
        mDay = c.get(java.util.Calendar.DAY_OF_MONTH);

        y1 = mYear;
        m1 = mMonth;
        d1 = mDay;
        CurrentDate = new SimpleDateFormat("yyyy/MM/dd").format(new Date());

        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        moreFilterButton = (LinearLayout) rootView.findViewById(R.id.venue_btn_filter);

        moreFilterButton.setOnClickListener(this);
        adapter = new VenueFilterAdapter(getActivity(), this);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.venue_recycler);
        search = (TextView) rootView.findViewById(R.id.venue_filter_fragment_btn_search);
        search.setOnClickListener(this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        animatorAdapter = new AlphaAnimatorAdapter(adapter, recyclerView);
        recyclerView.setAdapter(animatorAdapter);
        return rootView;


    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.venue_filter_fragment_btn_search:
                String filters = filterManager.getSelectedFilters();
                Log.d("filters", filters);
                if (filters.contains("FilterID 1") && filters.contains("FilterID 2") && filters.contains("FilterID 3")) {
                    if (GeneralUtils.isNetworkAvailable(getActivity())) {
                        progressDialog.show();
                        GetVenues(filters);
                    } else {
                        GeneralUtils.displayNetworkAlert(getActivity(), false);
                    }

                } else {
                    GeneralUtils.ShowAlert(getActivity(), "Hotel Category, Seating Style and Hall Capacity are required fields to search Venue.");
                }
                break;
            case R.id.venue_btn_filter:
                if (MySingleton.getInstance().getPayload2().size() > 0) {
                    i = new Intent(getActivity(), MoreFilterActivity.class);
                    startActivity(i);
                } else {
                    GeneralUtils.ShowAlert(getActivity(), "No Filters in this catogery");
                }

                break;
            case R.id.venue_filter_fragment_btn_reset:
//                new AlertDialog.Builder(getActivity())
//                        .setTitle(getString(R.string.app_name))
//                        .setMessage(getString(R.string.Reset))
//                        .setNegativeButton("No", null)
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//
//                            public void onClick(DialogInterface arg0, int arg1) {
//                                MySingleton.getInstance().resetPayload1();
//                                //storage.storePayload3(MySingleton.getInstance().getPayload2());
//                                storage.storePayload1(MySingleton.getInstance().getPayload1());
//                                //MySingleton.getInstance().resetAll();
//                                adapter.notifyDataSetChanged();
//                            }
//                        }).create().show();


                final Dialog clearAll = new Dialog(getContext());
                clearAll.requestWindowFeature(Window.FEATURE_NO_TITLE);
                clearAll.setCancelable(true);
                clearAll.setContentView(R.layout.dialog_reset_filter);
                TextView no = (TextView) clearAll.findViewById(R.id.no);
                TextView yes = (TextView) clearAll.findViewById(R.id.yes);
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MySingleton.getInstance().resetPayload1();
                        //storage.storePayload3(MySingleton.getInstance().getPayload2());
                        storage.storePayload1(MySingleton.getInstance().getPayload1());
                        //MySingleton.getInstance().resetAll();
                        adapter.notifyDataSetChanged();
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
            case R.id.venueFilterChangeCity:
                i = new Intent(getActivity(), CitySearchActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                break;

            case R.id.venueFilterSave:

                if (storage.loadEventID().equals("9999")) {

                    dialog.setContentView(R.layout.dialogue_enter_event_name);
                    dialog.setCancelable(true);
                    enterEventName = (EditText) dialog.findViewById(R.id.enterEventName);
                    final EditText enterEventDate = (EditText) dialog.findViewById(R.id.enterEventDate);

//                    enterEventName.addTextChangedListener(new TextWatcher() {
//                        @Override
//                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                            //
//                        }
//
//                        @Override
//                        public void onTextChanged(CharSequence s, int start, int before, int count) {
//                            if (s.length() > 1) {
//                                String lastChar = s.toString().substring(s.length() - 1);
//                                if (lastChar.equals(" ")) {
//                                    textbefore = enterEventName.getText().toString().trim();
//                                    String last = s.toString().substring(s.length() - 1);
//                                    if (last.equals(" ")) {
//                                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                                        builder.setTitle(getString(R.string.app_name))
//                                                .setMessage("Event name Cant't Content Space.")
//                                                .setCancelable(false)
//                                                .setPositiveButton(getString(android.R.string.ok),
//                                                        new DialogInterface.OnClickListener() {
//                                                            @Override
//                                                            public void onClick(DialogInterface dialog, int which) {
//                                                                enterEventName.setText(textbefore);
//                                                                enterEventName.setSelection(enterEventName.getText().length());
//                                                                dialog.dismiss();
//                                                            }
//                                                        });
//                                        AlertDialog dialog = builder.create();
//                                        dialog.show();
//                                    }
//                                } else {
//                                    // Toast.makeText(getActivity(), "space bar not pressed", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void afterTextChanged(Editable s) {
//
//                        }
//                    });

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
                            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                                    //   enterEventDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                                    y2 = year;
                                    m2 = monthOfYear;
                                    d2 = dayOfMonth;

                                    Log.d("SELECTEDDATE", String.valueOf(y2) + " " + String.valueOf(m2) + " " + String.valueOf(d2));
                                    Log.d("CURRENTDATE", String.valueOf(y1) + " " + String.valueOf(m1) + " " + String.valueOf(d1));
                                    enterEventDate.setText("");
                                    if (y1 <= y2) {
                                        if (m1 <= m2 || y1 < y2) {
                                            if (d1 <= d2 || m1 < m2) {

                                                enterEventDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                                            } else

                                            {
                                                GeneralUtils.ShowAlert(getActivity(), "Please Select Valid Date");
                                                enterEventDate.setText("");
                                            }

                                        } else {
                                            GeneralUtils.ShowAlert(getActivity(), "Please Select Valid Month");
                                            enterEventDate.setText("");
                                        }
                                    } else {
                                        GeneralUtils.ShowAlert(getActivity(), "Please Select Valid Year");
                                        enterEventDate.setText("");
                                    }


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
                                if (!enterEventDate.getText().toString().isEmpty()) {
                                    SaveFilters(enterEventName.getText().toString(), false, true, enterEventDate.getText().toString());
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

            case R.id.venueFilterSaveAndExit:
                if (storage.loadEventID().equals("9999")) {
                    dialog.setContentView(R.layout.dialogue_enter_event_name);
                    dialog.setCancelable(true);
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
                            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                                    y2 = year;
                                    m2 = monthOfYear;
                                    d2 = dayOfMonth;
                                    enterEventDate.setText("");
                                    if (y1 <= y2) {
                                        if (m1 <= m2 || y1 < y2) {
                                            if (d1 <= d2 || m1 < m2) {
                                                enterEventDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                                            } else {
                                                GeneralUtils.ShowAlert(getActivity(), "Please Select Valid Date");
                                                enterEventDate.setText("");
                                            }
                                        } else {
                                            GeneralUtils.ShowAlert(getActivity(), "Please Select Valid Month");
                                            enterEventDate.setText("");
                                        }
                                    } else {
                                        GeneralUtils.ShowAlert(getActivity(), "Please Select Valid Year");
                                        enterEventDate.setText("");
                                    }

                                    //  enterEventDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
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
                                if (!enterEventDate.getText().toString().isEmpty()) {
                                    SaveFilters(enterEventName.getText().toString(), true, true, enterEventDate.getText().toString());
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


    @Override
    public void onClickCallBack(final int i, int mode, int selection, String text, String text2) {
        Log.d("text", i + " Mode "+mode);
        if (mode == 0) {

            final FilterDialogueAdapterStyleA filterDialogueAdapterStyleA = new FilterDialogueAdapterStyleA(getActivity(), i, selection);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialogue_filter_style_a);
            dialogueRecycler = (RecyclerView) dialog.findViewById(R.id.dialogue_a_rv);
            dialogueSelect = (TextView) dialog.findViewById(R.id.dialogue_a_select_btn);
            dialogueBack = (TextView) dialog.findViewById(R.id.dialogue_a_back_btn);
            dialogueCategory = (TextView) dialog.findViewById(R.id.dialogue_a_venueCategory);
            dialogueSubCategory = (TextView) dialog.findViewById(R.id.dialogue_a_venueSubheading);
            dialogueSubCategory.setText(MySingleton.getInstance().getPayload1().get(i).getFilterSubHeading());
            dialogueCategory.setText(MySingleton.getInstance().getPayload1().get(i).getFilterName());
            final RecyclerView.LayoutManager lm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
            dialogueRecycler.setLayoutManager(lm);
            dialogueRecycler.setAdapter(filterDialogueAdapterStyleA);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.show();
            dialogueBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialogueSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    filterDialogueAdapterStyleA.getSelectedItems();
                    dialog.dismiss();
                    adapter.notifyDataSetChanged();
                }
            });
        } else if (mode == 1) {

            final FilterDialogueAdpaterStyleB filterDialogueAdpaterStyleB = new FilterDialogueAdpaterStyleB(getActivity(), i, selection);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialogue_filter_style_b);

            dialogueRecyclerPager = (RecyclerViewPager) dialog.findViewById(R.id.dialogue_b_rv);
            dialogueSelect = (TextView) dialog.findViewById(R.id.dialogue_b_select_btn);
            dialogueBack = (TextView) dialog.findViewById(R.id.dialogue_b_back_btn);
            next = (TextView) dialog.findViewById(R.id.next);
            previous = (TextView) dialog.findViewById(R.id.previous);

            dialogueCategory = (TextView) dialog.findViewById(R.id.dialogue_b_venueCategory);
            dialogueSubCategory = (TextView) dialog.findViewById(R.id.dialogue_b_venueSubheading);
            dialogueSubCategory.setText(MySingleton.getInstance().getPayload1().get(i).getFilterSubHeading());
            dialogueCategory.setText(MySingleton.getInstance().getPayload1().get(i).getFilterName());
            RecyclerView.LayoutManager lm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
            dialogueRecyclerPager.setLayoutManager(lm);
            dialogueRecyclerPager.setAdapter(filterDialogueAdpaterStyleB);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.show();

            if (MySingleton.getInstance().getPayload1().get(selection).getValues().size() < 1) {
                next.setVisibility(View.INVISIBLE);
                previous.setVisibility(View.INVISIBLE);
            } else {
                next.setVisibility(View.VISIBLE);
                previous.setVisibility(View.VISIBLE);
            }

            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int indexp = dialogueRecyclerPager.getCurrentPosition();
                    dialogueRecyclerPager.smoothScrollToPosition(indexp + 1);

                }
            });
            previous.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int indexm = dialogueRecyclerPager.getCurrentPosition();
                    dialogueRecyclerPager.smoothScrollToPosition(indexm - 1);
                }
            });
            dialogueBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialogueSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //filterDialogueAdpaterStyleB.getSelectedItems();
                    filterDialogueAdpaterStyleB.getSelection(dialogueRecyclerPager.getCurrentPosition());
                    dialog.dismiss();
                    adapter.notifyDataSetChanged();
                }
            });

        } else {

            if (!MySingleton.getInstance().getPayload1().get(i).getFilterName().equalsIgnoreCase("Hall Capacity")) {

                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialogue_filter_style_c);
                final RangeBar rangeBar = (RangeBar) dialog.findViewById(R.id.rangeBar);
                //rangeBar.setEnabled(false);
                final TextView range = (TextView) dialog.findViewById(R.id.rangeText);

//            final com.edmodo.rangebar.RangeBar rangebar2 = (com.edmodo.rangebar.RangeBar) dialog.findViewById(R.id.rangebar);
//           // rangebar2.setTickCount(10);
//            //rangebar2.setTickHeight(25);
//            rangebar2.setBarWeight(6);
//            rangebar2.setBarColor(getResources().getColor(R.color.colorAccent));
//            rangebar2.setConnectingLineColor(getResources().getColor(R.color.colorAccent));
//            rangebar2.setOnRangeBarChangeListener(new com.edmodo.rangebar.RangeBar.OnRangeBarChangeListener() {
//                @Override
//                public void onIndexChangeListener(com.edmodo.rangebar.RangeBar rangeBar, int i, int j) {
//                    Log.d("Value", "Min " + i + " Max " + j);
//                    if (min == i) {
//                        rangeBar.setThumbIndices(j - 1, j);
//                        min = j - 1;
//                        max = j;
//                    } else if (max == j) {
//                        rangeBar.setThumbIndices(i, i + 1);
//                        min = i;
//                        max = i + 1;
//                    }
//                }
//            });

                dialogueSelect = (TextView) dialog.findViewById(R.id.dialogue_c_select_btn);
                dialogueBack = (TextView) dialog.findViewById(R.id.dialogue_c_back_btn);
                dialogueCategory = (TextView) dialog.findViewById(R.id.dialogue_c_venueCategory);
                dialogueSubCategory = (TextView) dialog.findViewById(R.id.dialogue_c_venueSubheading);
                dialogueSubCategory.setText(MySingleton.getInstance().getPayload1().get(i).getFilterSubHeading());
                dialogueCategory.setText(MySingleton.getInstance().getPayload1().get(i).getFilterName());

                final float f1, f2;
                f1 = Float.valueOf(MySingleton.getInstance().getPayload1().get(i).getValues().get(0).getNameLabel());
                f2 = Float.valueOf(MySingleton.getInstance().getPayload1().get(i).getValues().get(1).getNameLabel());
                Log.d("f1", f1 + "");
                Log.d("f2", f2 + "");

                minEt = (EditText) dialog.findViewById(R.id.minEt);
                maxEt = (EditText) dialog.findViewById(R.id.maxEt);
                minEt.setText(Integer.valueOf(MySingleton.getInstance().getPayload1().get(i).getValues().get(0).getNameLabel()) + "");
                maxEt.setText(Integer.valueOf(MySingleton.getInstance().getPayload1().get(i).getValues().get(0).getNameLabel()) + 50 + "");

                minVal = Integer.valueOf(MySingleton.getInstance().getPayload1().get(i).getValues().get(0).getNameLabel());
                maxVal = Integer.valueOf(MySingleton.getInstance().getPayload1().get(i).getValues().get(1).getNameLabel());

                maxWatcher = new MaxTextWatcher(maxEt);
                minWatcher = new MinTextWatcher(minEt);

//            minEt.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View view, MotionEvent motionEvent) {
//                    minEt.addTextChangedListener(minWatcher);
//                    maxEt.removeTextChangedListener(maxWatcher);
//                    return false;
//                }
//            });

                minEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        if (b) {
                            maxEt.setError(null);
                            minEt.addTextChangedListener(minWatcher);
                        } else {
                            minEt.removeTextChangedListener(minWatcher);
                        }
                    }
                });

//            maxEt.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View view, MotionEvent motionEvent) {
//                    maxEt.addTextChangedListener(maxWatcher);
//                    minEt.removeTextChangedListener(minWatcher);
//                    return false;
//                }
//            });

                maxEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        if (b) {
                            minEt.setError(null);
                            maxEt.addTextChangedListener(maxWatcher);
                        } else {
                            maxEt.removeTextChangedListener(maxWatcher);
                        }
                    }
                });


                rangeBar.setTickInterval(5);
                rangeBar.setTickEnd(f2);
                rangeBar.setTickStart(f1);

                rangeBar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
                    @Override
                    public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
                        range.setText(leftPinValue + "-" + rightPinValue);
                    }
                });

                if (MySingleton.getInstance().getPayload1().get(i).getRanges() != null) {
                    rangeBar.setRangePinsByValue(Float.valueOf(MySingleton.getInstance().getPayload1().get(i).getRanges().getLowerLimit()), Float.valueOf(MySingleton.getInstance().getPayload1().get(i).getRanges().getLowerLimit()) + 50);
                    // rangeBar.setRangePinsByValue();
                    range.setText(MySingleton.getInstance().getPayload1().get(i).getRanges().getLowerLimit() + "-" + MySingleton.getInstance().getPayload1().get(i).getRanges().getUpperLimit());
                } else {
                    range.setText(MySingleton.getInstance().getPayload1().get(i).getValues().get(0).getNameLabel() + "-" + MySingleton.getInstance().getPayload1().get(i).getValues().get(1).getNameLabel());
                }

                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();
                dialogueBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialogueSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SavedRange savedRange = new SavedRange();
                        savedRange.setLowerLimit(rangeBar.getLeftPinValue());
                        savedRange.setUpperLimit(rangeBar.getRightPinValue());
                        //Toast.makeText(getApplicationContext(),String.valueOf(finalRangeSeekBar.getSelectedMinValue()) + " - " + String.valueOf(finalRangeSeekBar.getSelectedMaxValue()),Toast.LENGTH_SHORT).show();
                        //(rangeSeekBar.getSelectedMinValue() == rangeSeekBar.getAbsoluteMinValue()) && (rangeSeekBar.getSelectedMaxValue() == rangeSeekBar.getAbsoluteMaxValue())
                        int sl, su, al, au;
                        sl = Integer.parseInt(rangeBar.getLeftPinValue());
                        su = Integer.parseInt(rangeBar.getRightPinValue());
                        al = Math.round(rangeBar.getTickStart());
                        au = Math.round(rangeBar.getTickEnd());
                        if (sl == al && su == au) {
                            //Toast.makeText(getApplicationContext(),String.valueOf(rangeSeekBar.getAbsoluteMinValue()),Toast.LENGTH_SHORT).show();
                            MySingleton.getInstance().getPayload1().get(i).setSelected(true);
                        } else {
                            MySingleton.getInstance().getPayload1().get(i).setSelected(true);
                        }
                        MySingleton.getInstance().getPayload1().get(i).setRanges(savedRange);
                        dialog.dismiss();
                        storage.storePayload1(MySingleton.getInstance().getPayload1());
                        adapter.notifyDataSetChanged();
                    }
                });

            } else {

                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialogue_filter_style_d);

                firstTv = (TextView) dialog.findViewById(R.id.firstTv);
                secondTv = (TextView) dialog.findViewById(R.id.secondTv);
                thirdTv = (TextView) dialog.findViewById(R.id.thirdTv);

                etCapacity = (EditText) dialog.findViewById(R.id.etCapacity);
                dialogueSelect = (TextView) dialog.findViewById(R.id.dialogue_a_select_btn);
                dialogueBack = (TextView) dialog.findViewById(R.id.dialogue_a_back_btn);
                dialogueCategory = (TextView) dialog.findViewById(R.id.dialogue_a_venueCategory);
                dialogueSubCategory = (TextView) dialog.findViewById(R.id.dialogue_a_venueSubheading);
                dialogueSubCategory.setText(MySingleton.getInstance().getPayload1().get(i).getFilterSubHeading());
                dialogueCategory.setText(MySingleton.getInstance().getPayload1().get(i).getFilterName());
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();

                if (MySingleton.getInstance().getPayload1().get(i).getRanges() != null) {
                    int capacity = ((Integer.parseInt(MySingleton.getInstance().getPayload1().get(i).getRanges().getLowerLimit()))
                            + (Integer.parseInt(MySingleton.getInstance().getPayload1().get(i).getRanges().getUpperLimit()))) / 2;
                    if(capacity==25){
                        etCapacity.setText(50 + "");
                    }else if(capacity==75){
                        etCapacity.setText(100 + "");
                    }else if(capacity==125){
                        etCapacity.setText(150 + "");
                    }

                    dialogueSelect.setBackgroundColor(getResources().getColor(R.color.colorRedDark));

                    if (capacity <= 50) {
                        firstTv.setBackground(getResources().getDrawable(R.drawable.shape_circle_red));
                        firstTv.setTextColor(getResources().getColor(R.color.textColorWhite));
                        secondTv.setBackground(getResources().getDrawable(R.drawable.shape_circle_white));
                        secondTv.setTextColor(getResources().getColor(R.color.textColorBlack));
                        thirdTv.setBackground(getResources().getDrawable(R.drawable.shape_circle_white));
                        thirdTv.setTextColor(getResources().getColor(R.color.textColorBlack));
                    } else if (capacity <= 100) {
                        firstTv.setBackground(getResources().getDrawable(R.drawable.shape_circle_white));
                        firstTv.setTextColor(getResources().getColor(R.color.textColorBlack));
                        secondTv.setBackground(getResources().getDrawable(R.drawable.shape_circle_red));
                        secondTv.setTextColor(getResources().getColor(R.color.textColorWhite));
                        thirdTv.setBackground(getResources().getDrawable(R.drawable.shape_circle_white));
                        thirdTv.setTextColor(getResources().getColor(R.color.textColorBlack));
                    } else if (capacity > 100) {
                        firstTv.setBackground(getResources().getDrawable(R.drawable.shape_circle_white));
                        firstTv.setTextColor(getResources().getColor(R.color.textColorBlack));
                        secondTv.setBackground(getResources().getDrawable(R.drawable.shape_circle_white));
                        secondTv.setTextColor(getResources().getColor(R.color.textColorBlack));
                        thirdTv.setBackground(getResources().getDrawable(R.drawable.shape_circle_red));
                        thirdTv.setTextColor(getResources().getColor(R.color.textColorWhite));
                    }

                }

                dialogueBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                firstTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        etCapacity.setText("50");
                        firstTv.setBackground(getResources().getDrawable(R.drawable.shape_circle_red));
                        firstTv.setTextColor(getResources().getColor(R.color.textColorWhite));
                        secondTv.setBackground(getResources().getDrawable(R.drawable.shape_circle_white));
                        secondTv.setTextColor(getResources().getColor(R.color.textColorBlack));
                        thirdTv.setBackground(getResources().getDrawable(R.drawable.shape_circle_white));
                        thirdTv.setTextColor(getResources().getColor(R.color.textColorBlack));
                    }
                });

                secondTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        etCapacity.setText("100");
                        firstTv.setBackground(getResources().getDrawable(R.drawable.shape_circle_white));
                        firstTv.setTextColor(getResources().getColor(R.color.textColorBlack));
                        secondTv.setBackground(getResources().getDrawable(R.drawable.shape_circle_red));
                        secondTv.setTextColor(getResources().getColor(R.color.textColorWhite));
                        thirdTv.setBackground(getResources().getDrawable(R.drawable.shape_circle_white));
                        thirdTv.setTextColor(getResources().getColor(R.color.textColorBlack));
                    }
                });

                thirdTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        etCapacity.setText("150");
                        firstTv.setBackground(getResources().getDrawable(R.drawable.shape_circle_white));
                        firstTv.setTextColor(getResources().getColor(R.color.textColorBlack));
                        secondTv.setBackground(getResources().getDrawable(R.drawable.shape_circle_white));
                        secondTv.setTextColor(getResources().getColor(R.color.textColorBlack));
                        thirdTv.setBackground(getResources().getDrawable(R.drawable.shape_circle_red));
                        thirdTv.setTextColor(getResources().getColor(R.color.textColorWhite));
                    }
                });

                etCapacity.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        if (editable.length() > 0) {

                            if (Integer.parseInt(editable.toString()) <= 49) {
                                etCapacity.setError("Minimum hall capacity allowed is 50");
                                dialogueSelect.setBackgroundColor(getResources().getColor(R.color.selection_grey));
                            } else {
                                Log.d("Integer", Integer.parseInt(editable.toString()) + "");
                                dialogueSelect.setBackgroundColor(getResources().getColor(R.color.colorRedDark));

                                if (Integer.parseInt(editable.toString()) < 100) {
                                    firstTv.setBackground(getResources().getDrawable(R.drawable.shape_circle_red));
                                    firstTv.setTextColor(getResources().getColor(R.color.textColorWhite));
                                    secondTv.setBackground(getResources().getDrawable(R.drawable.shape_circle_white));
                                    secondTv.setTextColor(getResources().getColor(R.color.textColorBlack));
                                    thirdTv.setBackground(getResources().getDrawable(R.drawable.shape_circle_white));
                                    thirdTv.setTextColor(getResources().getColor(R.color.textColorBlack));
                                } else if (Integer.parseInt(editable.toString()) < 150) {
                                    firstTv.setBackground(getResources().getDrawable(R.drawable.shape_circle_white));
                                    firstTv.setTextColor(getResources().getColor(R.color.textColorBlack));
                                    secondTv.setBackground(getResources().getDrawable(R.drawable.shape_circle_red));
                                    secondTv.setTextColor(getResources().getColor(R.color.textColorWhite));
                                    thirdTv.setBackground(getResources().getDrawable(R.drawable.shape_circle_white));
                                    thirdTv.setTextColor(getResources().getColor(R.color.textColorBlack));
                                } else if (Integer.parseInt(editable.toString()) >= 150) {
                                    firstTv.setBackground(getResources().getDrawable(R.drawable.shape_circle_white));
                                    firstTv.setTextColor(getResources().getColor(R.color.textColorBlack));
                                    secondTv.setBackground(getResources().getDrawable(R.drawable.shape_circle_white));
                                    secondTv.setTextColor(getResources().getColor(R.color.textColorBlack));
                                    thirdTv.setBackground(getResources().getDrawable(R.drawable.shape_circle_red));
                                    thirdTv.setTextColor(getResources().getColor(R.color.textColorWhite));
                                }

                            }

                        } else {
                            dialogueSelect.setBackgroundColor(getResources().getColor(R.color.selection_grey));
                        }
                    }
                });

                dialogueSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (etCapacity.getText().toString().length() > 0) {


                            if (Integer.parseInt(etCapacity.getText().toString()) >= 50) {
                                SavedRange savedRange = new SavedRange();
                                if(Integer.parseInt(etCapacity.getText().toString()) == 50) {
//                                    savedRange.setLowerLimit((Integer.parseInt(etCapacity.getText().toString()) - 25) + "");
//                                    savedRange.setUpperLimit((Integer.parseInt(etCapacity.getText().toString()) + 25) + "");
                                    savedRange.setLowerLimit(45 + "");
                                    savedRange.setUpperLimit(50 + "");
                                }else if(Integer.parseInt(etCapacity.getText().toString()) == 100) {
                                    savedRange.setLowerLimit(51 + "");
                                    savedRange.setUpperLimit(100 + "");
                                }else if(Integer.parseInt(etCapacity.getText().toString()) == 150) {
                                    savedRange.setLowerLimit(101 + "");
                                    savedRange.setUpperLimit(150 + "");
                                }


                                String dataString = (Integer.parseInt(etCapacity.getText().toString()) - 25) + " - " +
                                        (Integer.parseInt(etCapacity.getText().toString()) + 25);

                                MySingleton.getInstance().getPayload1().get(i).setSelected(true);
                                MySingleton.getInstance().getPayload1().get(i).setRanges(savedRange);
                                dialog.dismiss();
                                storage.storePayload1(MySingleton.getInstance().getPayload1());
                                adapter.notifyDataSetChanged();

                            } else {
                                etCapacity.setError("Minimum hall capacity allowed is 50");
                            }
                        }
                    }
                });


            }

        }
//        else if (mode == 0 && text.equalsIgnoreCase("Hall Capacity")) {
//
//            Float f1 = Float.valueOf(MySingleton.getInstance().getPayload1().get(i).getValues().get(0).getNameLabel());
//            Float f2 = Float.valueOf(MySingleton.getInstance().getPayload1().get(i).getValues().get(1).getNameLabel());
//
//            List<Integer> max = new ArrayList<>();
//            List<Integer> min = new ArrayList<>();
//            int minCounter = Math.round(f1);
//            int maxCounter = Math.round(f2);
//
//            while (minCounter < maxCounter) {
//                min.add(minCounter);
//                max.add(minCounter + 50);
//                minCounter = minCounter + 50;
//            }
//
//            Log.d("min", min.toString());
//            Log.d("max", max.toString());
//
//
//            final FilterRangeAdapter filterDialogueAdapterStyleA = new FilterRangeAdapter(getActivity(), i, selection, min, max);
//            dialog.setCancelable(false);
//            dialog.setContentView(R.layout.dialogue_filter_style_a);
//            dialogueRecycler = (RecyclerView) dialog.findViewById(R.id.dialogue_a_rv);
//            dialogueSelect = (TextView) dialog.findViewById(R.id.dialogue_a_select_btn);
//            dialogueBack = (TextView) dialog.findViewById(R.id.dialogue_a_back_btn);
//            dialogueCategory = (TextView) dialog.findViewById(R.id.dialogue_a_venueCategory);
//            dialogueSubCategory = (TextView) dialog.findViewById(R.id.dialogue_a_venueSubheading);
//            dialogueSubCategory.setText(MySingleton.getInstance().getPayload1().get(i).getFilterSubHeading());
//            dialogueCategory.setText(MySingleton.getInstance().getPayload1().get(i).getFilterName());
//            final RecyclerView.LayoutManager lm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
//            dialogueRecycler.setLayoutManager(lm);
//            dialogueRecycler.setAdapter(filterDialogueAdapterStyleA);
//            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            dialog.show();
//            dialogueBack.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    dialog.dismiss();
//                }
//            });
//            dialogueSelect.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    filterDialogueAdapterStyleA.getSelectedItems();
//                    dialog.dismiss();
//                    adapter.notifyDataSetChanged();
//                }
//            });
//        }
    }

    // Our handler for received Intents. This will be called whenever an Intent
// with an action named "custom-event-name" is broadcasted.
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            // Get extra data included in the Intent
            if (intent.hasExtra("Flag")) {
                if (intent.getStringExtra("Flag").equals("0")) {
                    dialogueSelect.setBackgroundColor(getResources().getColor(R.color.selection_grey));
                } else if (intent.getStringExtra("Flag").equals("1")) {
                    dialogueSelect.setBackgroundColor(getResources().getColor(R.color.colorRedDark));
                }

            }
        }
    };

    @Override
    public void onPause() {
        // Unregister since the activity is paused.
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(
                mMessageReceiver);
        super.onPause();

    }

    @Override
    public void onResume() {
        // Register to receive messages.
        // We are registering an observer (mMessageReceiver) to receive Intents
        // with actions named "custom-event-name".
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(
                mMessageReceiver, new IntentFilter("custom-event-name"));
        super.onResume();
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    void GetVenues(String Filters) {
        Log.e("City Name ", storage.loadCityID());
        GenericRequest<GetVenueListResponse> request = new GenericRequest<GetVenueListResponse>(Request.Method.POST, CONSTANTS.URL_GET_VENUE_LIST, GetVenueListResponse.class, new VenueRequest(storage.loadCityID(), "1", "100", Filters),
                new Response.Listener<GetVenueListResponse>() {
                    @Override
                    public void onResponse(GetVenueListResponse response) {
                        progressDialog.dismiss();
                        Log.d("VENUE RESPONSEs", response.getResponse().toString());
                        if (Boolean.valueOf(response.getResponse().get(0).getStatus())) {

                            MySingleton.getInstance().setVenues(response);
                            storage.storeVenueDefault(response);

                            VenueFragment fragment = new VenueFragment();
                            android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
                            android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
                            ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
                            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                            ft.replace(R.id.venue_filter_placeholder, fragment, CONSTANTS.FRAGMENT_VENUE);
                            ft.addToBackStack(null);
                            ft.commit();
                        } else {

                            final Dialog successDialog = new Dialog(getActivity());
                            successDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            successDialog.setCancelable(true);
                            successDialog.setContentView(R.layout.dialogue_event_saved);

                            TextView messageTitle = (TextView) successDialog.findViewById(R.id.messageTitle);
                            TextView messageBody = (TextView) successDialog.findViewById(R.id.messageBody);
                            ImageView dialog_icon = (ImageView) successDialog.findViewById(R.id.dialog_icon);
                            dialog_icon.setImageResource(R.drawable.result_not_found);
                            messageTitle.setText(response.getResponse().get(0).getMessage());
//                            messageBody.setText("Something does't seems right. \n Please refine your filters and try again.");
                            messageBody.setText("Something does't seems right. \n Please change your distance from airport or " +
                                    "distance from railway station to get better result.");
                            TextView gotIt = (TextView) successDialog.findViewById(R.id.eventSaved);
                            gotIt.setText("Ok");
                            gotIt.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    successDialog.dismiss();
                                }
                            });
                            successDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            successDialog.show();
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


    void SaveFilters(final String name, final boolean exit, boolean NEW, String date) {
        dialog.dismiss();
        if (NEW) {
            if (GeneralUtils.isNetworkAvailable(getActivity())) {
                progressDialog.show();
                final GenericRequest<SaveEventResponse> request = new GenericRequest<SaveEventResponse>(Request.Method.POST, CONSTANTS.URL_SAVE_EVENT, SaveEventResponse.class, new SaveVenueRequest(storage.loadID(), name, filterManager.getSelectedFilters(), storage.loadCityID(), "0", date, "0", "0", "0", "0", ""),
                        new Response.Listener<SaveEventResponse>() {
                            @Override
                            public void onResponse(SaveEventResponse response) {
                                progressDialog.dismiss();

                                if (Boolean.parseBoolean(response.getResponse().get(0).getStatus())) {
                                    //Toast.makeText(getApplicationContext(),response.getResponse().get(0).getMesssage(),Toast.LENGTH_LONG).show();
                                    storage.storeEventName(name);
                                    storage.storeEventID(response.getResponse().get(0).getPayload().get(0).getEventId());
                                    storage.storeEventDate(response.getResponse().get(0).getPayload().get(0).getEventDate());
// Toast.makeText(getActivity(), storage.loadID(), Toast.LENGTH_LONG);
                                    final Dialog filterSaved1 = new Dialog(getActivity());
                                    filterSaved1.setCancelable(true);
                                    filterSaved1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    filterSaved1.setContentView(R.layout.dialogue_event_saved);

                                    TextView gotIt = (TextView) filterSaved1.findViewById(R.id.eventSaved);
                                    gotIt.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            filterSaved1.dismiss();
                                        }
                                    });
                                    filterSaved1.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    filterSaved1.show();
                                    if (exit) {
                                        Intent intent = new Intent(getActivity(), CitySearchActivity.class);
                                        startActivity(intent);
                                    }
                                } else {

                                    GeneralUtils.CustomDialog(getActivity(), "", response.getResponse().get(0).getMesssage());
                                }


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        progressDialog.dismiss();
                        Snackbar snackbar = Snackbar.make(root, "Sorry ! Filter Selections Not Save", Snackbar.LENGTH_LONG);
                        snackbar.show();

                    }
                });

                AppController.getInstance().addToRequestQueue(request);
            } else {
                GeneralUtils.displayNetworkAlert(getActivity(), false);
            }

        } else {

            if (GeneralUtils.isNetworkAvailable(getActivity())) {
                progressDialog.show();

                GenericRequest<SaveEventResponse> request = new GenericRequest<SaveEventResponse>(Request.Method.POST, CONSTANTS.URL_SAVE_EVENT, SaveEventResponse.class, new SaveVenueRequest(storage.loadID(), name, filterManager.getSelectedFilters(), storage.loadCityID(), storage.loadEventID(), date, "0", "0", "0", "0", ""), new Response.Listener<SaveEventResponse>() {
                    @Override
                    public void onResponse(SaveEventResponse response) {
                        progressDialog.dismiss();
                        dialog.dismiss();
                        if (Boolean.valueOf(response.getResponse().get(0).getStatus())) {
                            //Toast.makeText(getApplicationContext(),response.getResponse().get(0).getMesssage(),Toast.LENGTH_LONG).show();
                            storage.storeEventName(name);
                            storage.storeEventID(response.getResponse().get(0).getPayload().get(0).getEventId());
                            storage.storeEventDate(response.getResponse().get(0).getPayload().get(0).getEventDate());
                            // filterSaved.show();

                            if (exit) {
                                Intent intent = new Intent(getActivity(), CitySearchActivity.class);
                                startActivity(intent);
                            } else {
                                final Dialog filterSaved2 = new Dialog(getActivity());
                                filterSaved2.setCancelable(true);
                                filterSaved2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                filterSaved2.setContentView(R.layout.dialogue_event_saved);

                                TextView gotIt = (TextView) filterSaved2.findViewById(R.id.eventSaved);
                                gotIt.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        filterSaved2.dismiss();
                                    }
                                });
                                filterSaved2.show();
                                filterSaved2.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                Snackbar snackbar = Snackbar.make(root, response.getResponse().get(0).getMesssage(), Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        GeneralUtils.ShowAlert(getActivity(), "Sorry ! Filter Selections Not Saved");

                    }
                });

                AppController.getInstance().addToRequestQueue(request);
            } else {
                GeneralUtils.displayNetworkAlert(getActivity(), false);
            }


        }

    }


    void updateActivityUI() {
        Intent intent = new Intent("updateUI");
        intent.putExtra("frag", 1);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
    }

    private class MinTextWatcher implements TextWatcher {

        private View view;

        private MinTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            //String text = editable.toString();
            if (view.getId() == R.id.maxEt) {
                if (editable.length() > 0) {
                    if (Integer.valueOf(editable.toString()) > maxVal) {
                        maxEt.setError("Maximum allowed value is " + maxVal);
                    } else if (Integer.valueOf(editable.toString()) < minVal + 50) {
                        maxEt.setError("Minimum allowed value is " + minVal + 50);
                    } else {
                        minEt.setText(Integer.valueOf(editable.toString()) - 50 + "");
                    }
                }

            } else if (view.getId() == R.id.minEt) {

                if (editable.length() > 0) {
                    if (Integer.valueOf(editable.toString()) > maxVal - 50) {
                        minEt.setError("Maximum allowed value is " + (maxVal - 50));
                    } else if (Integer.valueOf(editable.toString()) < minVal) {
                        minEt.setError("Minimun allowed value is " + minVal);
                    } else {
                        maxEt.setText(Integer.valueOf(editable.toString()) + 50 + "");

                    }
                }

            }
        }
    }

    private class MaxTextWatcher implements TextWatcher {

        private View view;

        private MaxTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            //String text = editable.toString();
            if (view.getId() == R.id.maxEt) {
                if (editable.length() > 0) {
                    if (Integer.valueOf(editable.toString()) > maxVal) {
                        maxEt.setError("Maximum allowed value is " + maxVal);
                    } else if (Integer.valueOf(editable.toString()) < minVal + 50) {
                        maxEt.setError("Minimum allowed value is " + minVal + 50);
                    } else {
                        minEt.setText(Integer.valueOf(editable.toString()) - 50 + "");
                    }
                }

            } else if (view.getId() == R.id.minEt) {

                if (editable.length() > 0) {
                    if (Integer.valueOf(editable.toString()) > maxVal - 50) {
                        int all = maxVal - 50;
                        minEt.setError("Maximum allowed value is " + all);
                    } else if (Integer.valueOf(editable.toString()) < minVal) {
                        minEt.setError("Minimun allowed value is " + minVal);
                    } else {
                        maxEt.setText(Integer.valueOf(editable.toString()) + 50 + "");

                    }
                }

            }
        }
    }

}


