package kestone.com.kestone.UI;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import it.gmariotti.recyclerview.adapter.AlphaAnimatorAdapter;
import kestone.com.kestone.Adapters.ContactUsActivity.ContactUsAdapter;
import kestone.com.kestone.MODEL.ContactUs.ContactUsResponse;
import kestone.com.kestone.MODEL.SubmitQuestion.REQUEST.SubmitQuestionRequest;
import kestone.com.kestone.MODEL.SubmitQuestion.RESPONSE.SubmitQuestionResponse;
import kestone.com.kestone.R;
import kestone.com.kestone.Utilities.CONSTANTS;
import kestone.com.kestone.Utilities.GeneralUtils;
import kestone.com.kestone.Utilities.GenericRequest;
import kestone.com.kestone.Utilities.StorageUtilities;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ContactUsActivity extends AppCompatActivity implements ContactUsAdapter.ContactUsCallback {

    RecyclerView recyclerView;
    AlphaAnimatorAdapter animatorAdapter;
    ContactUsAdapter adapter;
    ContactUsResponse data;
    LinearLayout root;
    StorageUtilities storage;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(ContactUsActivity.this);
        progressDialog.setMessage(getString(R.string.PleaseWait));
        progressDialog.setCancelable(false);
        setContentView(R.layout.activity_contact_us);
        storage = new StorageUtilities(getApplicationContext());
        if (getIntent() != null) {
            data = (ContactUsResponse) getIntent().getSerializableExtra("data");
        }
        root = (LinearLayout) findViewById(R.id.Root);
        recyclerView = (RecyclerView) findViewById(R.id.ContactUsActivityRV);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new ContactUsAdapter(ContactUsActivity.this, data.getResponse().get(0).getPayload());
        //animatorAdapter = new AlphaAnimatorAdapter(adapter,recyclerView);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    public void onSubmitClick(String id, String Invoice, String Query) {
        if (GeneralUtils.isNetworkAvailable(ContactUsActivity.this))
            SubmitQuestion(storage.loadID(), Invoice, id, Query);
        else GeneralUtils.displayNetworkAlert(ContactUsActivity.this, false);
    }

    @Override
    public void resetExpanded() {
        adapter.notifyDataSetChanged();
    }

    void SubmitQuestion(String UserID, String Invoice, String QuestionID, String query) {
        progressDialog.show();
        GenericRequest<SubmitQuestionResponse> request = new GenericRequest<SubmitQuestionResponse>(Request.Method.POST, CONSTANTS.URL_SUBMIT_QUERY, SubmitQuestionResponse.class,
                new SubmitQuestionRequest(UserID, QuestionID, Invoice, query), new Response.Listener<SubmitQuestionResponse>() {
            @Override
            public void onResponse(SubmitQuestionResponse response) {
                progressDialog.dismiss();
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                if (Boolean.valueOf(response.getResponse().get(0).getStatus())) {
//                    Snackbar snackbar = Snackbar
//                            .make(root,response.getResponse().get(0).getMessage(), Snackbar.LENGTH_LONG);
//
//                    snackbar.show();
                    //GeneralUtils.ShowAlert(ContactUsActivity.this,response.getResponse().get(0).getMessage(),"Submission Successful" );
                    adapter.notifyDataSetChanged();
                    final Dialog dialog = new Dialog(ContactUsActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(true);
                    dialog.setContentView(R.layout.dialog_contact_us_submit);
                    TextView yes = (TextView) dialog.findViewById(R.id.yes);
                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });

                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.show();


                } else {
                    Snackbar snackbar = Snackbar
                            .make(root, getResources().getString(R.string.SomethingWentWrong), Snackbar.LENGTH_LONG);

                    snackbar.show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                GeneralUtils.ShowAlert(ContactUsActivity.this, getString(R.string.VolleyTimeout));

            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }
}
