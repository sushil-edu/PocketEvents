package kestone.com.kestone.UI;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import it.gmariotti.recyclerview.adapter.AlphaAnimatorAdapter;
import kestone.com.kestone.Adapters.ContactUsActivity.ContactUsAdapter;
import kestone.com.kestone.MODEL.ContactUs.ContactUsResponse;
import kestone.com.kestone.MODEL.ReferalRewardList.REQUEST.MyReferralRewardListRequest;
import kestone.com.kestone.MODEL.ReferalRewardList.RESPONSE.MyReferralRewardListResponse;
import kestone.com.kestone.MODEL.RewardsReferrals.REQUEST.ReferralsRewardRequest;
import kestone.com.kestone.MODEL.RewardsReferrals.RESPONSE.RewardsReferralsResponse;
import kestone.com.kestone.MODEL.SubmitQuestion.REQUEST.SubmitQuestionRequest;
import kestone.com.kestone.MODEL.SubmitQuestion.RESPONSE.SubmitQuestionResponse;
import kestone.com.kestone.R;
import kestone.com.kestone.Utilities.CONSTANTS;
import kestone.com.kestone.Utilities.GeneralUtils;
import kestone.com.kestone.Utilities.GenericRequest;
import kestone.com.kestone.Utilities.StorageUtilities;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RewardsAndReferrals extends AppCompatActivity {


    StorageUtilities storage;
    ProgressDialog progressDialog;
    LinearLayout view_all_earning;
    String referral_codes;
    TextView termsConditions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards_refferals);
        view_all_earning=(LinearLayout)findViewById(R.id.view_all_earning);
        TextView totalEarningpoint=(TextView)findViewById(R.id.earning_amount) ;
        TextView referral_code=(TextView)findViewById(R.id.referral_code) ;
        termsConditions=(TextView)findViewById(R.id.termsConditions);


        progressDialog = new ProgressDialog(RewardsAndReferrals.this);
        progressDialog.setMessage(getString(R.string.PleaseWait));
        progressDialog.setCancelable(false);
        storage = new StorageUtilities(getApplicationContext());

        Intent intent = getIntent();
        referral_codes = intent.getStringExtra("referral_codes");
        String totalEaring = intent.getStringExtra("totalEarningpoint");

        referral_code.setText(referral_codes);
        totalEarningpoint.setText(totalEaring);

        TextView invite_friend=(TextView) findViewById(R.id.invite_friend);
        invite_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            shareIt();
            }
        });


        view_all_earning.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               if (GeneralUtils.isNetworkAvailable(RewardsAndReferrals.this)) {
                   String id=storage.loadID();

                   RefferalRewardList(id);

               }
               else {
                   GeneralUtils.displayNetworkAlert(RewardsAndReferrals.this, false);
               }

           }
       });

        termsConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RewardsAndReferrals.this,TermsAndConditions.class);
                startActivity(intent);
            }
        });

    }

    void RefferalRewardList(String UserID){
        progressDialog.show();
        GenericRequest<MyReferralRewardListResponse> request = new GenericRequest<MyReferralRewardListResponse>(Request.Method.POST, CONSTANTS.URL_REFERRAL_REWARD_LIST,MyReferralRewardListResponse.class,
                new MyReferralRewardListRequest(UserID), new Response.Listener<MyReferralRewardListResponse>() {
            @Override
            public void onResponse(MyReferralRewardListResponse response) {
                progressDialog.dismiss();

                if (Boolean.valueOf(response.getResponse().get(0).getStatus())){
                    Intent intent =new Intent(RewardsAndReferrals.this,MyEarningActivity.class);
                    intent.putExtra("data",response);
                    startActivity(intent);

                }else {
                    GeneralUtils.ShowAlert(RewardsAndReferrals.this, getString(R.string.VolleyTimeout));
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                GeneralUtils.ShowAlert(RewardsAndReferrals.this,getString(R.string.VolleyTimeout));

            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }


    private void shareIt() {
        //sharing implementation here
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Sign-Up Pocket Events | Say Hello to Smarter Event Management");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Please help me earn 1000 Points with Pocket Events. You earn 1000 Points too once you sign-up using my Referral Code: "+referral_codes +". Download the app");
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
