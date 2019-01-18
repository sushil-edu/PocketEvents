package kestone.com.pocketevents.UI;

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
import kestone.com.pocketevents.Adapters.ContactUsActivity.ContactUsAdapter;
import kestone.com.pocketevents.Adapters.MyEarningAdapter;
import kestone.com.pocketevents.MODEL.ContactUs.ContactUsResponse;
import kestone.com.pocketevents.MODEL.ReferalRewardList.RESPONSE.MyReferralRewardListResponse;
import kestone.com.pocketevents.MODEL.SubmitQuestion.REQUEST.SubmitQuestionRequest;
import kestone.com.pocketevents.MODEL.SubmitQuestion.RESPONSE.SubmitQuestionResponse;
import kestone.com.pocketevents.R;
import kestone.com.pocketevents.Utilities.CONSTANTS;
import kestone.com.pocketevents.Utilities.GeneralUtils;
import kestone.com.pocketevents.Utilities.GenericRequest;
import kestone.com.pocketevents.Utilities.StorageUtilities;
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
