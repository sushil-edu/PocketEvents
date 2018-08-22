package kestone.com.kestone.UI;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import java.util.List;

import io.fabric.sdk.android.services.common.CommonUtils;
import kestone.com.kestone.Adapters.DialogueAdapters.SelectHallImageAdapter;
import kestone.com.kestone.Adapters.SetupActivity.SetupDetailsAdapter;
import kestone.com.kestone.MODEL.ContactUs.ContactUsResponse;
import kestone.com.kestone.MODEL.Setup.RESPONSE.Details;
import kestone.com.kestone.R;
import kestone.com.kestone.Utilities.CONSTANTS;
import kestone.com.kestone.Utilities.GeneralUtils;
import kestone.com.kestone.Utilities.GenericRequest;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SetupActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recyclerView;
    RecyclerViewPager recyclerViewPager;
    TextView banner;
    ProgressDialog progressDialog;
    TextView gotIt, next, previous,contactUs;
    List<Details> details;
    String[] images;
    LinearLayoutManager layout;
    LinearLayoutManager layout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance_setup);
        progressDialog = new ProgressDialog(SetupActivity.this);
        progressDialog.setMessage(getString(R.string.PleaseWait));
        progressDialog.setCancelable(false);
        gotIt = (TextView) findViewById(R.id.setup_btn_gotIt);
       // contactUs = (Button) findViewById(R.id.setup_btn_contact_us);
       // contactUs.setOnClickListener(this);
        gotIt.setOnClickListener(this);
        next = (TextView) findViewById(R.id.next);
        next.setOnClickListener(this);
        previous = (TextView) findViewById(R.id.previous);
        previous.setOnClickListener(this);
        banner = (TextView) findViewById(R.id.setup_banner);
        recyclerView = (RecyclerView) findViewById(R.id.setup_details_rv);
        recyclerViewPager = (RecyclerViewPager) findViewById(R.id.setup_image_rv);

        if (getIntent() != null) {
            details = (List<Details>) getIntent().getSerializableExtra("data");
            images = getIntent().getStringArrayExtra("images");
            banner.setText(getIntent().getStringExtra("banner"));
            if (images.length<=1){
               next.setVisibility(View.INVISIBLE);
               previous.setVisibility(View.INVISIBLE);
            }
            else {
                next.setVisibility(View.VISIBLE);
                previous.setVisibility(View.VISIBLE);
            }
        }

        SelectHallImageAdapter imageAdapter = new SelectHallImageAdapter(SetupActivity.this, images);
        layout = new LinearLayoutManager(SetupActivity.this, LinearLayoutManager.VERTICAL, false);
        layout2 = new LinearLayoutManager(SetupActivity.this, LinearLayoutManager.HORIZONTAL, false);
        SetupDetailsAdapter adapter = new SetupDetailsAdapter(details);
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(adapter);
        recyclerViewPager.setLayoutManager(layout2);
        recyclerViewPager.setAdapter(imageAdapter);

    }

    @Override
    public void onClick(View view) {
        int index;
        switch (view.getId()) {
            case R.id.setup_btn_gotIt:
                finish();
                break;
            case R.id.next:
                index = layout2.findFirstVisibleItemPosition();
                recyclerViewPager.smoothScrollToPosition(index + 1);
                break;
            case R.id.previous:
                index = layout2.findFirstVisibleItemPosition();
                recyclerViewPager.smoothScrollToPosition(index - 1);
                break;

        }
    }

    void ContactUs(){
        progressDialog.show();
        GenericRequest<ContactUsResponse> request = new GenericRequest<ContactUsResponse>(Request.Method.GET, CONSTANTS.URL_CONTACT_US, ContactUsResponse.class,
                null, new Response.Listener<ContactUsResponse>() {
            @Override
            public void onResponse(ContactUsResponse response) {
                progressDialog.dismiss();
                if (Boolean.valueOf(response.getResponse().get(0).getStatus())){
                    Intent intent = new Intent(SetupActivity.this,ContactUsActivity.class);
                    intent.putExtra("data",response);
                    startActivity(intent);

                }else {
                    GeneralUtils.ShowAlert(SetupActivity.this,getString(R.string.VolleyTimeout));
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                GeneralUtils.ShowAlert(SetupActivity.this,getString(R.string.VolleyTimeout));

            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
