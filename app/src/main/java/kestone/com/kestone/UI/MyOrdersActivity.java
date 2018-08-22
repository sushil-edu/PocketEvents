package kestone.com.kestone.UI;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import it.gmariotti.recyclerview.adapter.AlphaAnimatorAdapter;
import kestone.com.kestone.Adapters.MyOrdersActivity.MyOrdersAdapter;
import kestone.com.kestone.MODEL.InvoiceEmail.REQUEST.InvoiceEmailRequest;
import kestone.com.kestone.MODEL.InvoiceEmail.RESPONSE.InvoiceEmailResponse;
import kestone.com.kestone.MODEL.MyOrders.REQUEST.MyOrdersRequest;
import kestone.com.kestone.MODEL.MyOrders.RESPONSE.MyOrdersResponse;
import kestone.com.kestone.MODEL.SubmitRating.RESPONSE.SubmitRatingResponse;
import kestone.com.kestone.MODEL.SubmitRating.RESQUEST.SubmitRatingRequest;
import kestone.com.kestone.R;
import kestone.com.kestone.Utilities.CONSTANTS;
import kestone.com.kestone.Utilities.GeneralUtils;
import kestone.com.kestone.Utilities.GenericRequest;
import kestone.com.kestone.Utilities.StorageUtilities;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MyOrdersActivity extends AppCompatActivity implements MyOrdersAdapter.MyOrdersAdapterCallBack {

    RecyclerView rv;
    AppCompatEditText search;
    MyOrdersAdapter myOrdersAdapter;
    AlphaAnimatorAdapter animatorAdapter;
    MyOrdersResponse data;
    ProgressDialog progressDialog;
    StorageUtilities storage;
    LinearLayout root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        progressDialog = new ProgressDialog(MyOrdersActivity.this);
        progressDialog.setMessage(getString(R.string.PleaseWait));
        progressDialog.setCancelable(false);
        storage = new StorageUtilities(MyOrdersActivity.this);
        rv = (RecyclerView)findViewById(R.id.MyordersActivityRV);
        search = (AppCompatEditText)findViewById(R.id.MyordersActivitySearch);
        root = (LinearLayout)findViewById(R.id.MyOrdersRoot);
        if (getIntent()!=null){
            data = (MyOrdersResponse) getIntent().getSerializableExtra("data");
            myOrdersAdapter = new MyOrdersAdapter(MyOrdersActivity.this,data.getResponse().get(0).getPayload());
            animatorAdapter = new AlphaAnimatorAdapter(myOrdersAdapter,rv);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            rv.setLayoutManager(layoutManager);
            rv.setAdapter(animatorAdapter);
        }

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (myOrdersAdapter!=null){
                    myOrdersAdapter.getFilter().filter(charSequence);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    void Email(String Invoice, String UserID){
        progressDialog.show();
        GenericRequest<InvoiceEmailResponse> request = new GenericRequest<InvoiceEmailResponse>(Request.Method.POST, CONSTANTS.URL_EMAIL_INVOICE, InvoiceEmailResponse.class,
                new InvoiceEmailRequest(Invoice, UserID), new Response.Listener<InvoiceEmailResponse>() {
            @Override
            public void onResponse(InvoiceEmailResponse response) {
                progressDialog.dismiss();
                GeneralUtils.ShowAlert(MyOrdersActivity.this,response.getResponse().get(0).getMessage());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                GeneralUtils.ShowAlert(MyOrdersActivity.this,getString(R.string.VolleyTimeout));
            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }

    @Override
    public void OnEmailClicked(String InvoiceID) {
        Email(InvoiceID,storage.loadID());
    }

    @Override
    public void OnRatingClicked(final String InvoiceID) {
        final Dialog dialog = new Dialog(MyOrdersActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialogue_submit_rating);
        final RatingBar ratingBar = (RatingBar) dialog.findViewById(R.id.sd_rating);
        final AppCompatEditText editText = (AppCompatEditText) dialog.findViewById(R.id.sd_et);
        TextView submit = (TextView) dialog.findViewById(R.id.sd_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(MyOrdersActivity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

                String ratingText = editText.getText().toString();
                String rating = String.valueOf(ratingBar.getRating());
                if (rating.equals("0.0")){
                    Snackbar snackbar = Snackbar.make(root, getResources().getString(R.string.RatingError), Snackbar.LENGTH_LONG);
                    snackbar.show();
                    dialog.dismiss();
                }else {
                    SubmitRating(storage.loadID(),rating,InvoiceID,ratingText);
                    dialog.dismiss();
                }

            }
        });

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    void SubmitRating(String UserID, String Rating, String InvoiceNo, String RatingText){
        progressDialog.show();
        GenericRequest<SubmitRatingResponse> request = new GenericRequest<SubmitRatingResponse>(Request.Method.POST, CONSTANTS.URL_SUBMIT_RATING, SubmitRatingResponse.class,
                new SubmitRatingRequest(UserID, Rating, InvoiceNo, RatingText), new Response.Listener<SubmitRatingResponse>() {
            @Override
            public void onResponse(SubmitRatingResponse response) {
                progressDialog.dismiss();
                if (Boolean.valueOf(response.getResponse().get(0).getStatus())){
                    GeneralUtils.ShowAlert(MyOrdersActivity.this,response.getResponse().get(0).getPayload().get(0).getMessage());
                    getMyOrders(storage.loadID());
                }else {
                    GeneralUtils.ShowAlert(MyOrdersActivity.this,response.getResponse().get(0).getMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                GeneralUtils.ShowAlert(MyOrdersActivity.this,getString(R.string.VolleyTimeout));

            }
        });
        AppController.getInstance().addToRequestQueue(request);

    }


    void getMyOrders(String UserID) {
        progressDialog.show();
        GenericRequest<MyOrdersResponse> request = new GenericRequest<MyOrdersResponse>(Request.Method.POST, CONSTANTS.URL_MYORDERS, MyOrdersResponse.class,
                new MyOrdersRequest(UserID), new Response.Listener<MyOrdersResponse>() {
            @Override
            public void onResponse(MyOrdersResponse response) {
                progressDialog.dismiss();
                if (Boolean.parseBoolean(response.getResponse().get(0).getStatus())) {
                    myOrdersAdapter = new MyOrdersAdapter(MyOrdersActivity.this,response.getResponse().get(0).getPayload());
                    animatorAdapter = new AlphaAnimatorAdapter(myOrdersAdapter,rv);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(MyOrdersActivity.this);
                    rv.setLayoutManager(layoutManager);
                    rv.setAdapter(animatorAdapter);

                } else {
                    GeneralUtils.ShowAlert(MyOrdersActivity.this, response.getResponse().get(0).getMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                GeneralUtils.ShowAlert(MyOrdersActivity.this,getString(R.string.VolleyTimeout));

            }
        });
        AppController.getInstance().addToRequestQueue(request);

    }
}
