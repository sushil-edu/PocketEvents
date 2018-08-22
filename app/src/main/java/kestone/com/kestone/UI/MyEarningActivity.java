package kestone.com.kestone.UI;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.lang.annotation.ElementType;

import it.gmariotti.recyclerview.adapter.AlphaAnimatorAdapter;
import kestone.com.kestone.Adapters.ContactUsActivity.ContactUsAdapter;
import kestone.com.kestone.Adapters.MyEarningAdapter;
import kestone.com.kestone.MODEL.ContactUs.ContactUsResponse;
import kestone.com.kestone.MODEL.ReferalRewardList.RESPONSE.MyReferralRewardListResponse;
import kestone.com.kestone.MODEL.SubmitQuestion.REQUEST.SubmitQuestionRequest;
import kestone.com.kestone.MODEL.SubmitQuestion.RESPONSE.SubmitQuestionResponse;
import kestone.com.kestone.R;
import kestone.com.kestone.Utilities.CONSTANTS;
import kestone.com.kestone.Utilities.GeneralUtils;
import kestone.com.kestone.Utilities.GenericRequest;
import kestone.com.kestone.Utilities.StorageUtilities;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MyEarningActivity extends AppCompatActivity {

    RecyclerView my_earning_recycler;
    StorageUtilities storage;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_earning);
        my_earning_recycler = (RecyclerView)findViewById(R.id.my_earning_recycler);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            MyReferralRewardListResponse response = (MyReferralRewardListResponse) intent.getSerializableExtra("data");
         //   GeneralUtils.ShowAlert(MyEarningActivity.this,response.getResponse().get(0).getPayload().get(0).getDispatchstatus());
            MyEarningAdapter adapter = new MyEarningAdapter(MyEarningActivity.this,response.getResponse());
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            my_earning_recycler.setLayoutManager(layoutManager);
            my_earning_recycler.setAdapter(adapter);
        }
        else{
            GeneralUtils.ShowAlert(MyEarningActivity.this,"Something Went wrong ! Try Again");
        }

    }




    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
