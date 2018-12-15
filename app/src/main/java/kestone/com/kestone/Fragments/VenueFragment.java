package kestone.com.kestone.Fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.appyvet.rangebar.RangeBar;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.gmariotti.recyclerview.adapter.AlphaAnimatorAdapter;
import kestone.com.kestone.Adapters.DialogueAdapters.FilterDialogueAdapterStyleA;
import kestone.com.kestone.Adapters.DialogueAdapters.FilterDialogueAdpaterStyleB;
import kestone.com.kestone.Adapters.DialogueAdapters.PhoneNumbersAdapter;
import kestone.com.kestone.Adapters.DialogueAdapters.SelectHallCapacityAdapter;
import kestone.com.kestone.Adapters.DialogueAdapters.SelectHallImageAdapter;
import kestone.com.kestone.Adapters.DialogueAdapters.SelectHallSizeAdapter;
import kestone.com.kestone.Adapters.VenueFragment.VenueCardAdapter;
import kestone.com.kestone.Adapters.VenueFragment.VenueCardInnerHallAdapter;
import kestone.com.kestone.Adapters.VenueFragment.VenueImagesAdapter;
import kestone.com.kestone.Adapters.VenueFragment.VenueSmallFilterAdapter;
import kestone.com.kestone.MODEL.Filters.RESPONSE.SavedRange;
import kestone.com.kestone.MODEL.FlorePlanMail.REQUEST.FlorePlanMailRequest;
import kestone.com.kestone.MODEL.FlorePlanMail.RESPONSE.FlorePlanMailResponse;
import kestone.com.kestone.MODEL.Manager.FilterManager;
import kestone.com.kestone.MODEL.Manager.MySingleton;
import kestone.com.kestone.MODEL.Venue.REQUEST.VenueRequest;
import kestone.com.kestone.MODEL.Venue.RESPONSE.GetVenueListResponse;
import kestone.com.kestone.MODEL.Venue.RESPONSE.Halls;
import kestone.com.kestone.MODEL.Venue.RESPONSE.Payload;
import kestone.com.kestone.R;
import kestone.com.kestone.UI.AppController;
import kestone.com.kestone.UI.CitySearchActivity;
import kestone.com.kestone.UI.CompareActivity;
import kestone.com.kestone.UI.MoreFilterActivity;
import kestone.com.kestone.Utilities.CONSTANTS;
import kestone.com.kestone.Utilities.GeneralUtils;
import kestone.com.kestone.Utilities.GenericRequest;
import kestone.com.kestone.Utilities.StorageUtilities;

/**
 * Created by karan on 8/2/2017.
 */

public class VenueFragment extends Fragment implements VenueSmallFilterAdapter.VenueSmallFilterAdapterCallBack, View.OnClickListener, VenueCardAdapter.VenueCardAdapterCallBack,
        VenueCardInnerHallAdapter.InnerHallAdapterCallBack {
    RecyclerView recyclerViewSmall;
    RecyclerViewPager recyclerViewLarge;
    VenueSmallFilterAdapter venueSmallFilterAdapter;
    VenueCardAdapter venueCardAdapter;

    RecyclerView dialogueRecycler;
    RecyclerViewPager dialogueRecyclerPager;
    RecyclerView dialogueViewPager;

    Dialog dialog;

    RangeSeekBar<Integer> rangeSeekBar;

    TextView dialogueCategory, dialogueSubCategory, firstTv, secondTv, thirdTv;

    TextView cityName;

    StorageUtilities storage;

    TextView dialogueSelect, dialogueBack, cityChange, addToCompare1, addToCompare2, addToCompare3, compare;

    GetVenueListResponse venues;

    ProgressDialog progressDialog;

    RelativeLayout root;

    FilterManager filterManager;

    LinearLayoutManager layout;

    RelativeLayout textLayout;
    TextView label, currentVenueIndex, btnSelect;
    AlphaAnimatorAdapter alphaAnimatorAdapter;
    int index;
    int hallIndex;
    TextView next, previous;
    int venuePage = 1;
    boolean isBannerNext = true;
    EditText etCapacity;
    // Our handler for received Intents. This will be called whenever an Intent
    // with an action named "custom-event-name" is broadcasted.
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            // Get extra data included in the Intent
            if (intent.hasExtra( "Flag" )) {
                if (intent.getStringExtra( "Flag" ).equals( "0" )) {
                    dialogueSelect.setBackgroundColor( getResources().getColor( R.color.selection_grey ) );
                } else if (intent.getStringExtra( "Flag" ).equals( "1" )) {
                    dialogueSelect.setBackgroundColor( getResources().getColor( R.color.colorRedDark ) );
                }
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate( R.layout.fragment_venue, container, false );
        storage = new StorageUtilities( getActivity() );
        dialog = new Dialog( getActivity() );
        updateActivityUI();
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        progressDialog = new ProgressDialog( getActivity() );
        progressDialog.setCancelable( false );
        progressDialog.setMessage( "Fetching Floor Plan" );
        addToCompare1 = (TextView) rootView.findViewById( R.id.venue_btn_addtoCompare1 );
        addToCompare2 = (TextView) rootView.findViewById( R.id.venue_btn_addtoCompare2 );
        addToCompare3 = (TextView) rootView.findViewById( R.id.venue_btn_addtoCompare3 );
        currentVenueIndex = (TextView) rootView.findViewById( R.id.venue_showingText );
        compare = (TextView) rootView.findViewById( R.id.venue_btn_compare );
        textLayout = (RelativeLayout) rootView.findViewById( R.id.textLayout );
        label = (TextView) rootView.findViewById( R.id.textviewLabel );
        textLayout.setVisibility( View.GONE );
        btnSelect = (TextView) rootView.findViewById( R.id.venue_card_btn_select );
        btnSelect.setOnClickListener( this );

        addToCompare1.setOnClickListener( this );
        addToCompare2.setOnClickListener( this );
        addToCompare3.setOnClickListener( this );
        compare.setOnClickListener( this );


        MySingleton.getInstance().resetCompare();
        MySingleton.getInstance().setVenues( storage.loadVenueDefault() );

        root = (RelativeLayout) rootView.findViewById( R.id.venue_root );

        filterManager = new FilterManager( getActivity() );


        venueSmallFilterAdapter = new VenueSmallFilterAdapter( getActivity(), this );
        recyclerViewSmall = (RecyclerView) rootView.findViewById( R.id.venueMain_rv1 );
        recyclerViewLarge = (RecyclerViewPager) rootView.findViewById( R.id.venueMain_rv2 );
        cityName = (TextView) rootView.findViewById( R.id.venueCityName );

        cityChange = (TextView) rootView.findViewById( R.id.venue_citychange );
        cityChange.setOnClickListener( this );

        cityName.setText( storage.loadCity() );

        venueCardAdapter = new VenueCardAdapter( getActivity(), this, this, btnSelect );


        RecyclerView.LayoutManager layoutManager = new GridLayoutManager( getActivity(), 1, GridLayoutManager.HORIZONTAL, false );
        recyclerViewSmall.setLayoutManager( layoutManager );
        recyclerViewSmall.setItemAnimator( new DefaultItemAnimator() );
        recyclerViewSmall.setAdapter( venueSmallFilterAdapter );

        layout = new LinearLayoutManager( getActivity(), LinearLayoutManager.HORIZONTAL, false );
        recyclerViewLarge.setLayoutManager( layout );
        recyclerViewLarge.setHasFixedSize( true );
        //venueCardAdapter.setHasStableIds(true);
        alphaAnimatorAdapter = new AlphaAnimatorAdapter( venueCardAdapter, recyclerViewLarge );

        recyclerViewLarge.setAdapter( alphaAnimatorAdapter );
        recyclerViewLarge.scrollToPosition( MySingleton.getInstance().getSelectedHallVenuePosition() );


        recyclerViewLarge.addOnScrollListener( new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged( recyclerView, newState );
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        index = layout.findFirstCompletelyVisibleItemPosition() + 1;
                        currentVenueIndex.setText( "Showing \n" + index + " of " + MySingleton.getInstance().getVenues().getResponse().get( 0 ).getPayload().size() + " Results" );

                        LinearLayoutManager llManager = (LinearLayoutManager) recyclerViewLarge.getLayoutManager();
                        if (llManager.findLastCompletelyVisibleItemPosition() == (MySingleton.getInstance().getVenues().getResponse().get( 0 ).getPayload().size() - 3) ||
                                llManager.findLastCompletelyVisibleItemPosition() == (MySingleton.getInstance().getVenues().getResponse().get( 0 ).getPayload().size() - 1)) {
                            if (isBannerNext) {
                                isBannerNext = false;
                                venuePage++;
//                                GetVenuesPaging( String.valueOf( venuePage ) );
                                break;
                            }
                        }
                }
            }
        } );

        if (getArguments() != null) {
            layout.scrollToPosition( getArguments().getInt( "compare" ) );
        }

        storage.ClearHallID();
        storage.ClearVenueID();
        index = layout.findFirstVisibleItemPosition() + 2;
        currentVenueIndex.setText( "Showing \n" + index + " of " + MySingleton.getInstance().getVenues().getResponse().get( 0 ).getPayload().size() + " Results" );
        return rootView;
    }

    @Override
    public void onClickCallBack(final int i, int mode, int selection) {
        Log.e("Mode ", ""+mode);
        if (mode == 0) {
            final FilterDialogueAdapterStyleA filterDialogueAdapterStyleA = new FilterDialogueAdapterStyleA( getActivity(), i, selection );
            dialog.setCancelable( false );
            dialog.setContentView( R.layout.dialogue_filter_style_a );
            dialogueRecycler = (RecyclerView) dialog.findViewById( R.id.dialogue_a_rv );
            dialogueSelect = (TextView) dialog.findViewById( R.id.dialogue_a_select_btn );
            dialogueBack = (TextView) dialog.findViewById( R.id.dialogue_a_back_btn );
            dialogueCategory = (TextView) dialog.findViewById( R.id.dialogue_a_venueCategory );
            dialogueSubCategory = (TextView) dialog.findViewById( R.id.dialogue_a_venueSubheading );
            dialogueSubCategory.setText( MySingleton.getInstance().getPayload1().get( i ).getFilterSubHeading() );
            dialogueCategory.setText( MySingleton.getInstance().getPayload1().get( i ).getFilterName() );
            final RecyclerView.LayoutManager lm = new GridLayoutManager( getActivity(), 1, GridLayoutManager.HORIZONTAL, false );
            dialogueRecycler.setLayoutManager( lm );
            dialogueRecycler.setAdapter( filterDialogueAdapterStyleA );
            dialog.getWindow().setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );
            dialog.show();
            dialogueBack.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            } );
            dialogueSelect.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    filterDialogueAdapterStyleA.getSelectedItems();
                    dialog.dismiss();
                    progressDialog.show();
                    GetVenues();
                    venueSmallFilterAdapter.notifyDataSetChanged();
                }
            } );
        } else if (mode == 1) {
            final FilterDialogueAdpaterStyleB filterDialogueAdpaterStyleB = new FilterDialogueAdpaterStyleB( getActivity(), i, selection );
            dialog.setCancelable( false );
            dialog.setContentView( R.layout.dialogue_filter_style_b );
            dialogueRecyclerPager = (RecyclerViewPager) dialog.findViewById( R.id.dialogue_b_rv );
            dialogueSelect = (TextView) dialog.findViewById( R.id.dialogue_b_select_btn );
            dialogueBack = (TextView) dialog.findViewById( R.id.dialogue_b_back_btn );
            dialogueCategory = (TextView) dialog.findViewById( R.id.dialogue_b_venueCategory );

            dialogueSubCategory = (TextView) dialog.findViewById( R.id.dialogue_b_venueSubheading );
            next = (TextView) dialog.findViewById( R.id.next );
            previous = (TextView) dialog.findViewById( R.id.previous );
            dialogueSubCategory.setText( MySingleton.getInstance().getPayload1().get( i ).getFilterSubHeading() );
            dialogueCategory.setText( MySingleton.getInstance().getPayload1().get( i ).getFilterName() );

            final RecyclerView.LayoutManager lm = new GridLayoutManager( getActivity(), 1, GridLayoutManager.HORIZONTAL, false );
            dialogueRecyclerPager.setLayoutManager( lm );
            dialogueRecyclerPager.setAdapter( filterDialogueAdpaterStyleB );
            dialog.getWindow().setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );
            dialog.show();

            if (MySingleton.getInstance().getPayload1().get( selection ).getValues().size() > 1) {
                next.setVisibility( View.INVISIBLE );
                previous.setVisibility( View.INVISIBLE );
            } else {
                next.setVisibility( View.VISIBLE );
                previous.setVisibility( View.VISIBLE );
            }
            next.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int indexp = dialogueRecyclerPager.getCurrentPosition();
                    dialogueRecyclerPager.smoothScrollToPosition( indexp + 1 );

                }
            } );
            previous.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int indexm = dialogueRecyclerPager.getCurrentPosition();
                    dialogueRecyclerPager.smoothScrollToPosition( indexm - 1 );
                }
            } );
            dialogueBack.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            } );
            dialogueSelect.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // filterDialogueAdpaterStyleB.getSelectedItems();
                    filterDialogueAdpaterStyleB.getSelection( dialogueRecyclerPager.getCurrentPosition() );
                    dialog.dismiss();
                    progressDialog.show();
                    GetVenues();
                    venueSmallFilterAdapter.notifyDataSetChanged();
                }
            } );

        } else {

            if (!MySingleton.getInstance().getPayload1().get( i ).getFilterName().equalsIgnoreCase( "Hall Capacity" )) {

                dialog.setCancelable( false );
                dialog.setContentView( R.layout.dialogue_filter_style_c );
                final RangeBar rangeBar = (RangeBar) dialog.findViewById( R.id.rangeBar );

                final TextView range = (TextView) dialog.findViewById( R.id.rangeText );

                dialogueSelect = (TextView) dialog.findViewById( R.id.dialogue_c_select_btn );
                dialogueBack = (TextView) dialog.findViewById( R.id.dialogue_c_back_btn );
                dialogueCategory = (TextView) dialog.findViewById( R.id.dialogue_c_venueCategory );
                dialogueSubCategory = (TextView) dialog.findViewById( R.id.dialogue_c_venueSubheading );
                dialogueSubCategory.setText( MySingleton.getInstance().getPayload1().get( i ).getFilterSubHeading() );
                dialogueCategory.setText( MySingleton.getInstance().getPayload1().get( i ).getFilterName() );

                float f1, f2;
                f1 = Float.valueOf( MySingleton.getInstance().getPayload1().get( i ).getValues().get( 0 ).getNameLabel() );
                f2 = Float.valueOf( MySingleton.getInstance().getPayload1().get( i ).getValues().get( 1 ).getNameLabel() );
                rangeBar.setTickInterval( 5 );
                rangeBar.setTickEnd( f2 );
                rangeBar.setTickStart( f1 );
                rangeBar.setOnRangeBarChangeListener( new RangeBar.OnRangeBarChangeListener() {
                    @Override
                    public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
                        range.setText( leftPinValue + "-" + rightPinValue );
                    }
                } );


                if (MySingleton.getInstance().getPayload1().get( i ).getRanges() != null) {
                    rangeBar.setRangePinsByValue( Float.valueOf( MySingleton.getInstance().getPayload1().get( i ).getRanges().getLowerLimit() ), Float.valueOf( MySingleton.getInstance().getPayload1().get( i ).getRanges().getUpperLimit() ) );
                    range.setText( MySingleton.getInstance().getPayload1().get( i ).getRanges().getLowerLimit() + "-" + MySingleton.getInstance().getPayload1().get( i ).getRanges().getUpperLimit() );
                } else {
                    range.setText( MySingleton.getInstance().getPayload1().get( i ).getValues().get( 0 ).getNameLabel() + "-" + MySingleton.getInstance().getPayload1().get( i ).getValues().get( 1 ).getNameLabel() );
                }


                dialog.getWindow().setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );
                dialog.show();
                dialogueBack.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                } );
                dialogueSelect.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SavedRange savedRange = new SavedRange();
                        savedRange.setLowerLimit( rangeBar.getLeftPinValue() );
                        savedRange.setUpperLimit( rangeBar.getRightPinValue() );
                        //Toast.makeText(getApplicationContext(),String.valueOf(finalRangeSeekBar.getSelectedMinValue()) + " - " + String.valueOf(finalRangeSeekBar.getSelectedMaxValue()),Toast.LENGTH_SHORT).show();
                        //(rangeSeekBar.getSelectedMinValue() == rangeSeekBar.getAbsoluteMinValue()) && (rangeSeekBar.getSelectedMaxValue() == rangeSeekBar.getAbsoluteMaxValue())
                        int sl, su, al, au;
                        sl = Integer.parseInt( rangeBar.getLeftPinValue() );
                        su = Integer.parseInt( rangeBar.getRightPinValue() );
                        al = Math.round( rangeBar.getTickStart() );
                        au = Math.round( rangeBar.getTickEnd() );
                        if (sl == al && su == au) {
                            //Toast.makeText(getApplicationContext(),String.valueOf(rangeSeekBar.getAbsoluteMinValue()),Toast.LENGTH_SHORT).show();
                            MySingleton.getInstance().getPayload1().get( i ).setSelected( true );
                        } else {
                            MySingleton.getInstance().getPayload1().get( i ).setSelected( true );
                        }
                        MySingleton.getInstance().getPayload1().get( i ).setRanges( savedRange );
                        dialog.dismiss();
                        storage.storePayload1( MySingleton.getInstance().getPayload1() );
                        progressDialog.show();
                        GetVenues();
                        venueSmallFilterAdapter.notifyDataSetChanged();
                    }
                } );
            } else {

                dialog.setCancelable( false );
                dialog.setContentView( R.layout.dialogue_filter_style_d );

                firstTv = (TextView) dialog.findViewById( R.id.firstTv );
                secondTv = (TextView) dialog.findViewById( R.id.secondTv );
                thirdTv = (TextView) dialog.findViewById( R.id.thirdTv );

                etCapacity = (EditText) dialog.findViewById( R.id.etCapacity );
                dialogueSelect = (TextView) dialog.findViewById( R.id.dialogue_a_select_btn );
                dialogueBack = (TextView) dialog.findViewById( R.id.dialogue_a_back_btn );
                dialogueCategory = (TextView) dialog.findViewById( R.id.dialogue_a_venueCategory );
                dialogueSubCategory = (TextView) dialog.findViewById( R.id.dialogue_a_venueSubheading );
                dialogueSubCategory.setText( MySingleton.getInstance().getPayload1().get( i ).getFilterSubHeading() );
                dialogueCategory.setText( MySingleton.getInstance().getPayload1().get( i ).getFilterName() );
                dialog.getWindow().setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );
                dialog.show();

                if (MySingleton.getInstance().getPayload1().get( i ).getRanges() != null) {
                    int capacity = ((Integer.parseInt( MySingleton.getInstance().getPayload1().get( i ).getRanges().getLowerLimit() ))
                            + (Integer.parseInt( MySingleton.getInstance().getPayload1().get( i ).getRanges().getUpperLimit() ))) / 2;
                    if(capacity==25) {
//                        etCapacity.setText( capacity + "" );
                        etCapacity.setText( 50 + "" );
                    }else if(capacity==75) {
                        etCapacity.setText( 100 + "" );
                    }else if(capacity==125) {
                        etCapacity.setText( 150 + "" );
                    }
                    dialogueSelect.setBackgroundColor( getResources().getColor( R.color.colorRedDark ) );

                    if (capacity <= 50) {
                        firstTv.setBackground( getResources().getDrawable( R.drawable.shape_circle_red ) );
                        firstTv.setTextColor( getResources().getColor( R.color.textColorWhite ) );
                        secondTv.setBackground( getResources().getDrawable( R.drawable.shape_circle_white ) );
                        secondTv.setTextColor( getResources().getColor( R.color.textColorBlack ) );
                        thirdTv.setBackground( getResources().getDrawable( R.drawable.shape_circle_white ) );
                        thirdTv.setTextColor( getResources().getColor( R.color.textColorBlack ) );
                    } else if (capacity <= 100) {
                        firstTv.setBackground( getResources().getDrawable( R.drawable.shape_circle_white ) );
                        firstTv.setTextColor( getResources().getColor( R.color.textColorBlack ) );
                        secondTv.setBackground( getResources().getDrawable( R.drawable.shape_circle_red ) );
                        secondTv.setTextColor( getResources().getColor( R.color.textColorWhite ) );
                        thirdTv.setBackground( getResources().getDrawable( R.drawable.shape_circle_white ) );
                        thirdTv.setTextColor( getResources().getColor( R.color.textColorBlack ) );
                    } else if (capacity > 100) {
                        firstTv.setBackground( getResources().getDrawable( R.drawable.shape_circle_white ) );
                        firstTv.setTextColor( getResources().getColor( R.color.textColorBlack ) );
                        secondTv.setBackground( getResources().getDrawable( R.drawable.shape_circle_white ) );
                        secondTv.setTextColor( getResources().getColor( R.color.textColorBlack ) );
                        thirdTv.setBackground( getResources().getDrawable( R.drawable.shape_circle_red ) );
                        thirdTv.setTextColor( getResources().getColor( R.color.textColorWhite ) );
                    }

                }

                dialogueBack.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                } );

                firstTv.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        etCapacity.setText( "50" );
                        firstTv.setBackground( getResources().getDrawable( R.drawable.shape_circle_red ) );
                        firstTv.setTextColor( getResources().getColor( R.color.textColorWhite ) );
                        secondTv.setBackground( getResources().getDrawable( R.drawable.shape_circle_white ) );
                        secondTv.setTextColor( getResources().getColor( R.color.textColorBlack ) );
                        thirdTv.setBackground( getResources().getDrawable( R.drawable.shape_circle_white ) );
                        thirdTv.setTextColor( getResources().getColor( R.color.textColorBlack ) );
                    }
                } );

                secondTv.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        etCapacity.setText( "100" );
                        firstTv.setBackground( getResources().getDrawable( R.drawable.shape_circle_white ) );
                        firstTv.setTextColor( getResources().getColor( R.color.textColorBlack ) );
                        secondTv.setBackground( getResources().getDrawable( R.drawable.shape_circle_red ) );
                        secondTv.setTextColor( getResources().getColor( R.color.textColorWhite ) );
                        thirdTv.setBackground( getResources().getDrawable( R.drawable.shape_circle_white ) );
                        thirdTv.setTextColor( getResources().getColor( R.color.textColorBlack ) );
                    }
                } );

                thirdTv.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        etCapacity.setText( "150" );
                        firstTv.setBackground( getResources().getDrawable( R.drawable.shape_circle_white ) );
                        firstTv.setTextColor( getResources().getColor( R.color.textColorBlack ) );
                        secondTv.setBackground( getResources().getDrawable( R.drawable.shape_circle_white ) );
                        secondTv.setTextColor( getResources().getColor( R.color.textColorBlack ) );
                        thirdTv.setBackground( getResources().getDrawable( R.drawable.shape_circle_red ) );
                        thirdTv.setTextColor( getResources().getColor( R.color.textColorWhite ) );
                    }
                } );

                etCapacity.addTextChangedListener( new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        if (editable.length() > 0) {

                            if (Integer.parseInt( editable.toString() ) <= 49) {
                                etCapacity.setError( "Minimum hall capacity allowed is 50" );
                                dialogueSelect.setBackgroundColor( getResources().getColor( R.color.selection_grey ) );
                            } else {
                                Log.d( "Integer", Integer.parseInt( editable.toString() ) + "" );
                                dialogueSelect.setBackgroundColor( getResources().getColor( R.color.colorRedDark ) );

                                if (Integer.parseInt( editable.toString() ) <= 50) {
                                    firstTv.setBackground( getResources().getDrawable( R.drawable.shape_circle_red ) );
                                    firstTv.setTextColor( getResources().getColor( R.color.textColorWhite ) );
                                    secondTv.setBackground( getResources().getDrawable( R.drawable.shape_circle_white ) );
                                    secondTv.setTextColor( getResources().getColor( R.color.textColorBlack ) );
                                    thirdTv.setBackground( getResources().getDrawable( R.drawable.shape_circle_white ) );
                                    thirdTv.setTextColor( getResources().getColor( R.color.textColorBlack ) );
                                } else if (Integer.parseInt( editable.toString() ) <= 100) {
                                    firstTv.setBackground( getResources().getDrawable( R.drawable.shape_circle_white ) );
                                    firstTv.setTextColor( getResources().getColor( R.color.textColorBlack ) );
                                    secondTv.setBackground( getResources().getDrawable( R.drawable.shape_circle_red ) );
                                    secondTv.setTextColor( getResources().getColor( R.color.textColorWhite ) );
                                    thirdTv.setBackground( getResources().getDrawable( R.drawable.shape_circle_white ) );
                                    thirdTv.setTextColor( getResources().getColor( R.color.textColorBlack ) );
                                } else if (Integer.parseInt( editable.toString() ) > 100) {
                                    firstTv.setBackground( getResources().getDrawable( R.drawable.shape_circle_white ) );
                                    firstTv.setTextColor( getResources().getColor( R.color.textColorBlack ) );
                                    secondTv.setBackground( getResources().getDrawable( R.drawable.shape_circle_white ) );
                                    secondTv.setTextColor( getResources().getColor( R.color.textColorBlack ) );
                                    thirdTv.setBackground( getResources().getDrawable( R.drawable.shape_circle_red ) );
                                    thirdTv.setTextColor( getResources().getColor( R.color.textColorWhite ) );
                                }


                            }

                        } else {
                            dialogueSelect.setBackgroundColor( getResources().getColor( R.color.selection_grey ) );
                        }
                    }
                } );

                dialogueSelect.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (etCapacity.getText().toString().length() > 0) {

//                            if (Integer.parseInt(etCapacity.getText().toString()) >= 50) {

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
//                            savedRange.setLowerLimit( (Integer.parseInt( etCapacity.getText().toString() ) - 25) + "" );
//                            savedRange.setUpperLimit( (Integer.parseInt( etCapacity.getText().toString() ) + 25) + "" );

                            String dataString = (Integer.parseInt( etCapacity.getText().toString() ) - 25) + " - " +
                                    (Integer.parseInt( etCapacity.getText().toString() ) + 25);

                            MySingleton.getInstance().getPayload1().get( i ).setSelected( true );
                            MySingleton.getInstance().getPayload1().get( i ).setRanges( savedRange );
                            dialog.dismiss();
                            storage.storePayload1( MySingleton.getInstance().getPayload1() );
                            GetVenues();
                            venueSmallFilterAdapter.notifyDataSetChanged();

//                            } else {
//                                etCapacity.setError("Minimum hall capacity allowed is 50");
//                            }
                        }
                    }
                } );


            }


        }
    }

    @Override
    public void onPause() {
        // Unregister since the activity is paused.
        LocalBroadcastManager.getInstance( getContext() ).unregisterReceiver(
                mMessageReceiver );
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();
        venueSmallFilterAdapter.notifyDataSetChanged();
        alphaAnimatorAdapter.notifyDataSetChanged();
//        MySingleton.getInstance().resetCompare();
//        MySingleton.getInstance().setVenues(storage.loadVenueDefault());

        // Register to receive messages.
        // We are registering an observer (mMessageReceiver) to receive Intents
        // with actions named "custom-event-name".
        LocalBroadcastManager.getInstance( getContext() ).registerReceiver(
                mMessageReceiver, new IntentFilter( "custom-event-name" ) );
    }

    @Override
    public void moreFilterCallback() {
        Intent intent = new Intent( getActivity(), MoreFilterActivity.class );
        intent.putExtra( "activitySource", 1 );
        startActivity( intent );
    }


    @Override
    public void onClick(View view) {
        Intent i;
        int size;
        List<Halls> list = new ArrayList<>();
        switch (view.getId()) {
            case R.id.venue_citychange:
                i = new Intent( getActivity(), CitySearchActivity.class );
                i.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                startActivity( i );
                break;
            case R.id.venue_btn_addtoCompare1:

                if (!MySingleton.getInstance().isC1Empty()) {
                    size = MySingleton.getInstance().getCompare().get( 0 ).getHalls().size();
                    list = MySingleton.getInstance().getCompare().get( 0 ).getHalls();

                    if (addToCompare1.getText().equals( list.get( MySingleton.getInstance().getCompareHallIndex1() ).getHallName() )) {
                        MySingleton.getInstance().getVenues().getResponse().get( 0 ).getPayload().get( MySingleton.getInstance().getCompareIndex1() ).setAddedToCompare( false );
                        MySingleton.getInstance().getVenues().getResponse().get( 0 ).getPayload().get( MySingleton.getInstance().getCompareIndex1() ).getHalls().get( MySingleton.getInstance().getCompareHallIndex1() ).setCompare( false );
                        alphaAnimatorAdapter.notifyItemChanged( MySingleton.getInstance().getCompare().get( 0 ).getCompareIndex() );
                        MySingleton.getInstance().getCompare().set( 0, null );
                        addToCompare1.setText( "Add Hall to\nCompare" );
                        addToCompare1.setTextColor( getResources().getColor( R.color.btnColorGrey ) );
                        TransitionDrawable transition = (TransitionDrawable) addToCompare1.getBackground();
                        transition.reverseTransition( 400 );
                        //addToCompare1.setBackground(getResources().getDrawable(R.drawable.btn_design_grey_stroke));
                        //venueCardAdapter.notifyDataSetChanged();
                        MySingleton.getInstance().setC1Empty( true );
                    }


                }
                break;
            case R.id.venue_btn_addtoCompare2:

                if (!MySingleton.getInstance().isC2Empty()) {
                    size = MySingleton.getInstance().getCompare().get( 1 ).getHalls().size();
                    list = MySingleton.getInstance().getCompare().get( 1 ).getHalls();

                    if (addToCompare2.getText().equals( list.get( MySingleton.getInstance().getCompareHallIndex2() ).getHallName() )) {
                        MySingleton.getInstance().getVenues().getResponse().get( 0 ).getPayload().get( MySingleton.getInstance().getCompareIndex2() ).setAddedToCompare( false );
                        MySingleton.getInstance().getVenues().getResponse().get( 0 ).getPayload().get( MySingleton.getInstance().getCompareIndex2() ).getHalls().get( MySingleton.getInstance().getCompareHallIndex2() ).setCompare( false );
                        alphaAnimatorAdapter.notifyItemChanged( MySingleton.getInstance().getCompare().get( 1 ).getCompareIndex() );
                        MySingleton.getInstance().getCompare().set( 1, null );
                        addToCompare2.setText( "Add Hall to\nCompare" );
                        addToCompare2.setTextColor( getResources().getColor( R.color.btnColorGrey ) );
                        TransitionDrawable transition = (TransitionDrawable) addToCompare2.getBackground();
                        transition.reverseTransition( 400 );
                        //addToCompare1.setBackground(getResources().getDrawable(R.drawable.btn_design_grey_stroke));
                        //venueCardAdapter.notifyDataSetChanged();
                        MySingleton.getInstance().setC2Empty( true );
                    }


                }
                break;
            case R.id.venue_btn_addtoCompare3:

                if (!MySingleton.getInstance().isC3Empty()) {
                    size = MySingleton.getInstance().getCompare().get( 2 ).getHalls().size();
                    list = MySingleton.getInstance().getCompare().get( 2 ).getHalls();
                    if (addToCompare3.getText().equals( list.get( MySingleton.getInstance().getCompareHallIndex3() ).getHallName() )) {
                        MySingleton.getInstance().getVenues().getResponse().get( 0 ).getPayload().get( MySingleton.getInstance().getCompareIndex3() ).setAddedToCompare( false );
                        MySingleton.getInstance().getVenues().getResponse().get( 0 ).getPayload().get( MySingleton.getInstance().getCompareIndex3() ).getHalls().get( MySingleton.getInstance().getCompareHallIndex3() ).setCompare( false );
                        alphaAnimatorAdapter.notifyItemChanged( MySingleton.getInstance().getCompare().get( 2 ).getCompareIndex() );
                        MySingleton.getInstance().getCompare().set( 2, null );
                        addToCompare3.setText( "Add Hall to\nCompare" );
                        addToCompare3.setTextColor( getResources().getColor( R.color.btnColorGrey ) );
                        TransitionDrawable transition = (TransitionDrawable) addToCompare3.getBackground();
                        transition.reverseTransition( 400 );

                        MySingleton.getInstance().setC3Empty( true );
                    }


                }
                break;
            case R.id.venue_btn_compare:
                if (MySingleton.getInstance().getCompareCount() > 1) {
                    i = new Intent( getActivity(), CompareActivity.class );
                    getActivity().startActivityForResult( i, CONSTANTS.RESULTCODE_COMPARE );
                } else {
//                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
//
//                    alert.setTitle(getString(R.string.app_name));
//                    alert.setMessage("Please select at least two Venues");
//                    alert.setCancelable(true);
//                    final AlertDialog alertDialog = alert.create();
//                    alertDialog.show();

                    GeneralUtils.CustomDialog( getActivity(), "Compare Halls", "Please add at least two halls for comparison." );

                }
                break;

            case R.id.venue_card_btn_select:


                Log.d( "HallIndex", MySingleton.getInstance().getSelectedHallPosition() + "" );
                Log.d( "HallIndex", MySingleton.getInstance().getSelectedHallVenuePosition() + "" );

                // if (!MySingleton.getInstance().getVenues().getResponse().get(0).getPayload().get(index - 1).getVenueID().equals(storage.loadVenueID())) {
                if (MySingleton.getInstance().getSelectedHallPosition() == 999
                        && MySingleton.getInstance().getSelectedHallVenuePosition() == 999) {

                    GeneralUtils.CustomDialog( getActivity(), "Hall Selection Required", "Please select a Hall for your event venue." );

                } else {
                    Payload venue = MySingleton.getInstance().getVenues().getResponse().get( 0 ).getPayload().get( MySingleton.getInstance().getSelectedHallVenuePosition() );
                    SelectedVenueFragment fragment = new SelectedVenueFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString( "isRec", venue.getIsRecomended() );
                    bundle.putString( "vAdd", venue.getVenueAddress() );
                    bundle.putString( "vLat", venue.getVenueLat() );
                    bundle.putString( "vLon", venue.getVenueLon() );
                    bundle.putStringArray( "vImages", venue.getVenueImages() );
                    bundle.putString( "vName", venue.getVenueName() );
                    bundle.putString( "vCount", venue.getTotalCount() );
                    bundle.putString( "vFilter", venue.getVenueFilter() );
                    bundle.putInt( "position", hallIndex );
                    bundle.putString( "vKestoneRating", venue.getKeystoneRating() );
                    bundle.putStringArray( "vPhone", venue.getVenuePhone() );
                    bundle.putSerializable( "vHalls", (Serializable) venue.getHalls() );
                    bundle.putSerializable( "vAttributes", (Serializable) venue.getAttributes() );

                    // resetCompareButtons();

                    fragment.setArguments( bundle );
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = manager.beginTransaction();
                    ft.setCustomAnimations( R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right );
                    ft.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN );
                    ft.replace( R.id.venue_filter_placeholder, fragment, CONSTANTS.FRAGMENT_SELECTED_VENUE );
                    ft.addToBackStack( null );
                    ft.commit();

                }


                break;
        }
    }


    @Override
    public void onPhoneClick(String[] phone) {
        dialog.setCancelable( true );
        dialog.setContentView( R.layout.dialogue_phone );
        ListView phoneList = (ListView) dialog.findViewById( R.id.phone_lv );
        PhoneNumbersAdapter adapter = new PhoneNumbersAdapter( getActivity(), phone );
        phoneList.setAdapter( adapter );
        phoneList.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent( Intent.ACTION_DIAL );
                intent.setData( Uri.parse( "tel:" + adapterView.getItemAtPosition( i ) ) );
                startActivity( intent );
            }
        } );
        dialog.getWindow().setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );
        dialog.show();

    }

    @Override
    public void onAddtoCompareClick(Payload payload, int position) {

    }

    @Override
    public void onImageClick(String[] images) {
        if (images.length > 0) {
            Dialog dialog1 = new Dialog( getActivity() );
            dialog1.requestWindowFeature( Window.FEATURE_NO_TITLE );
            dialog1.setCancelable( true );
            dialog1.setContentView( R.layout.dialogue_venue_images );
            RecyclerViewPager rv = (RecyclerViewPager) dialog1.findViewById( R.id.venue_images_dialogue_recycler );
            VenueImagesAdapter adapter = new VenueImagesAdapter( getActivity(), images );
            RecyclerView.LayoutManager lm = new GridLayoutManager( getActivity(), 1, GridLayoutManager.HORIZONTAL, false );
            rv.setLayoutManager( lm );
            rv.setAdapter( adapter );
            dialog1.getWindow().setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );
            dialog1.getWindow().setBackgroundDrawable( new ColorDrawable( android.graphics.Color.TRANSPARENT ) );
            dialog1.show();
        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder( getActivity() );
            alert.setTitle( "Keystone" );
            alert.setMessage( "No Images to Display" );
            alert.setCancelable( true );
            final AlertDialog alertDialog = alert.create();
            alertDialog.show();
        }

    }

    @Override
    public void onSelectClick(String VenueId, Payload venue) {

        if (!VenueId.equals( storage.loadVenueID() )) {

            GeneralUtils.CustomDialog( getActivity(), "", "Please select Hall first.." );

        } else {
            SelectedVenueFragment fragment = new SelectedVenueFragment();
            Bundle bundle = new Bundle();
            bundle.putString( "isRec", venue.getIsRecomended() );
            bundle.putString( "vAdd", venue.getVenueAddress() );
            bundle.putString( "vLat", venue.getVenueLat() );
            bundle.putString( "vLon", venue.getVenueLon() );
            bundle.putStringArray( "vImages", venue.getVenueImages() );
            bundle.putString( "vName", venue.getVenueName() );
            bundle.putString( "vCount", venue.getTotalCount() );
            bundle.putString( "vFilter", venue.getVenueFilter() );
            bundle.putInt( "position", hallIndex );
            bundle.putString( "vKestoneRating", venue.getKeystoneRating() );
            bundle.putStringArray( "vPhone", venue.getVenuePhone() );
            bundle.putSerializable( "vHalls", (Serializable) venue.getHalls() );
            bundle.putSerializable( "vAttributes", (Serializable) venue.getAttributes() );

            // resetCompareButtons();

            fragment.setArguments( bundle );
            FragmentManager manager = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            ft.setCustomAnimations( R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right );
            ft.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN );
            ft.replace( R.id.venue_filter_placeholder, fragment, CONSTANTS.FRAGMENT_SELECTED_VENUE );
            ft.addToBackStack( null );
            ft.commit();

        }
    }


    @Override
    public void onClickHall(final Halls halls, String HotelName, final int POSITION, final int position, final Payload payload, boolean refresh) {

        ListView sizeList;
        RecyclerView capacity;
        TextView hotelName, hallName, min_hall_size;
        TextView floorPlan, selectHall, compare;
        SelectHallCapacityAdapter selectHallCapacityAdapter = new SelectHallCapacityAdapter( getActivity(), halls.getCapacities() );
        SelectHallImageAdapter selectHallImageAdapter = new SelectHallImageAdapter( getActivity(), halls.getHallImages() );
        SelectHallSizeAdapter selectHallSizeAdapter = new SelectHallSizeAdapter( getActivity(), halls.getHallSizes() );

        dialog.setCancelable( true );
        dialog.setContentView( R.layout.dialogue_halls );
        min_hall_size = (TextView) dialog.findViewById( R.id.min_hall_size );
        hotelName = (TextView) dialog.findViewById( R.id.select_hall_dialogue_hotel_name );
        hallName = (TextView) dialog.findViewById( R.id.select_hall_dialogue_hall_name );
        floorPlan = (TextView) dialog.findViewById( R.id.select_hall_dialogue_btn_floorplan );
        compare = (TextView) dialog.findViewById( R.id.select_hall_dialogue_btn_compare );
        TextView next1 = (TextView) dialog.findViewById( R.id.next );
        TextView previous1 = (TextView) dialog.findViewById( R.id.previous );
        selectHall = (TextView) dialog.findViewById( R.id.select_hall_dialogue_btn_selecthall );

        if (MySingleton.getInstance().getSelectedHallVenuePosition() == POSITION && MySingleton.getInstance().getSelectedHallPosition() == position) {
            selectHall.setText( "Unselect Hall" );
            selectHall.setBackground( getResources().getDrawable( R.drawable.btn_design_solid ) );
        } else {
            selectHall.setText( "Select Hall" );
            selectHall.setBackground( getResources().getDrawable( R.drawable.btn_grey_solid_dark ) );
        }


        if (selectHallImageAdapter.getItemCount() <= 1) {
            next1.setVisibility( View.INVISIBLE );
            previous1.setVisibility( View.INVISIBLE );
        } else {
            next1.setVisibility( View.VISIBLE );
            previous1.setVisibility( View.VISIBLE );
        }

//        next1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int indexp = dialogueViewPager.getCurrentPosition();
//                dialogueViewPager.smoothScrollToPosition(indexp + 1);
//
//            }
//        });
//        previous1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                int indexm = dialogueViewPager.getCurrentPosition();
//                dialogueViewPager.smoothScrollToPosition(indexm - 1);
//            }
//        });

        selectHall.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //MySingleton.getInstance().getVenues().getResponse().get(0).getPayload().get(POSITION).setSelectedHall(position);

                Log.d( "first", MySingleton.getInstance().getSelectedHallVenuePosition() + "" );
                Log.d( "first", MySingleton.getInstance().getSelectedHallPosition() + "" );

                if (MySingleton.getInstance().getSelectedHallVenuePosition() == 999 && MySingleton.getInstance().getSelectedHallPosition() == 999) {
                    MySingleton.getInstance().setSelectedHallVenuePosition( POSITION );
                    MySingleton.getInstance().setSelectedHallPosition( position );
                    hallIndex = position;
                    storage.storeHallID( MySingleton.getInstance().getVenues().getResponse().get( 0 ).getPayload().get( POSITION ).getHalls().get( position ).getHallId() );
                    storage.storeVenueID( MySingleton.getInstance().getVenues().getResponse().get( 0 ).getPayload().get( POSITION ).getVenueID() );
                    alphaAnimatorAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                } else if (MySingleton.getInstance().getSelectedHallVenuePosition() == POSITION && MySingleton.getInstance().getSelectedHallPosition() == position) {
                    MySingleton.getInstance().setSelectedHallVenuePosition( 999 );
                    MySingleton.getInstance().setSelectedHallPosition( 999 );
                    hallIndex = 999;
                    storage.storeHallID( "" );
                    storage.storeVenueID( "" );
                    alphaAnimatorAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                } else {
                    MySingleton.getInstance().setSelectedHallVenuePosition( POSITION );
                    MySingleton.getInstance().setSelectedHallPosition( position );
                    hallIndex = position;
                    storage.storeHallID( MySingleton.getInstance().getVenues().getResponse().get( 0 ).getPayload().get( POSITION ).getHalls().get( position ).getHallId() );
                    storage.storeVenueID( MySingleton.getInstance().getVenues().getResponse().get( 0 ).getPayload().get( POSITION ).getVenueID() );
                    alphaAnimatorAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        } );

        floorPlan.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //dialog.dismiss();
                mailFloorPlan( storage.loadID(), MySingleton.getInstance().getVenues().getResponse().get( 0 ).getPayload().get( POSITION ).getHalls().get( position ).getHallId() );
                // openPDF(MySingleton.getInstance().getVenues().getResponse().get(0).getPayload().get(POSITION).getHalls().get(position).getFloorPlan());
            }
        } );

        if (MySingleton.getInstance().getVenues().getResponse().get( 0 ).getPayload().get( POSITION ).getHalls().get( position ).isCompare()) {
            compare.setEnabled( false );
            compare.setBackground( getResources().getDrawable( R.drawable.btn_grey_solid_dark ) );
            compare.setTextColor( ContextCompat.getColor( getActivity(), R.color.textColorWhite ) );
        }

//        if (POSITION == MySingleton.getInstance().getSelectedHallVenuePosition() && position == MySingleton.getInstance().getSelectedHallPosition()) {
//            selectHall.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.btn_design_solid));
//            selectHall.setEnabled(false);
//        }

        compare.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MySingleton.getInstance().isC1Empty()) {
                    addToCompare1.setText( halls.getHallName() );
                    TransitionDrawable transition = (TransitionDrawable) addToCompare1.getBackground();
                    transition.startTransition( 400 );
                    //addToCompare1.setBackground(getResources().getDrawable(R.drawable.btn_design_solid));
                    addToCompare1.setTextColor( getResources().getColor( R.color.textColorWhite ) );
                    MySingleton.getInstance().getCompare().set( 0, payload );
                    MySingleton.getInstance().getCompare().get( 0 ).setCompareIndex( POSITION );
                    MySingleton.getInstance().getCompare().get( 0 ).setCompareHallIndex( position );
                    MySingleton.getInstance().setCompareIndex1( POSITION );
                    MySingleton.getInstance().setCompareHallIndex1( position );
                    MySingleton.getInstance().getVenues().getResponse().get( 0 ).getPayload().get( POSITION ).setCompareIndex( POSITION );
                    MySingleton.getInstance().getVenues().getResponse().get( 0 ).getPayload().get( POSITION ).setCompareHallIndex( position );
                    MySingleton.getInstance().getVenues().getResponse().get( 0 ).getPayload().get( POSITION ).setAddedToCompare( true );
                    MySingleton.getInstance().getVenues().getResponse().get( 0 ).getPayload().get( POSITION ).getHalls().get( position ).setCompare( true );
                    MySingleton.getInstance().setC1Empty( false );

                    Log.d( "comparePayload", payload.toString() );
                    alphaAnimatorAdapter.notifyItemChanged( position );
                } else if (MySingleton.getInstance().isC2Empty()) {
                    addToCompare2.setText( halls.getHallName() );
                    TransitionDrawable transition = (TransitionDrawable) addToCompare2.getBackground();
                    transition.startTransition( 400 );
                    //addToCompare2.setBackground(getResources().getDrawable(R.drawable.btn_design_solid));
                    addToCompare2.setTextColor( getResources().getColor( R.color.textColorWhite ) );
                    MySingleton.getInstance().getCompare().set( 1, payload );
                    MySingleton.getInstance().getCompare().get( 1 ).setCompareIndex( POSITION );
                    MySingleton.getInstance().getCompare().get( 1 ).setCompareHallIndex( position );
                    MySingleton.getInstance().setCompareIndex2( POSITION );
                    MySingleton.getInstance().setCompareHallIndex2( position );
                    MySingleton.getInstance().getVenues().getResponse().get( 0 ).getPayload().get( POSITION ).setCompareIndex( POSITION );
                    MySingleton.getInstance().getVenues().getResponse().get( 0 ).getPayload().get( POSITION ).setCompareHallIndex( position );
                    MySingleton.getInstance().getVenues().getResponse().get( 0 ).getPayload().get( POSITION ).setAddedToCompare( true );
                    MySingleton.getInstance().getVenues().getResponse().get( 0 ).getPayload().get( POSITION ).getHalls().get( position ).setCompare( true );
                    MySingleton.getInstance().setC2Empty( false );
                    alphaAnimatorAdapter.notifyItemChanged( position );
                } else if (MySingleton.getInstance().isC3Empty()) {
                    addToCompare3.setText( halls.getHallName() );
                    TransitionDrawable transition = (TransitionDrawable) addToCompare3.getBackground();
                    transition.startTransition( 400 );
                    //addToCompare3.setBackground(getResources().getDrawable(R.drawable.btn_design_solid));
                    addToCompare3.setTextColor( getResources().getColor( R.color.textColorWhite ) );
                    MySingleton.getInstance().getCompare().set( 2, payload );
                    MySingleton.getInstance().getCompare().get( 2 ).setCompareIndex( POSITION );
                    MySingleton.getInstance().getCompare().get( 2 ).setCompareHallIndex( position );
                    MySingleton.getInstance().setCompareIndex3( POSITION );
                    MySingleton.getInstance().setCompareHallIndex3( position );
                    MySingleton.getInstance().getVenues().getResponse().get( 0 ).getPayload().get( POSITION ).setCompareIndex( POSITION );
                    MySingleton.getInstance().getVenues().getResponse().get( 0 ).getPayload().get( POSITION ).setCompareHallIndex( position );
                    MySingleton.getInstance().getVenues().getResponse().get( 0 ).getPayload().get( POSITION ).setAddedToCompare( true );
                    MySingleton.getInstance().getVenues().getResponse().get( 0 ).getPayload().get( POSITION ).getHalls().get( position ).setCompare( true );
                    MySingleton.getInstance().setC3Empty( false );
                    alphaAnimatorAdapter.notifyItemChanged( position );
                } else {
                    Toast.makeText( getActivity(), "All Filled", Toast.LENGTH_LONG ).show();
                }

                dialog.dismiss();
            }
        } );

        hotelName.setText( HotelName );
        hallName.setText( halls.getHallName() );
        min_hall_size.setText( halls.getMinHallSizeRequired() );

        dialogueViewPager = (RecyclerView) dialog.findViewById( R.id.select_hall_dialogue_image_rv );
        sizeList = (ListView) dialog.findViewById( R.id.select_hall_dialogue_hallsize_lv );
        capacity = (RecyclerView) dialog.findViewById( R.id.select_hall_dialogue_hallcapacity_rv );

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager( getActivity(), 1, GridLayoutManager.HORIZONTAL, false );
        RecyclerView.LayoutManager layoutManager2 = new GridLayoutManager( getActivity(), 1, GridLayoutManager.HORIZONTAL, false );

        dialogueViewPager.setLayoutManager( layoutManager );
        capacity.setLayoutManager( layoutManager2 );


        dialogueViewPager.setAdapter( selectHallImageAdapter );
        sizeList.setAdapter( selectHallSizeAdapter );
        capacity.setAdapter( selectHallCapacityAdapter );
        if (refresh)
            alphaAnimatorAdapter.notifyDataSetChanged();
        dialog.getWindow().setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );
        dialog.show();
    }


    @Override
    public void onClickCompare(final Halls halls, String HotelName, final int POSITION, final int position, final Payload payload, boolean refresh) {

        ListView sizeList;
        RecyclerView capacity;
        TextView hotelName, hallName, min_hall_size;
        TextView floorPlan, selectHall, compare;
        SelectHallCapacityAdapter selectHallCapacityAdapter = new SelectHallCapacityAdapter( getActivity(), halls.getCapacities() );
        SelectHallImageAdapter selectHallImageAdapter = new SelectHallImageAdapter( getActivity(), halls.getHallImages() );
        SelectHallSizeAdapter selectHallSizeAdapter = new SelectHallSizeAdapter( getActivity(), halls.getHallSizes() );

        if (!MySingleton.getInstance().getVenues().getResponse().get( 0 ).getPayload().get( POSITION ).getHalls().get( position ).isCompare()) {

            if (MySingleton.getInstance().isC1Empty()) {
                addToCompare1.setText( halls.getHallName() );
                TransitionDrawable transition = (TransitionDrawable) addToCompare1.getBackground();
                transition.startTransition( 400 );
                //addToCompare1.setBackground(getResources().getDrawable(R.drawable.btn_design_solid));
                addToCompare1.setTextColor( getResources().getColor( R.color.textColorWhite ) );
                MySingleton.getInstance().getCompare().set( 0, payload );
                MySingleton.getInstance().getCompare().get( 0 ).setCompareIndex( POSITION );
                MySingleton.getInstance().getCompare().get( 0 ).setCompareHallIndex( position );
                MySingleton.getInstance().setCompareIndex1( POSITION );
                MySingleton.getInstance().setCompareHallIndex1( position );
                MySingleton.getInstance().getVenues().getResponse().get( 0 ).getPayload().get( POSITION ).setCompareIndex( POSITION );
                MySingleton.getInstance().getVenues().getResponse().get( 0 ).getPayload().get( POSITION ).setCompareHallIndex( position );
                MySingleton.getInstance().getVenues().getResponse().get( 0 ).getPayload().get( POSITION ).setAddedToCompare( true );
                MySingleton.getInstance().getVenues().getResponse().get( 0 ).getPayload().get( POSITION ).getHalls().get( position ).setCompare( true );
                MySingleton.getInstance().setC1Empty( false );
                alphaAnimatorAdapter.notifyItemChanged( position );
            } else if (MySingleton.getInstance().isC2Empty()) {
                addToCompare2.setText( halls.getHallName() );
                TransitionDrawable transition = (TransitionDrawable) addToCompare2.getBackground();
                transition.startTransition( 400 );
                //addToCompare2.setBackground(getResources().getDrawable(R.drawable.btn_design_solid));
                addToCompare2.setTextColor( getResources().getColor( R.color.textColorWhite ) );
                MySingleton.getInstance().getCompare().set( 1, payload );
                MySingleton.getInstance().getCompare().get( 1 ).setCompareIndex( POSITION );
                MySingleton.getInstance().getCompare().get( 1 ).setCompareHallIndex( position );
                MySingleton.getInstance().setCompareIndex2( POSITION );
                MySingleton.getInstance().setCompareHallIndex2( position );
                MySingleton.getInstance().getVenues().getResponse().get( 0 ).getPayload().get( POSITION ).setCompareIndex( POSITION );
                MySingleton.getInstance().getVenues().getResponse().get( 0 ).getPayload().get( POSITION ).setCompareHallIndex( position );
                MySingleton.getInstance().getVenues().getResponse().get( 0 ).getPayload().get( POSITION ).setAddedToCompare( true );
                MySingleton.getInstance().getVenues().getResponse().get( 0 ).getPayload().get( POSITION ).getHalls().get( position ).setCompare( true );
                MySingleton.getInstance().setC2Empty( false );
                alphaAnimatorAdapter.notifyItemChanged( position );
            } else if (MySingleton.getInstance().isC3Empty()) {
                addToCompare3.setText( halls.getHallName() );
                TransitionDrawable transition = (TransitionDrawable) addToCompare3.getBackground();
                transition.startTransition( 400 );
                //addToCompare3.setBackground(getResources().getDrawable(R.drawable.btn_design_solid));
                addToCompare3.setTextColor( getResources().getColor( R.color.textColorWhite ) );
                MySingleton.getInstance().getCompare().set( 2, payload );
                MySingleton.getInstance().getCompare().get( 2 ).setCompareIndex( POSITION );
                MySingleton.getInstance().getCompare().get( 2 ).setCompareHallIndex( position );
                MySingleton.getInstance().setCompareIndex3( POSITION );
                MySingleton.getInstance().setCompareHallIndex3( position );
                MySingleton.getInstance().getVenues().getResponse().get( 0 ).getPayload().get( POSITION ).setCompareIndex( POSITION );
                MySingleton.getInstance().getVenues().getResponse().get( 0 ).getPayload().get( POSITION ).setCompareHallIndex( position );
                MySingleton.getInstance().getVenues().getResponse().get( 0 ).getPayload().get( POSITION ).setAddedToCompare( true );
                MySingleton.getInstance().getVenues().getResponse().get( 0 ).getPayload().get( POSITION ).getHalls().get( position ).setCompare( true );
                MySingleton.getInstance().setC3Empty( false );
                alphaAnimatorAdapter.notifyItemChanged( position );
            } else {
                Toast.makeText( getActivity(), "All Filled", Toast.LENGTH_LONG ).show();
            }

            if (refresh)
                alphaAnimatorAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onClickSelect(final Halls halls, String HotelName, final int POSITION, final int position, final Payload payload, boolean refresh) {

        MySingleton.getInstance().setSelectedHallVenuePosition( POSITION );
        MySingleton.getInstance().setSelectedHallPosition( position );
        hallIndex = position;
        storage.storeHallID( MySingleton.getInstance().getVenues().getResponse().get( 0 ).getPayload().get( POSITION ).getHalls().get( position ).getHallId() );
        storage.storeVenueID( MySingleton.getInstance().getVenues().getResponse().get( 0 ).getPayload().get( POSITION ).getVenueID() );
        alphaAnimatorAdapter.notifyDataSetChanged();

    }


    void GetVenues() {
//        String filters = filterManager.getSelectedFilters();
//        String[] filterData = filters.split( "; FilterID 3 ; FilterValue" );
        if (GeneralUtils.isNetworkAvailable( getActivity() )) {
            venueCardAdapter = new VenueCardAdapter( getActivity(), this, this, btnSelect );
            alphaAnimatorAdapter = new AlphaAnimatorAdapter( venueCardAdapter, recyclerViewLarge );
            GenericRequest<GetVenueListResponse> request = new GenericRequest<GetVenueListResponse>(Request.Method.POST, CONSTANTS.URL_GET_VENUE_LIST, GetVenueListResponse.class, new VenueRequest(storage.loadCityID(), "1", "100", filterManager.getSelectedFilters()),
                    new Response.Listener<GetVenueListResponse>() {
                        @Override
                        public void onResponse(GetVenueListResponse response) {
                            progressDialog.dismiss();
                            Log.d( "VENUE RESPONSE", response.toString() );
                            if (Boolean.valueOf( response.getResponse().get( 0 ).getStatus() )) {
                                recyclerViewLarge.setVisibility( View.VISIBLE );
                                recyclerViewLarge.setAlpha( 1 );
                                textLayout.setVisibility( View.GONE );

                                MySingleton.getInstance().setVenues( response );
                                storage.storeVenueDefault( response );

                                recyclerViewLarge.setAdapter( alphaAnimatorAdapter );
                                index = layout.findFirstVisibleItemPosition() + 2;
                                currentVenueIndex.setText( "Showing \n" + index + " of " + MySingleton.getInstance().getVenues().getResponse().get( 0 ).getPayload().size() + " Results" );
                                resetCompareButtons();
                            } else {
                                recyclerViewLarge.animate()
                                        .alpha( 0.0f )
                                        .setDuration( 500 )
                                        .setListener( new AnimatorListenerAdapter() {
                                            @Override
                                            public void onAnimationEnd(Animator animation) {
                                                super.onAnimationEnd( animation );
                                                recyclerViewLarge.setVisibility( View.GONE );
                                            }
                                        } );
//                            textLayout.animate()
//                                    .translationY(textLayout.getHeight())
//                                    .alpha(1.0f)
//                                    .setDuration(300);

                                textLayout.setVisibility( View.VISIBLE );
                                label.setText( "No Venues matching your search...." );

                                currentVenueIndex.setText( "No Results" );
                                resetCompareButtons();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    GeneralUtils.ShowAlert( getActivity(), getString( R.string.VolleyTimeout ) );

                }
            } );

            AppController.getInstance().addToRequestQueue( request );
        } else {
            GeneralUtils.displayNetworkAlert( getActivity(), false );
        }

    }

    void GetVenuesPaging(String page) {
//        String filters = filterManager.getSelectedFilters();
//        String [] filterData = filters.split( "; FilterID 3 ; FilterValue" );
        if (GeneralUtils.isNetworkAvailable( getActivity() )) {
            GenericRequest<GetVenueListResponse> request = new GenericRequest<GetVenueListResponse>( Request.Method.POST, CONSTANTS.URL_GET_VENUE_LIST, GetVenueListResponse.class, new VenueRequest( storage.loadCityID(), "1", "100", filterManager.getSelectedFilters() ),

                    new Response.Listener<GetVenueListResponse>() {
                        @Override
                        public void onResponse(GetVenueListResponse response) {
                            if (Boolean.valueOf( response.getResponse().get( 0 ).getStatus() )) {
                                List<Payload> payload = MySingleton.getInstance().getVenues().getResponse().get( 0 ).getPayload();
                                payload.addAll( response.getResponse().get( 0 ).getPayload() );
                                MySingleton.getInstance().getVenues().getResponse().get( 0 ).setPayload( payload );
                                recyclerViewLarge.getAdapter().notifyDataSetChanged();
                                currentVenueIndex.setText( "Showing \n" + index + " of " + MySingleton.getInstance().getVenues().getResponse().get( 0 ).getPayload().size() + " Results" );
                                if (!isBannerNext) {
                                    isBannerNext = true;
                                }
                            }


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            } );

            AppController.getInstance().addToRequestQueue( request );
        }


    }


    void resetCompareButtons() {
        addToCompare1.setText( "Add Hall to Compare" );
        addToCompare1.setTextColor( getResources().getColor( R.color.textColorGrey ) );
        TransitionDrawable transition = (TransitionDrawable) addToCompare1.getBackground();
        transition.resetTransition();
        //addToCompare1.setBackground(getResources().getDrawable(R.drawable.btn_design_grey_stroke));

        addToCompare2.setText( "Add Hall to Compare" );
        addToCompare2.setTextColor( getResources().getColor( R.color.textColorGrey ) );
        TransitionDrawable transition2 = (TransitionDrawable) addToCompare2.getBackground();
        transition2.resetTransition();
        //addToCompare2.setBackground(getResources().getDrawable(R.drawable.btn_design_grey_stroke));

        addToCompare3.setText( "Add Hall to Compare" );
        addToCompare3.setTextColor( getResources().getColor( R.color.textColorGrey ) );
        TransitionDrawable transition3 = (TransitionDrawable) addToCompare3.getBackground();
        transition3.resetTransition();
        //addToCompare3.setBackground(getResources().getDrawable(R.drawable.btn_design_grey_stroke));

        MySingleton.getInstance().resetCompare();
        MySingleton.getInstance().setVenues( storage.loadVenueDefault() );
    }


    void openPDF(String url) {
        Intent intent = new Intent( Intent.ACTION_VIEW );

        intent.setDataAndType( Uri.parse( "http://docs.google.com/viewer?url=" + url ), "text/html" );

        startActivity( intent );
    }

    public void ScrollToPosition(int pos, int POSTION) {

        // layout.scrollToPosition(VenueFilterActivity.index);
        recyclerViewLarge.smoothScrollToPosition( POSTION );
        venueCardAdapter.OpenHallDialogue( POSTION, pos );

    }

    void updateActivityUI() {
        Intent intent = new Intent( "updateUI" );
        intent.putExtra( "frag", 1 );
        LocalBroadcastManager.getInstance( getActivity() ).sendBroadcast( intent );
    }

    void mailFloorPlan(String userID, String eventID) {
        progressDialog.show();
        GenericRequest<FlorePlanMailResponse> request = new GenericRequest<FlorePlanMailResponse>( Request.Method.POST, CONSTANTS.URL_FLOORPLAN, FlorePlanMailResponse.class,
                new FlorePlanMailRequest( userID, eventID ), new Response.Listener<FlorePlanMailResponse>() {
            @Override
            public void onResponse(FlorePlanMailResponse response) {
                progressDialog.dismiss();
                GeneralUtils.ShowAlert( getActivity(), response.getResponse().get( 0 ).getMessage() );
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                GeneralUtils.ShowAlert( getActivity(), getString( R.string.VolleyTimeout ) );
            }
        } );
        AppController.getInstance().addToRequestQueue( request );
    }

}