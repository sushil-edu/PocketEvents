package kestone.com.pocketevents.UI;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.gmariotti.recyclerview.adapter.AlphaAnimatorAdapter;
import kestone.com.pocketevents.Adapters.AllEventsActivity.AllEventAdapter;
import kestone.com.pocketevents.MODEL.DeleteEvent.REQUEST.DeleteEventRequest;
import kestone.com.pocketevents.MODEL.DeleteEvent.RESPONSE.DeleteEventResponse;
import kestone.com.pocketevents.MODEL.EventEmail.REQUEST.EventEmailRequest;
import kestone.com.pocketevents.MODEL.EventEmail.RESPONSE.EventEmailResponse;
import kestone.com.pocketevents.MODEL.Filters.REQUEST.SavedFilterRequest;
import kestone.com.pocketevents.MODEL.Filters.RESPONSE.Payload;
import kestone.com.pocketevents.MODEL.Filters.RESPONSE.SavedEventListResponse;
import kestone.com.pocketevents.MODEL.Filters.RESPONSE.SavedRange;
import kestone.com.pocketevents.MODEL.GetSavedVenue.REQUEST.GetSavedVenueRequest;
import kestone.com.pocketevents.MODEL.GetSavedVenue.RESPONSE.GetSavedEventListResponse;
import kestone.com.pocketevents.MODEL.Manager.MySingleton;
import kestone.com.pocketevents.MODEL.More.REQUEST.MoreRequest;
import kestone.com.pocketevents.MODEL.More.RESPONSE.MoreResponse;
import kestone.com.pocketevents.MODEL.Setup.REQUEST.SetupRequest;
import kestone.com.pocketevents.MODEL.Setup.RESPONSE.SetupResponse;
import kestone.com.pocketevents.MODEL.Theme.RESPONSE.ThemeResponse;
import kestone.com.pocketevents.R;
import kestone.com.pocketevents.Utilities.CONSTANTS;
import kestone.com.pocketevents.Utilities.GeneralUtils;
import kestone.com.pocketevents.Utilities.GenericRequest;
import kestone.com.pocketevents.Utilities.PrefEntities;
import kestone.com.pocketevents.Utilities.StorageUtilities;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AllEventsActivity extends AppCompatActivity implements AllEventAdapter.AllEventAdapterCallBack, Serializable {

    RecyclerView recyclerView;

    List<Payload> p1 = new ArrayList<>();
    List<Payload> p2 = new ArrayList<>();

    StorageUtilities storage;

    AppCompatEditText search;

    ProgressDialog progressDialogue;

    AllEventAdapter adapter;
    AlphaAnimatorAdapter animatorAdapter;

    AlertDialog alert;

    GetSavedEventListResponse getSavedEventListResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_all_events );

        storage = new StorageUtilities( AllEventsActivity.this );
        search = (AppCompatEditText) findViewById( R.id.allEventSearch );
        getSavedEventListResponse = (GetSavedEventListResponse) getIntent().getSerializableExtra( "events" );

        progressDialogue = new ProgressDialog( this );
        progressDialogue.setCancelable( false );
        progressDialogue.setMessage( "Please wait.." );

        recyclerView = (RecyclerView) findViewById( R.id.allEvents );

        LinearLayoutManager layoutManager = new LinearLayoutManager( this );

        adapter = new AllEventAdapter( AllEventsActivity.this, getSavedEventListResponse );

        recyclerView.setLayoutManager( layoutManager );
        animatorAdapter = new AlphaAnimatorAdapter( adapter, recyclerView );
        recyclerView.setAdapter( animatorAdapter );
        recyclerView.setItemAnimator( new DefaultItemAnimator() );

        search.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (GeneralUtils.isNetworkAvailable( AllEventsActivity.this ))
                    adapter.getFilter().filter( charSequence );
                else GeneralUtils.displayNetworkAlert( AllEventsActivity.this, false );
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        } );
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    void GetSavedFilter(String eventID) {
        SavedFilterRequest filterRequest = new SavedFilterRequest( eventID, storage.loadID() );
        Log.d( "VOLLEY", new Gson().toJson( filterRequest ) );
        GenericRequest<SavedEventListResponse> request = new GenericRequest<SavedEventListResponse>( Request.Method.POST, CONSTANTS.URL_SAVED_EVENT_FILTERS, SavedEventListResponse.class, filterRequest,
                new Response.Listener<SavedEventListResponse>() {
                    @Override
                    public void onResponse(SavedEventListResponse response) {
                        progressDialogue.dismiss();
                        if (Boolean.parseBoolean( response.getResponse().get( 0 ).getStatus() )) {
                            new setUpSingleton().execute( response );
                        } else {
                            AlertDialog.Builder alert = new AlertDialog.Builder( AllEventsActivity.this );
                            alert.setMessage( response.getResponse().get( 0 ).getMessage() );
                            alert.setCancelable( true );
                            final AlertDialog alertDialog = alert.create();
                            alertDialog.show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialogue.dismiss();
                GeneralUtils.ShowAlert( AllEventsActivity.this, getString( R.string.VolleyTimeout ) );
            }
        } );
        AppController.getInstance().addToRequestQueue( request );
    }

    @Override
    public void OnEventClicked(kestone.com.pocketevents.MODEL.GetSavedVenue.RESPONSE.Payload payload, int position) {
        // Toast.makeText(getApplicationContext(),payload.getEventId() + " " + storage.loadID(),Toast.LENGTH_LONG).show();
        if (position == 0) {
            storage.storeCityID( payload.getCityID() );
            storage.storeEventID( payload.getEventId() );
            storage.storeCity( payload.getCityname() );
            storage.storeEventName( payload.getEventName() );
            storage.storeEventDate( payload.getCreateDate() );
            GetSavedFilter( payload.getEventId() );
            progressDialogue.show();
        } else if (position == 1) {
            storage.storeCityID( payload.getCityID() );
            storage.storeEventID( payload.getEventId() );
            storage.storeCity( payload.getCityname() );
            storage.storeEventName( payload.getEventName() );
            storage.storeEventDate( payload.getCreateDate() );
            storage.StoreKey( PrefEntities.VENUEID, payload.getVenueId() );
            storage.storeHallID( payload.getHallId() );
            Setup( payload.getEventId(), payload.getVenueId(), payload.getHallId(), payload.getCityID() );
        } else if (position == 2) {
            storage.storeCityID( payload.getCityID() );
            storage.storeEventID( payload.getEventId() );
            storage.storeCity( payload.getCityname() );
            storage.storeEventName( payload.getEventName() );
            storage.storeEventDate( payload.getCreateDate() );
            storage.StoreKey( PrefEntities.VENUEID, payload.getVenueId() );
            storage.StoreKey( PrefEntities.SETUPID, payload.getSetupID() );
            storage.storeHallID( payload.getHallId() );
//            Design(payload.getEventId());
            Design();
        } else if (position == 3) {
            storage.storeCityID( payload.getCityID() );
            storage.storeEventID( payload.getEventId() );
            storage.storeCity( payload.getCityname() );
            storage.storeEventName( payload.getEventName() );
            storage.storeEventDate( payload.getCreateDate() );
            storage.StoreKey( PrefEntities.VENUEID, payload.getVenueId() );
            storage.StoreKey( PrefEntities.SETUPID, payload.getSetupID() );
            storage.storeHallID( payload.getHallId() );

            More( storage.loadID(), payload.getEventId() );

        } else {
            Email( payload.getEventId(), storage.loadID() );
        }

    }

    @Override
    public void OnDeleteClicked(final kestone.com.pocketevents.MODEL.GetSavedVenue.RESPONSE.Payload payload, final int position) {
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AllEventsActivity.this);
//
//        // Setting Dialog Title
//        alertDialog.setTitle(getString(R.string.app_name));
//
//        // Setting Dialog Message
//        alertDialog.setMessage("Confirm Delete Action?");
//
//        alertDialog.setCancelable(false);
//
//        // Setting Icon to Dialog
//        //alertDialog.setIcon(R.drawable.delete);
//
//        // On pressing Settings button
//        alertDialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog,int which) {
//                DeleteEvent(storage.loadID(),payload.getEventId(),position);
//            }
//        });
//
//        // on pressing cancel button
//        alertDialog.setNegativeButton("no", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                alert.dismiss();
//            }
//        });
//
//        // Showing Alert Message
//
//        alert = alertDialog.create();
//        alert.show();

        final Dialog clearAll = new Dialog( AllEventsActivity.this );
        clearAll.requestWindowFeature( Window.FEATURE_NO_TITLE );
        clearAll.setCancelable( true );
        clearAll.setContentView( R.layout.dialog_delete_event );
        TextView no = (TextView) clearAll.findViewById( R.id.no );
        TextView yes = (TextView) clearAll.findViewById( R.id.yes );
        yes.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteEvent( storage.loadID(), payload.getEventId(), position );
                clearAll.dismiss();
            }
        } );
        no.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clearAll.dismiss();
//
            }
        } );
        clearAll.getWindow().setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );
        clearAll.show();


    }

    @Override
    public void OnEmailClicked(String EventID) {
        Email( EventID, storage.loadID() );
//        new SendMail( EventID, storage.loadID() );

    }

    @Override
    public void OnConfigurationClicked(String EventID) {
        openPDF( EventID, storage.loadID() );
    }

    void DeleteEvent(String userID, String EventID, final int position) {
        progressDialogue.show();
        GenericRequest<DeleteEventResponse> request = new GenericRequest<DeleteEventResponse>( Request.Method.POST, CONSTANTS.URL_DELETE_EVENT, DeleteEventResponse.class,
                new DeleteEventRequest( EventID, userID ), new Response.Listener<DeleteEventResponse>() {
            @Override
            public void onResponse(DeleteEventResponse response) {
                progressDialogue.dismiss();
                if (Boolean.valueOf( response.getResponse().get( 0 ).getStatus() )) {
                    GetallEvents();


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialogue.dismiss();
                GeneralUtils.ShowAlert( AllEventsActivity.this, getString( R.string.VolleyTimeout ) );
            }
        } );
        AppController.getInstance().addToRequestQueue( request );
    }

    void Setup(String eventID, String venueID, String hallID, String cityID) {
        progressDialogue.show();
        GenericRequest<SetupResponse> request = new GenericRequest<SetupResponse>( Request.Method.POST, CONSTANTS.URL_SETUP, SetupResponse.class,
                new SetupRequest( eventID, venueID, hallID, cityID ), new Response.Listener<SetupResponse>() {
            @Override
            public void onResponse(SetupResponse response) {

                progressDialogue.dismiss();
                if (Boolean.valueOf( response.getResponse().get( 0 ).getStatus() )) {
                    //Toast.makeText(getApplicationContext(),response.getResponse().get(0).getStatus(),Toast.LENGTH_LONG).show();
                    Intent intent = new Intent( AllEventsActivity.this, VenueFilterActivity.class );
                    intent.putExtra( CONSTANTS.VENUESETUP_TAG, CONSTANTS.VENUESETUP_VALUE );
                    intent.putExtra( "data", (Serializable) response );
                    startActivity( intent );
                } else {
                    GeneralUtils.ShowAlert( AllEventsActivity.this, response.getResponse().get( 0 ).getMessage() );
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialogue.dismiss();
                GeneralUtils.ShowAlert( AllEventsActivity.this, getString( R.string.VolleyTimeout ) );


            }
        } );

        AppController.getInstance().addToRequestQueue( request );
    }

    void GetallEvents() {
        GenericRequest<GetSavedEventListResponse> request = new GenericRequest<GetSavedEventListResponse>( Request.Method.POST, CONSTANTS.URL_GET_SAVED_EVENT_LIST, GetSavedEventListResponse.class, new GetSavedVenueRequest( storage.loadID() )
                , new Response.Listener<GetSavedEventListResponse>() {
            @Override
            public void onResponse(GetSavedEventListResponse response) {
                adapter = new AllEventAdapter( AllEventsActivity.this, response );
                animatorAdapter = new AlphaAnimatorAdapter( adapter, recyclerView );
                recyclerView.setAdapter( animatorAdapter );
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                GeneralUtils.ShowAlert( AllEventsActivity.this, getString( R.string.VolleyTimeout ) );

            }
        } );

        AppController.getInstance().addToRequestQueue( request );
    }

//    void Design(String EventID) {
//        progressDialogue.show();
//        GenericRequest<DesignResponse> request = new GenericRequest<DesignResponse>( Request.Method.POST, CONSTANTS.URL_DESIGN, DesignResponse.class,
//                new DesignRequest( EventID ), new Response.Listener<DesignResponse>() {
//            @Override
//            public void onResponse(DesignResponse response) {
//
//                progressDialogue.dismiss();
//                if (response != null) {
//                    if (Boolean.parseBoolean( response.getResponse().get( 0 ).getStatus() )) {
//                        //MySingleton.getInstance().setDesignResponse(response);
//                        Intent intent = new Intent( AllEventsActivity.this, VenueFilterActivity.class );
//                        intent.putExtra( CONSTANTS.VENUEDESIGN_TAG, CONSTANTS.VENUEDESIGN_VALUE );
//                        intent.putExtra( "data", (Serializable) response );
//                        startActivity( intent );
//                    } else {
//                        GeneralUtils.ShowAlert( AllEventsActivity.this, response.getResponse().get( 0 ).getMessage() );
//                    }
//                } else {
//                    GeneralUtils.ShowAlert( AllEventsActivity.this, "Invalid Response from server" );
//                }
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                progressDialogue.dismiss();
//                GeneralUtils.ShowAlert( AllEventsActivity.this, getString( R.string.VolleyTimeout ) );
//            }
//        } );
//
//        AppController.getInstance().addToRequestQueue( request );
//    }

    void Design() {
        if (GeneralUtils.isNetworkAvailable( this )) {
            progressDialogue.show();
            GenericRequest<ThemeResponse> request = new GenericRequest<ThemeResponse>( Request.Method.GET, CONSTANTS.URL_DESIGN_THEME, ThemeResponse.class,
                    null, new Response.Listener<ThemeResponse>() {
                @Override
                public void onResponse(ThemeResponse response) {
                    progressDialogue.dismiss();
                    Log.e( "Size ", "" + response.getGetThemeResult().size() );
                    if (response.getGetThemeResult().size() > 0) {

                        Intent intent = new Intent( AllEventsActivity.this, VenueFilterActivity.class );
                        intent.putExtra( CONSTANTS.VENUEDESIGN_TAG, CONSTANTS.VENUEDESIGN_VALUE );
                        intent.putExtra( "data", (Serializable) response );
                        startActivity( intent );


                    } else {
                        GeneralUtils.ShowAlert( getApplicationContext(), "Theme not available" );
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialogue.dismiss();
                    GeneralUtils.ShowAlert( getApplicationContext(), getString( R.string.VolleyTimeout ) );
                }
            } );

            AppController.getInstance().addToRequestQueue( request );

        } else {
            GeneralUtils.displayNetworkAlert( getApplicationContext(), false );
        }

    }

    void More(String UserID, String eventID) {
        Log.d( "UserID", UserID );
        Log.d( "eventID", eventID );
        progressDialogue.show();
        GenericRequest<MoreResponse> request = new GenericRequest<MoreResponse>( Request.Method.POST, CONSTANTS.URL_MORE, MoreResponse.class,
                new MoreRequest( eventID, UserID ), new Response.Listener<MoreResponse>() {
            @Override
            public void onResponse(MoreResponse response) {
                progressDialogue.dismiss();
                if (Boolean.parseBoolean( response.getResponse().get( 0 ).getStatus() )) {
                    if (MySingleton.getInstance() == null) {
                        MySingleton.initInstance();
                    }
                    MySingleton.getInstance().setMoreResponse( response );
                    storage.storeMoreData( response );
                    storage.storeMoreDataDefault( response );

                    Intent intent = new Intent( AllEventsActivity.this, VenueFilterActivity.class );
                    intent.putExtra( CONSTANTS.VENUEMORE_TAG, CONSTANTS.VENUEMORE_VALUE );
                    startActivity( intent );

                } else {
                    GeneralUtils.ShowAlert( AllEventsActivity.this, response.getResponse().get( 0 ).getMessage() );
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialogue.dismiss();
                GeneralUtils.ShowAlert( AllEventsActivity.this, getString( R.string.VolleyTimeout ) );
            }
        } );
        AppController.getInstance().addToRequestQueue( request );

    }

    void Email(String EventID, String UserID) {
        progressDialogue.show();
        GenericRequest<EventEmailResponse> request = new GenericRequest<EventEmailResponse>( Request.Method.POST, CONSTANTS.URL_EMAIL_ALLEVENTS, EventEmailResponse.class,
                new EventEmailRequest( EventID, UserID ), new Response.Listener<EventEmailResponse>() {
            @Override
            public void onResponse(EventEmailResponse response) {
                progressDialogue.dismiss();
                Log.e( "Response ", response.getResponse().get( 0 ).getCode() );
                GeneralUtils.CustomDialogSucessWithImage( AllEventsActivity.this, "Email Configuration Sent", "Event configuration has been sent to your Email ID" );
                // GeneralUtils.ShowAlert(AllEventsActivity.this,"BOQ sent to Registered Email Account");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialogue.dismiss();
                GeneralUtils.ShowAlert( AllEventsActivity.this, getString( R.string.VolleyTimeout ) );
            }
        } );
        AppController.getInstance().addToRequestQueue( request );
        request.setRetryPolicy( new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT ) );
    }

    void openPDF(String eventID, String userID) {
        String uri = Uri.parse( CONSTANTS.url_event_configuration )
                .buildUpon()
                .appendQueryParameter( "EventID", eventID )
                .appendQueryParameter( "UserID", userID )
                .build().toString();

        Intent intent = new Intent( AllEventsActivity.this, TermsAndConditions.class );
        intent.putExtra( "title", "View Configuration" );
        intent.putExtra( "url", uri );
        startActivity( intent );

//        Intent intent = new Intent( Intent.ACTION_VIEW );
////        http://pocketevents.in/eiab/M_EmailView.aspx?EventID=3371&UserID=1021

//        Log.e( "Configuration ", uri );
//
//        intent.setDataAndType( Uri.parse( uri ), "text/html");
//        startActivity(intent);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext( CalligraphyContextWrapper.wrap( newBase ) );
    }

    class setUpSingleton extends AsyncTask<SavedEventListResponse, Void, String> {

        @Override
        protected String doInBackground(SavedEventListResponse... savedEventListResponses) {

            p1.clear();
            p2.clear();
            SavedEventListResponse response = savedEventListResponses[0];

            if (MySingleton.getInstance() == null) {
                MySingleton.initInstance();
            }

            //MySingleton.getInstance().setModel(response);
            for (int i = 0; i < response.getResponse().get( 0 ).getPayloads().size(); i++) {
                if (response.getResponse().get( 0 ).getPayloads().get( i ).getMoreType().equals( "0" )) {
                    if (response.getResponse().get( 0 ).getPayloads().get( i ).getStyle().equals( "2" )) {
                        if (response.getResponse().get( 0 ).getPayloads().get( i ).getValues().size() > 2) {
                            SavedRange range = new SavedRange();
                            range.setLowerLimit( response.getResponse().get( 0 ).getPayloads().get( i ).getValues().get( 2 ).getNameLabel() );
                            range.setUpperLimit( response.getResponse().get( 0 ).getPayloads().get( i ).getValues().get( 3 ).getNameLabel() );
                            response.getResponse().get( 0 ).getPayloads().get( i ).setRanges( range );
                        }
                        p1.add( response.getResponse().get( 0 ).getPayloads().get( i ) );
                    } else {
                        p1.add( response.getResponse().get( 0 ).getPayloads().get( i ) );
                    }
                } else {
                    p2.add( response.getResponse().get( 0 ).getPayloads().get( i ) );
                }

            }
            MySingleton.getInstance().setPayload1( p1 );
            MySingleton.getInstance().setPayload2( p2 );
            storage.storePayload1( p1 );
            storage.storePayload3( p2 );


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute( s );
            progressDialogue.dismiss();

            Intent i = new Intent( AllEventsActivity.this, VenueFilterActivity.class );
            startActivity( i );

        }
    }

}


//FilterID 1 ; FilterValue 3; FilterID 13 ; FilterValue 45; FilterID 5 ; FilterValue 18; FilterID 6 ; FilterValue 20,21,22