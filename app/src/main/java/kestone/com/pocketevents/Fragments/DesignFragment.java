package kestone.com.pocketevents.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import it.gmariotti.recyclerview.adapter.AlphaAnimatorAdapter;
import kestone.com.pocketevents.Adapters.DesignFragment.DesignDialogueAdapter;
import kestone.com.pocketevents.Adapters.Theme.ThemeTypeAdapter;
import kestone.com.pocketevents.MODEL.DesignEmail.REQUEST.DesignEmailRequest;
import kestone.com.pocketevents.MODEL.DesignEmail.RESPONSE.DesignEmailResponse;
import kestone.com.pocketevents.MODEL.Manager.MySingleton;
import kestone.com.pocketevents.MODEL.More.REQUEST.MoreRequest;
import kestone.com.pocketevents.MODEL.More.RESPONSE.MoreResponse;
import kestone.com.pocketevents.MODEL.SaveVenue.REQUEST.SaveVenueRequest;
import kestone.com.pocketevents.MODEL.SaveVenue.RESPONSE.SaveEventResponse;
import kestone.com.pocketevents.MODEL.Theme.RESPONSE.DesignStatus;
import kestone.com.pocketevents.MODEL.Theme.RESPONSE.Payload;
import kestone.com.pocketevents.MODEL.Theme.RESPONSE.ThemeResponse;
import kestone.com.pocketevents.R;
import kestone.com.pocketevents.UI.AppController;
import kestone.com.pocketevents.UI.CitySearchActivity;
import kestone.com.pocketevents.Utilities.CONSTANTS;
import kestone.com.pocketevents.Utilities.GeneralUtils;
import kestone.com.pocketevents.Utilities.GenericRequest;
import kestone.com.pocketevents.Utilities.GridSpacingItemDecoration;
import kestone.com.pocketevents.Utilities.PrefEntities;
import kestone.com.pocketevents.Utilities.StorageUtilities;


public class DesignFragment extends Fragment implements View.OnClickListener, ThemeTypeAdapter.DesignCallback, DesignDialogueAdapter.ClickArtWork {//} DesignAdapterTheme.DesignCallback {

    public DesignDialogueAdapter adapterDsn;
    TextView venueText, hallText;
    TextView save, saveExit;
    RecyclerView recyclerView;
    //DesignAdapter designAdapter;
//  DesignAdapterTheme designAdapter;
    ThemeTypeAdapter typeAdapter;
    AlphaAnimatorAdapter animatorAdapter;
    //        DesignResponse data;
    ThemeResponse data, data2;
    Dialog dialog, filterSaved;
    StorageUtilities storage;
    ProgressDialog progressDialog;
    int selectedPos = 0;

    String designID = "", selectedTheme = "";
    TextView venueFilter_layout_city;
    TextView next, previous;
    Spinner spnrSelectTheme;
    ArrayList<String> listThemeName = new ArrayList<>();
    ArrayList<Payload> listImage = new ArrayList<Payload>();
    HashMap selectedImageId = new HashMap();
    HashSet<DesignStatus> setDStatus = new HashSet<>();
    ArrayList<Payload> displayImage = new ArrayList<>();
    HashSet<Integer> imageID = new HashSet<>();
    StringBuilder sb = new StringBuilder();

    int designSelectedPosition;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate( R.layout.fragment_design, container, false );
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );
        storage = new StorageUtilities( getActivity() );
        updateActivityUI();
        SetupFilterSavedDialogue();

        venueFilter_layout_city = (TextView) view.findViewById( R.id.venueFilterChangeCity );
//        venueFilter_layout_city.setOnClickListener( this );

        if (getArguments() != null) {
//            data = (DesignResponse) getArguments().getSerializable( "data" );
            data = (ThemeResponse) getArguments().getSerializable( "data" );
        }
        dialog = new Dialog( getActivity() );
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        progressDialog = new ProgressDialog( getActivity() );
        progressDialog.setMessage( "Please Wait" );
        progressDialog.setCancelable( false );
        recyclerView = (RecyclerView) view.findViewById( R.id.design_recycler );
        venueText = (TextView) view.findViewById( R.id.design_venueText );
        venueText.setText( storage.loadEventName() );
        //venueText.setText(data.getResponse().get(0).getVenueName());
        hallText = (TextView) view.findViewById( R.id.design_hallText );
//        hallText.setText( data.getResponse().get( 0 ).getHallName() );
        save = (TextView) view.findViewById( R.id.designSave );
        save.setOnClickListener( this );
        saveExit = (TextView) view.findViewById( R.id.designSaveExit );
        saveExit.setOnClickListener( this );
//        designAdapter = new DesignAdapter( getActivity(),data.getResponse().get(0).getPayload(), this);


        RecyclerView.LayoutManager layoutManager = new GridLayoutManager( getActivity(), 3 );
        recyclerView.setLayoutManager( layoutManager );
        recyclerView.addItemDecoration( new GridSpacingItemDecoration( 3, dpToPx( 10 ), true ) );
//        recyclerView.setItemAnimator( new DefaultItemAnimator() );


        //for theme selection
//        Design("");
        spnrSelectTheme = (Spinner) view.findViewById( R.id.spnrSelectTheme );

        listThemeName.clear();
        listThemeName.add( "Select theme for your event" );
//        for (int i = 0; i < data.getGetThemeResult().size(); i++) {
        for (int i = 0; i < data.getGetThemeResult().size(); i++) {
            listThemeName.add( data.getGetThemeResult().get( i ).getThemeName() );
        }
        spnrSelectTheme.setAdapter( new ArrayAdapter<String>( getActivity(), R.layout.simple_list_item_1, listThemeName ) );

        spnrSelectTheme.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    listImage.clear();
                    for (int th = 0; th < data.getGetThemeResult().size(); th++) {
                        for (int j = 0; j < data.getGetThemeResult().get( th ).getPayload().size(); j++) {
                            if (data.getGetThemeResult().get( th ).getThemeName().equals( listThemeName.get( i ) )
                                    && data.getGetThemeResult().get( th ).getPayload().get( j ).getIsPrimary().equalsIgnoreCase( "True" )) {
                                listImage.add( data.getGetThemeResult().get( th ).getPayload().get( j ) );
                                data.getGetThemeResult().get( th ).getPayload().get( j ).setSelected( true );
                                imageID.add( Integer.valueOf( data.getGetThemeResult().get( th ).getPayload().get( j ).getImageid() ) );
                                showHideRecycler( listImage );
                                selectedPos = i;
                                selectedTheme = listThemeName.get( i );
                                selectedImageId.put( j, data.getGetThemeResult().get( th ).getPayload().get( j ).getImageid()  );
                            } else {
                                data.getGetThemeResult().get( th ).getPayload().get( j ).setSelected( false );
                            }
                        }
                    }
//
                } else {
                    recyclerView.setVisibility( View.INVISIBLE );
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        } );


    }

    private void showHideRecycler(List<Payload> payload) {//List<ThemeResult> themeData) {
        recyclerView.setVisibility( View.VISIBLE );
//            designAdapter = new DesignAdapterTheme( getActivity(), payload, this );
        typeAdapter = new ThemeTypeAdapter( listImage, getActivity(), this );
        animatorAdapter = new AlphaAnimatorAdapter( typeAdapter, recyclerView );
        recyclerView.setAdapter( animatorAdapter );
        imageID.clear();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }

    void updateActivityUI() {
        Intent intent = new Intent( "updateUI" );
        intent.putExtra( "frag", 3 );
        LocalBroadcastManager.getInstance( getActivity() ).sendBroadcast( intent );
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round( TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics() ) );
    }

//    @Override
//    public void onDesignClick(List<Values> data, boolean isSelected, final int Position, final String DesignId, String Title) {
//        dialog.setContentView( R.layout.dialogue_design );
//        dialog.setCancelable( true );
//
//        final RecyclerViewPager recyclerView = (RecyclerViewPager) dialog.findViewById( R.id.designRV );
//        TextView selected = (TextView) dialog.findViewById( R.id.designSelect );
//        TextView email = (TextView) dialog.findViewById( R.id.designEmail );
//        next = (TextView) dialog.findViewById( R.id.next );
//        previous = (TextView) dialog.findViewById( R.id.previous );
//
//        TextView title = (TextView) dialog.findViewById( R.id.designN );
//        title.setText( Title + " Designs" );
//
//        final RecyclerView.LayoutManager lm = new GridLayoutManager( getActivity(), 1, GridLayoutManager.HORIZONTAL, false );
//        DesignDialogueAdapter adapter = new DesignDialogueAdapter( getActivity(), data );
////        DesignDialogueAdapter adapter = new DesignDialogueAdapter( getActivity(), listImage, dialog );
//
//        recyclerView.setLayoutManager( lm );
//        recyclerView.setAdapter( adapter );
//        recyclerView.smoothScrollToPosition( selectedPos-1 );
//
//
//        //   Toast.makeText(getActivity(),String.valueOf(data.size()),Toast.LENGTH_LONG).show();
//        if (data.size() <= 1) {
//            next.setVisibility( View.INVISIBLE );
//            previous.setVisibility( View.INVISIBLE );
//        } else {
//            next.setVisibility( View.VISIBLE );
//            previous.setVisibility( View.VISIBLE );
//        }
//
//
//        if (!isSelected) {
//            selected.setText( "Select" );
//            selected.setBackgroundColor( getResources().getColor( R.color.textColorRed ) );
//            selected.setTextColor( getResources().getColor( R.color.textColorWhite ) );
//        } else {
//            selected.setText( "Unselect" );
//            selected.setBackgroundColor( getResources().getColor( R.color.btnColorGrey ) );
//            selected.setTextColor( getResources().getColor( R.color.textColorWhite ) );
//        }
//
//
//        next.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int indexp = recyclerView.getCurrentPosition();
//                recyclerView.smoothScrollToPosition( indexp + 1 );
//
//            }
//        } );
//        previous.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                int indexm = recyclerView.getCurrentPosition();
//                recyclerView.smoothScrollToPosition( indexm - 1 );
//            }
//        } );
//        selected.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                designAdapter.setMultiSelect( Position );
//                dialog.dismiss();
//
//                designID = designAdapter.getSelected();
//                if (!designID.equals( "" )) {
//                    save.setBackgroundColor( getResources().getColor( R.color.btnColorGrey ) );
//
//                } else {
//                    save.setBackgroundColor( getResources().getColor( R.color.textColorRed ) );
//                }
//            }
//        } );
//        email.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (GeneralUtils.isNetworkAvailable( getActivity() )) {
//                    SendEmail( DesignId, storage.loadID() );
//                } else {
//                    GeneralUtils.displayNetworkAlert( getActivity(), false );
//                }
//            }
//        } );
//
//        dialog.getWindow().setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );
//        dialog.show();
//    }


//    @Override
//    public void onDesignClick(final int position, String strTitle, boolean isSelected) {
//        Log.e( "Title ", strTitle +" POS "+position);
//
//        dialog.setContentView( R.layout.dialogue_design_theme );
//        dialog.setCancelable( true );
//        final RecyclerViewPager recyclerView = (RecyclerViewPager) dialog.findViewById( R.id.designRV );
//        TextView selected = (TextView) dialog.findViewById( R.id.designSelect );
//        TextView email = (TextView) dialog.findViewById( R.id.designEmail );
//
//        next = (TextView) dialog.findViewById( R.id.next );
//        previous = (TextView) dialog.findViewById( R.id.previous );
//        TextView title = (TextView) dialog.findViewById( R.id.designN );
//        title.setText( strTitle + " Designs" );
//        displayImage.clear();
//        for(int i =0;i<listImage.size();i++){
//            if(strTitle.equals( listImage.get( i ).getThemeType() )){
//                displayImage.add( listImage.get( i ) );
//
//            }
//        }
////        for (int dis = 0;dis<displayImage.size();dis++){
////            if(dis==selectedPos-1){
////                displayImage.get( dis ).setSelected( true );
////            }else {
////                displayImage.get( dis ).setSelected( false );
////            }
////        }
//        RecyclerView.LayoutManager lm = new GridLayoutManager( getActivity(), 1, GridLayoutManager.HORIZONTAL, false );
//        adapterDsn = new DesignDialogueAdapter( DesignFragment.this, displayImage, dialog, selectedPos-1 );
//        recyclerView.setLayoutManager( lm );
//        recyclerView.setAdapter( adapterDsn );
//        recyclerView.smoothScrollToPosition( selectedPos-1 );
////        Log.e("Position ", ""+lm.findFirstVisibleItemPosition());
//
//        //   Toast.makeText(getActivity(),String.valueOf(data.size()),Toast.LENGTH_LONG).show();
//        if (displayImage.size() <= 1) {
//            next.setVisibility( View.INVISIBLE );
//            previous.setVisibility( View.INVISIBLE );
//        } else {
//            next.setVisibility( View.VISIBLE );
//            previous.setVisibility( View.VISIBLE );
//        }
//
//
//
//        if (!isSelected) {
//            selected.setText( "Select" );
//            selected.setBackgroundColor( getResources().getColor( R.color.textColorRed ) );
//            selected.setTextColor( getResources().getColor( R.color.textColorWhite ) );
//        } else {
//            selected.setText( "Unselect" );
//            selected.setBackgroundColor( getResources().getColor( R.color.btnColorGrey ) );
//            selected.setTextColor( getResources().getColor( R.color.textColorWhite ) );
//        }
//
//
//        next.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int indexp = recyclerView.getCurrentPosition();
//                recyclerView.smoothScrollToPosition( indexp + 1 );
//
////                Log.e( "Position nest ", ""+indexp );
////
////                if(selectedPos-1!=indexp+1)
////                    displayImage.get( indexp ).setSelected( false );
//
//            }
//        } );
//        previous.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                int indexm = recyclerView.getCurrentPosition();
//                recyclerView.smoothScrollToPosition( indexm - 1 );
//
//
//
////                if(selectedPos-1!=indexm-1)
////                    displayImage.get( indexm ).setSelected( false );
//            }
//        } );
//        selected.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                designAdapter.setMultiSelect( position );
//                dialog.dismiss();
//                Log.e("Status ", designAdapter.getSelected());
//                designID = designAdapter.getSelected();
//                if (!designID.equals( "" )) {
//                    save.setBackgroundColor( getResources().getColor( R.color.btnColorGrey ) );
//
//                } else {
//                    save.setBackgroundColor( getResources().getColor( R.color.textColorRed ) );
//                }
//            }
//        } );
////        email.setOnClickListener( new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////
////                if (GeneralUtils.isNetworkAvailable( getActivity() )) {
////                    SendEmail( DesignId, storage.loadID() );
////                } else {
////                    GeneralUtils.displayNetworkAlert( getActivity(), false );
////                }
////            }
////        } );
//
//        dialog.getWindow().setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );
//        dialog.show();
//    }

    void SendEmail(String DesignID, String UserID) {
        dialog.dismiss();
        progressDialog.show();
        GenericRequest<DesignEmailResponse> request = new GenericRequest<DesignEmailResponse>( Request.Method.POST, CONSTANTS.URL_DESIGN_EMAIL, DesignEmailResponse.class,
                new DesignEmailRequest( DesignID, UserID ), new Response.Listener<DesignEmailResponse>() {
            @Override
            public void onResponse(DesignEmailResponse response) {
                progressDialog.dismiss();

                final Dialog notSelected = new Dialog( getActivity() );
                notSelected.setCancelable( true );
                notSelected.requestWindowFeature( Window.FEATURE_NO_TITLE );
                notSelected.setContentView( R.layout.dialogue_event_saved );

                TextView messageTitle = (TextView) notSelected.findViewById( R.id.messageTitle );
                TextView messageBody = (TextView) notSelected.findViewById( R.id.messageBody );
                ImageView dialog_icon = (ImageView) notSelected.findViewById( R.id.dialog_icon );
//                messageTitle.setText(response.getResponse().get(0).getMessage());
//                messageBody.setText(response.getResponse().get(0).getMessage());
                messageTitle.setText( "Email Artwork" );
                messageBody.setText( "Artwork has been sent to your Email ID." );

                dialog_icon.setImageResource( R.drawable.tick_inside_a_circle );
                TextView gotIt = (TextView) notSelected.findViewById( R.id.eventSaved );
                gotIt.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        notSelected.dismiss();
                    }
                } );
                notSelected.getWindow().setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );
                notSelected.show();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.venueFilterChangeCity:
                Intent i;
                i = new Intent( getActivity(), CitySearchActivity.class );
                i.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                startActivity( i );

                break;
            case R.id.designSave:
                sb = new StringBuilder();
                for(int n =0;n<selectedImageId.size();n++){
                    sb.append( selectedImageId.get( n )+"," );
//                    Log.e("Selected id ", String.valueOf( selectedImageId.get( n )));
                }

                designID = sb.toString();

                Log.e( "Design ID ", designID );
                if (!designID.equals( "" )) {
//                        ol;
                    SaveFilters( storage.loadEventName(), false, false, storage.loadEventDate() );

                } else {
                    final Dialog notSelected = new Dialog( getActivity() );
                    notSelected.setCancelable( true );
                    notSelected.requestWindowFeature( Window.FEATURE_NO_TITLE );
                    notSelected.setContentView( R.layout.dialogue_event_saved );

                    TextView messageTitle = (TextView) notSelected.findViewById( R.id.messageTitle );
                    TextView messageBody = (TextView) notSelected.findViewById( R.id.messageBody );
                    ImageView dialog_icon = (ImageView) notSelected.findViewById( R.id.dialog_icon );
                    messageTitle.setText( "Select Design" );
                    messageBody.setText( "Please select your design \n elements" );
                    dialog_icon.setImageResource( R.drawable.forget_icon );
                    TextView gotIt = (TextView) notSelected.findViewById( R.id.eventSaved );
                    gotIt.setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            notSelected.dismiss();
                        }
                    } );
                    notSelected.getWindow().setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );
                    notSelected.show();
                }
                break;
            case R.id.designSaveExit:
//                designID = designAdapter.getSelected();
                sb = new StringBuilder();
                for(int n =0;n<selectedImageId.size();n++){
                    sb.append( selectedImageId.get( n )+"," );
//                    Log.e("Selected id ", String.valueOf( selectedImageId.get( n )));
                }

                designID = sb.toString();

                if (!designID.equals( "" )) {
/////////---------******----------////////////
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
/////////---------******----------////////////
                    final Dialog dialog = new Dialog( getContext() );
                    dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
                    dialog.setContentView( R.layout.dialog_save_and_exit );
                    TextView yes = (TextView) dialog.findViewById( R.id.yes );
                    yes.setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (GeneralUtils.isNetworkAvailable( getActivity() )) {
                                SaveFilters( storage.loadEventName(), true, false, storage.loadEventDate() );
                            } else {
                                GeneralUtils.displayNetworkAlert( getActivity(), false );
                            }
                            dialog.cancel();
                        }
                    } );

                    TextView no = (TextView) dialog.findViewById( R.id.no );
                    no.setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    } );

                    dialog.getWindow().setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );
                    dialog.show();


                    // }
                } else {

                    final Dialog notSelected = new Dialog( getActivity() );
                    notSelected.setCancelable( true );
                    notSelected.requestWindowFeature( Window.FEATURE_NO_TITLE );
                    notSelected.setContentView( R.layout.dialogue_event_saved );

                    TextView messageTitle = (TextView) notSelected.findViewById( R.id.messageTitle );
                    TextView messageBody = (TextView) notSelected.findViewById( R.id.messageBody );
                    ImageView dialog_icon = (ImageView) notSelected.findViewById( R.id.dialog_icon );
                    messageTitle.setText( "Select Design" );
                    messageBody.setText( "Please select your design \n elements" );
                    dialog_icon.setImageResource( R.drawable.forget_icon );
                    TextView gotIt = (TextView) notSelected.findViewById( R.id.eventSaved );
                    gotIt.setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            notSelected.dismiss();
                        }
                    } );
                    notSelected.getWindow().setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );
                    notSelected.show();
                }
                break;
        }
    }


    void SaveFilters(final String name, final boolean exit, boolean NEW, String date) {
        dialog.dismiss();

        progressDialog.show();
        GenericRequest<SaveEventResponse> request = new GenericRequest<SaveEventResponse>( Request.Method.POST, CONSTANTS.URL_SAVE_EVENT, SaveEventResponse.class,
                new SaveVenueRequest( storage.loadID(), name, "", storage.loadCityID(), storage.loadEventID(), date, storage.LoadKey( PrefEntities.HALLID ), storage.LoadKey( PrefEntities.VENUEID ), "0", designID, "" ), new Response.Listener<SaveEventResponse>() {
            @Override
            public void onResponse(SaveEventResponse response) {
                progressDialog.dismiss();
                dialog.dismiss();
                if (Boolean.parseBoolean( response.getResponse().get( 0 ).getStatus() )) {

                    if (exit) {
                        Intent intent = new Intent( getActivity(), CitySearchActivity.class );
                        startActivity( intent );
                    } else {
                        //filterSaved.show();

                        if (GeneralUtils.isNetworkAvailable( getActivity() )) {
                            GetMore( storage.loadID(), storage.loadEventID() );

                        } else {
                            GeneralUtils.displayNetworkAlert( getActivity(), false );
                        }
                    }
                } else {
                    GeneralUtils.ShowAlert( getActivity(), response.getResponse().get( 0 ).getMesssage() );
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                dialog.dismiss();
                GeneralUtils.ShowAlert( getActivity(), getString( R.string.VolleyTimeout ) );
            }
        } );

        AppController.getInstance().addToRequestQueue( request );
        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        //Toast.makeText(getActivity(),designID,Toast.LENGTH_LONG).show();

        // }

    }

    void openMoreFragment() {

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations( R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right );
        ft.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN );
        ft.replace( R.id.venue_filter_placeholder, new MoreFragment(), CONSTANTS.FRAGMENT_MORE );
        getActivity().getSupportFragmentManager().popBackStackImmediate( null, FragmentManager.POP_BACK_STACK_INCLUSIVE );
        ft.commit();
    }

    void SetupFilterSavedDialogue() {
        filterSaved = new Dialog( getActivity() );
        filterSaved.setCancelable( true );
        filterSaved.setContentView( R.layout.dialogue_event_saved );
        TextView gotIt = (TextView) filterSaved.findViewById( R.id.eventSaved );
        gotIt.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterSaved.dismiss();
            }
        } );
        filterSaved.getWindow().setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );
    }

    void GetMore(String UserID, String eventID) {
        Log.d( "UserID", UserID );
        Log.d( "eventID", eventID );
        progressDialog.show();
        GenericRequest<MoreResponse> request = new GenericRequest<MoreResponse>( Request.Method.POST, CONSTANTS.URL_MORE, MoreResponse.class,
                new MoreRequest( eventID, UserID ), new Response.Listener<MoreResponse>() {
            @Override
            public void onResponse(MoreResponse response) {
                progressDialog.dismiss();
                if (Boolean.parseBoolean( response.getResponse().get( 0 ).getStatus() )) {
                    if (MySingleton.getInstance() == null) {
                        MySingleton.initInstance();
                    }
                    MySingleton.getInstance().setMoreResponse( response );
                    storage.storeMoreData( response );
                    storage.storeMoreDataDefault( response );

                    final Dialog setUpSuccess = new Dialog( getActivity() );
                    setUpSuccess.requestWindowFeature( Window.FEATURE_NO_TITLE );
                    setUpSuccess.setCancelable( true );
                    setUpSuccess.setContentView( R.layout.dialog_design_selected_successfuly );

                    TextView procceed = (TextView) setUpSuccess.findViewById( R.id.proceed );
                    procceed.setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setUpSuccess.dismiss();
                            openMoreFragment();
                        }

                    } );
                    setUpSuccess.getWindow().setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT );
                    setUpSuccess.show();

                } else {
                    GeneralUtils.ShowAlert( getActivity(), response.getResponse().get( 0 ).getMessage() );
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

    }

    public void setSelectionImage(int pos) {

        for (int i = 0; i < displayImage.size(); i++) {
            displayImage.get( i ).setSelected( false );
            imageID.remove( Integer.valueOf( displayImage.get( pos ).getImageid() ) );
        }
        Log.e( "Position ", "" + pos );
        imageID.add( Integer.valueOf( displayImage.get( pos ).getImageid() ) );
        selectedImageId.replace(  designSelectedPosition, displayImage.get( pos ).getImageid()  );

        displayImage.get( pos ).setSelected( true );
        adapterDsn.notifyDataSetChanged();
    }

    @Override
    public void designCallback(final int pos, boolean isSelected) {
        designSelectedPosition=pos;
        String strTitle = listImage.get( pos ).getThemeType();
        dialog.setContentView( R.layout.dialogue_design_theme );
        dialog.setCancelable( true );
        final RecyclerViewPager recyclerView = (RecyclerViewPager) dialog.findViewById( R.id.designRV );
        final TextView selected = (TextView) dialog.findViewById( R.id.designSelect );
        TextView email = (TextView) dialog.findViewById( R.id.designEmail );

        next = (TextView) dialog.findViewById( R.id.next );
        previous = (TextView) dialog.findViewById( R.id.previous );
        TextView title = (TextView) dialog.findViewById( R.id.designN );
        title.setText( strTitle + " Designs" );
        displayImage.clear();
        for (int i = 0; i < data.getGetThemeResult().size(); i++) {
            for (int c = 0; c < data.getGetThemeResult().get( i ).getPayload().size(); c++) {
                if (strTitle.equals( data.getGetThemeResult().get( i ).getPayload().get( c ).getThemeType() ))
                    displayImage.add( data.getGetThemeResult().get( i ).getPayload().get( c ) );
            }
        }

        RecyclerView.LayoutManager lm = new GridLayoutManager( getActivity(), 1, GridLayoutManager.HORIZONTAL, false );
        adapterDsn = new DesignDialogueAdapter( DesignFragment.this, displayImage, dialog, selectedPos - 1, this );
        recyclerView.setLayoutManager( lm );
        recyclerView.setAdapter( adapterDsn );
        adapterDsn.notifyDataSetChanged();
        for (int dis = 0; dis < displayImage.size(); dis++) {
            if (displayImage.get( dis ).isSelected) {
                final int finalDis = dis;
                new Handler().postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {
                                recyclerView.smoothScrollToPosition( finalDis );
                            }
                        }, 50 );
            }
        }
//        new Handler().postDelayed(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        recyclerView.smoothScrollToPosition( selectedPos - 1 );
//                    }
//                }, 50 );

        if (!listImage.get( pos ).isDgnSelected) {
            selected.setText( "Select" );
            selected.setBackgroundColor( getResources().getColor( R.color.textColorRed ) );
            selected.setTextColor( getResources().getColor( R.color.textColorWhite ) );
        } else {
            selected.setText( "Unselect" );
            selected.setBackgroundColor( getResources().getColor( R.color.btnColorGrey ) );
            selected.setTextColor( getResources().getColor( R.color.textColorWhite ) );
        }

        next.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int indexp = recyclerView.getCurrentPosition();
                recyclerView.smoothScrollToPosition( indexp + 1 );

            }
        } );
        previous.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int indexm = recyclerView.getCurrentPosition();
                recyclerView.smoothScrollToPosition( indexm - 1 );
            }
        } );
        selected.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e( "Position ", "" + pos );
                if (listImage.get( pos ).isDgnSelected) {
                    listImage.get( pos ).setDgnSelected( false );
                    imageID.remove( Integer.valueOf( listImage.get( pos ).getImageid() ) );
                    selectedImageId.remove( pos );
                } else {
                    listImage.get( pos ).setDgnSelected( true );

                }
                typeAdapter.notifyDataSetChanged();
                dialog.dismiss();

            }
        } );

        dialog.getWindow().setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );
        dialog.show();

    }

    @Override
    public void onClickArtWork(String id) {
        SendEmail( id, storage.loadID() );

    }

}
