package kestone.com.pocketevents.UI;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import kestone.com.pocketevents.MODEL.GetSavedVenue.REQUEST.GetSavedVenueRequest;
import kestone.com.pocketevents.MODEL.GetSavedVenue.RESPONSE.GetSavedEventListResponse;
import kestone.com.pocketevents.MODEL.SubmitQuestion.REQUEST.SubmitQuestionRequest;
import kestone.com.pocketevents.MODEL.SubmitQuestion.RESPONSE.SubmitQuestionResponse;
import kestone.com.pocketevents.R;
import kestone.com.pocketevents.Utilities.CONSTANTS;
import kestone.com.pocketevents.Utilities.GeneralUtils;
import kestone.com.pocketevents.Utilities.GenericRequest;
import kestone.com.pocketevents.Utilities.StorageUtilities;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ContactUsActivity extends AppCompatActivity {//} implements ContactUsAdapter.ContactUsCallback {

    //    RecyclerView recyclerView;
//    AlphaAnimatorAdapter animatorAdapter;
//    ContactUsAdapter adapter;
//    ContactUsResponse data;
    //for saved event name
    List<String> listSavedEvent = new ArrayList<>();
    LinearLayout root;
    StorageUtilities storage;
    ProgressDialog progressDialog;

    AppCompatEditText edit_query;
    Spinner spinnerSavedEvent;
    TextView btn_submit;
    String strSavedEvent="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        progressDialog = new ProgressDialog( ContactUsActivity.this );
        progressDialog.setMessage( getString( R.string.PleaseWait ) );
        progressDialog.setCancelable( false );
        setContentView( R.layout.activity_contact_us );

        storage = new StorageUtilities( getApplicationContext() );
//        if (getIntent() != null) {
//            data = (ContactUsResponse) getIntent().getSerializableExtra("data");
//        }
        root = (LinearLayout) findViewById( R.id.Root );
//        recyclerView = (RecyclerView) findViewById(R.id.ContactUsActivityRV);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        adapter = new ContactUsAdapter( ContactUsActivity.this, data.getResponse().get( 0 ).getPayload().get( 2 ) );
//        //animatorAdapter = new AlphaAnimatorAdapter(adapter,recyclerView);
//
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(adapter);

        progressDialog.show();
        GetallEvents();

        edit_query = (AppCompatEditText) findViewById( R.id.edit_query );
        spinnerSavedEvent = (Spinner) findViewById( R.id.spinner_savedEvent );
        btn_submit = (TextView) findViewById( R.id.btn_Submit );

        spinnerSavedEvent.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i>0) {
                    strSavedEvent= spinnerSavedEvent.getSelectedItem().toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        } );
        btn_submit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(edit_query.getText().length()>0){
                    SubmitQuestion(storage.loadID(), strSavedEvent, "", edit_query.getText().toString());
//                }else {
//                }
            }
        } );

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext( CalligraphyContextWrapper.wrap( newBase ) );
    }


//    @Override
//    public void onSubmitClick(String id, String Invoice, String Query) {
//        if (GeneralUtils.isNetworkAvailable(ContactUsActivity.this))
//            SubmitQuestion(storage.loadID(), Invoice, id, Query);
//        else GeneralUtils.displayNetworkAlert(ContactUsActivity.this, false);
//    }
//
//    @Override
//    public void resetExpanded() {
//        adapter.notifyDataSetChanged();
//    }

    void SubmitQuestion(String UserID, String Invoice, String QuestionID, String query) {
        progressDialog.show();
        GenericRequest<SubmitQuestionResponse> request = new GenericRequest<SubmitQuestionResponse>( Request.Method.POST, CONSTANTS.URL_SUBMIT_QUERY, SubmitQuestionResponse.class,
                new SubmitQuestionRequest( UserID, QuestionID, Invoice, query ), new Response.Listener<SubmitQuestionResponse>() {
            @Override
            public void onResponse(SubmitQuestionResponse response) {
                progressDialog.dismiss();
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService( INPUT_METHOD_SERVICE );
                inputMethodManager.hideSoftInputFromWindow( getCurrentFocus().getWindowToken(), 0 );
                if (Boolean.valueOf( response.getResponse().get( 0 ).getStatus() )) {
//                    Snackbar snackbar = Snackbar
//                            .make(root,response.getResponse().get(0).getMessage(), Snackbar.LENGTH_LONG);
//
//                    snackbar.show();
                    //GeneralUtils.ShowAlert(ContactUsActivity.this,response.getResponse().get(0).getMessage(),"Submission Successful" );
//                    adapter.notifyDataSetChanged();
                    final Dialog dialog = new Dialog( ContactUsActivity.this );
                    dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
                    dialog.setCancelable( true );
                    dialog.setContentView( R.layout.dialog_contact_us_submit );
                    TextView yes = (TextView) dialog.findViewById( R.id.yes );
                    yes.setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                            finish();
                        }
                    } );

                    dialog.getWindow().setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );
                    dialog.show();


                } else {
                    Snackbar snackbar = Snackbar
                            .make( root, getResources().getString( R.string.SomethingWentWrong ), Snackbar.LENGTH_LONG );

                    snackbar.show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                GeneralUtils.ShowAlert( ContactUsActivity.this, getString( R.string.VolleyTimeout ) );

            }
        } );
        AppController.getInstance().addToRequestQueue( request );
        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    void GetallEvents() {

        GenericRequest<GetSavedEventListResponse> request = new GenericRequest<GetSavedEventListResponse>( Request.Method.POST, CONSTANTS.URL_GET_SAVED_EVENT_LIST, GetSavedEventListResponse.class,
                new GetSavedVenueRequest( storage.loadID() )
                , new Response.Listener<GetSavedEventListResponse>() {
            @Override
            public void onResponse(GetSavedEventListResponse response) {
                progressDialog.dismiss();
                if (Boolean.valueOf( response.getResponse().get( 0 ).getStatus() )) {

                    listSavedEvent.clear();
                    listSavedEvent.add( "Select your event" );
                    for (int i = 0; i < response.getResponse().get( 0 ).getPayload().size(); i++) {
                        listSavedEvent.add( response.getResponse().get( 0 ).getPayload().get( i ).getEventName() );
                    }
                    spinnerSavedEvent.setAdapter( new ArrayAdapter<String>( ContactUsActivity.this, R.layout.simple_list_item_1, listSavedEvent ) );
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                GeneralUtils.ShowAlert( ContactUsActivity.this, getString( R.string.VolleyTimeout ) );
            }
        } );

        AppController.getInstance().addToRequestQueue( request );
//        request.setRetryPolicy(new DefaultRetryPolicy(
//                500,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
}
