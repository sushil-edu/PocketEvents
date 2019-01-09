package kestone.com.kestone.UI;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import kestone.com.kestone.MODEL.RefferalValidation.REQUEST.RefferalValidationRequest;
import kestone.com.kestone.MODEL.RefferalValidation.RESPONSE.RefferalValidationResponse;
import kestone.com.kestone.MODEL.Register.REQUEST.RegisterRequest;
import kestone.com.kestone.MODEL.Register.RESPONSE.InsertSignUPResponse;
import kestone.com.kestone.MODEL.RewardsReferrals.REQUEST.ReferralsRewardRequest;
import kestone.com.kestone.R;
import kestone.com.kestone.Utilities.CONSTANTS;
import kestone.com.kestone.Utilities.GeneralUtils;
import kestone.com.kestone.Utilities.GenericRequest;
import kestone.com.kestone.Utilities.StorageUtilities;
import qiu.niorgai.StatusBarCompat;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    TextView login, otp;
    AppCompatEditText fname, email, mobile, pass, register_et_refferal_code, cpass, register_et_address;
    // EditText register_et_refferal_code;

    InsertSignUPResponse result;

    LinearLayout root;

    ImageView logo;

    StorageUtilities storage;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  StatusBarCompat.translucentStatusBar(RegisterActivity.this,true);
        setContentView(R.layout.activity_register);
        login = (TextView) findViewById(R.id.register_btn_login);
        fname = (AppCompatEditText) findViewById(R.id.register_et_fname);
//        lname = (AppCompatEditText)findViewById(R.id.register_et_lname);
        email = (AppCompatEditText) findViewById(R.id.register_et_email);
        mobile = (AppCompatEditText) findViewById(R.id.register_et_mobile);
        pass = (AppCompatEditText) findViewById(R.id.register_et_pass);
        pass.setTransformationMethod(new PasswordTransformationMethod());
        cpass = (AppCompatEditText) findViewById(R.id.register_et_confirm_pass);
        cpass.setTransformationMethod(new PasswordTransformationMethod());
        register_et_refferal_code = (AppCompatEditText) findViewById(R.id.register_et_refferal_code);
        register_et_address = (AppCompatEditText) findViewById(R.id.register_et_address);


        register_et_refferal_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String refferalCode = register_et_refferal_code.getText().toString();
                if (refferalCode.length() == 9) {
                    VerifieReferralCode(refferalCode);
                } else {
                    register_et_refferal_code.setTextColor(Color.WHITE);
                }
            }

            @Override
            public void afterTextChanged(Editable et) {
                String m = et.toString();

                boolean isUnderScore = false;


                String s = et.toString();
                if (!s.equals(s.toUpperCase())) {
                    s = s.toUpperCase();
                    register_et_refferal_code.setText(s);

                    if (s.length() == 4) {
                        String G = s + "_";
                        register_et_refferal_code.setText(G);
                    }
                }

            }
        });


        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        root = (LinearLayout) findViewById(R.id.register_root);
        logo = (ImageView) findViewById(R.id.register_logo);

        root.setOnClickListener(this);
        logo.setOnClickListener(this);

        otp = (TextView) findViewById(R.id.register_btn_otp);
        login.setOnClickListener(this);
        otp.setOnClickListener(this);

        storage = new StorageUtilities(getApplicationContext());
    }


    @Override
    public void onClick(View view) {
        Intent i;
        InputMethodManager inputMethodManager;
        inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        switch (view.getId()) {
            case R.id.register_btn_login:
                i = new Intent(RegisterActivity.this, LoginActivity.class);
                if (GeneralUtils.hasLollipop()) {
                    String transitionName = getString(R.string.logoAnimation);
                    View sharedView = logo;
                    ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(RegisterActivity.this, sharedView, transitionName);
                    startActivity(i);
                } else {
                    startActivity(i);
                }
                break;
            case R.id.register_btn_otp:


                inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);


                if (pass.getText().toString().equals(cpass.getText().toString()) && GeneralUtils.isEmailValid(email.getText().toString())) {
                    if (pass.getText().toString().length() < 1) {

                        //GeneralUtils.ShowAlert(RegisterActivity.this,getString(R.string.ValidPassword));
                        showPopUp("Password Required", "Password field cannot be left empty", "OK");
                    } else if (pass.getText().toString().length() < 4) {

                        //GeneralUtils.ShowAlert(RegisterActivity.this,getString(R.string.ValidPassword));
                        showPopUp("Invalid Password", "Password should be at least 4 characters long", "Retry");
                    } else if (TextUtils.isEmpty(register_et_address.getText().toString())) {

                        //GeneralUtils.ShowAlert(RegisterActivity.this,"Enter Valid Address");
                        showPopUp("Address Required", "Address field cannot be left empty", "OK");

                    } else if (TextUtils.isEmpty(mobile.getText().toString())) {

                    //GeneralUtils.ShowAlert(RegisterActivity.this,"Enter Valid Address");
                    showPopUp("Mobile Number Required", "Mobile number field cannot be left empty", "OK");

                } else {
                        if (GeneralUtils.isNetworkAvailable(RegisterActivity.this)) {
                            Register(fname.getText().toString().trim(), "", email.getText().toString().trim(), mobile.getText().toString().trim(), pass.getText().toString().trim(), register_et_address.getText().toString(), register_et_refferal_code.getText().toString());
                            progressDialog.show();
                        } else {
                            GeneralUtils.displayNetworkAlert(RegisterActivity.this, false);
                        }
                    }

                } else if (email.getText().length() < 1) {
                    showPopUp("Email ID Required ", "Please enter Email ID for registration", "OK");
                } else if (!GeneralUtils.isEmailValid(email.getText().toString())) {
                    //GeneralUtils.ShowAlert(RegisterActivity.this, getString(R.string.ValidEmail));
                    showPopUp("Invalid Email ID", "Please enter valid Email ID", "Retry");
                } else if (TextUtils.isEmpty(register_et_address.getText().toString())) {
                    //GeneralUtils.ShowAlert(RegisterActivity.this, "Enter Valid Address");
                    showPopUp("Address Required", "Address field cannot be left empty", "OK");

                } else if (mobile.getText().length() < 1) {
                    showPopUp("Mobile Number Required", "Mobile number field cannot be left empty", "OK");
                } else if (pass.getText().toString().length() < 1) {

                    //GeneralUtils.ShowAlert(RegisterActivity.this,getString(R.string.ValidPassword));
                    showPopUp("Password Required", "Password field cannot be left empty", "OK");
                } else {
                    //GeneralUtils.ShowAlert(RegisterActivity.this, getString(R.string.MatchPassword));
                    showPopUp("Password Mismatch", "Passwords entered do not match", "Retry");

                }
                break;
            case R.id.register_root:
                inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                break;
            case R.id.register_logo:
                inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                break;

        }
    }

    void Register(String fname, String lname, String mail, String phone, String pass, String address, String refferal) {


        GenericRequest<InsertSignUPResponse> request = new GenericRequest<InsertSignUPResponse>(Request.Method.POST, CONSTANTS.URL_INSERT_SIGNUP, InsertSignUPResponse.class,
                new RegisterRequest(mail, pass, fname, lname, "", "", "", "1", storage.loadDeviceToken(), "0", phone, address, refferal)
                , new Response.Listener<InsertSignUPResponse>() {
            @Override
            public void onResponse(InsertSignUPResponse response) {
                progressDialog.dismiss();
                result = response;
                //Toast.makeText(getApplicationContext(),result.getResponse().get(0).getStatus(),Toast.LENGTH_SHORT).show();
                if (Boolean.valueOf(response.getResponse().get(0).getStatus())) {

                    if (response.getResponse().get(0).getPayloads().get(0).getEmail().equals("Record Already Exists")) {
                        // GeneralUtils.ShowAlert(RegisterActivity.this, "Entered email-ID already exists ");
                        showPopUp("Email ID Already Exists", "Entered Email ID already exists. Try Forgot Password to recover your password.", "OK");
                    } else {
                        storage.storeEmail(result.getResponse().get(0).getPayloads().get(0).getEmail());
                        storage.storeFirstName(result.getResponse().get(0).getPayloads().get(0).getFirstName());
//                        storage.storeLastName(result.getResponse().get(0).getPayloads().get(0).getLastName());
                        storage.storeID(result.getResponse().get(0).getPayloads().get(0).getId());
                        storage.isLogIn(1);
                        Intent i = new Intent(RegisterActivity.this, GetStartedActivity.class);
                        startActivity(i);
                    }
                } else {
                    GeneralUtils.ShowAlert(RegisterActivity.this, response.getResponse().get(0).getMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                GeneralUtils.ShowAlert(RegisterActivity.this, getString(R.string.VolleyTimeout));

            }
        });

        AppController.getInstance().addToRequestQueue(request);
        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

//    @Override
//    public void onBackPressed() {
//
//        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
//        startActivity(i);
//
//    }


    void VerifieReferralCode(final String refferalCode) {
        progressDialog.setMessage("Validating Refferal Code...");
        progressDialog.show();

        GenericRequest<RefferalValidationResponse> request = new GenericRequest<RefferalValidationResponse>(Request.Method.POST, CONSTANTS.URL_FOR_REFFERAL_VALIDATION, RefferalValidationResponse.class,

                new RefferalValidationRequest(refferalCode), new Response.Listener<RefferalValidationResponse>() {
            @Override
            public void onResponse(RefferalValidationResponse response) {
                progressDialog.dismiss();

                if (Boolean.valueOf(response.getResponse().get(0).getStatus())) {
                    if (Boolean.valueOf(response.getResponse().get(0).getPayload().get(0).getStatus())) {
                        //  GeneralUtils.ShowAlert(RegisterActivity.this,"Refferal Code match Success.");
                    } else {
                        GeneralUtils.ShowAlert(RegisterActivity.this, "Enter Valid Refferal Code.");
                    }

                } else {
                    GeneralUtils.ShowAlert(RegisterActivity.this, "Something Went Wrong.");
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                GeneralUtils.ShowAlert(RegisterActivity.this, getString(R.string.VolleyTimeout));

            }
        });
        AppController.getInstance().addToRequestQueue(request);

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void showPopUp(String title, String body, String yes) {
        final Dialog dialog = new Dialog(RegisterActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_incorrect_credentials);
        TextView titleTv = (TextView) dialog.findViewById(R.id.titleTv);
        titleTv.setText(title);
        TextView bodyTv = (TextView) dialog.findViewById(R.id.bodyTv);
        bodyTv.setText(body);
        TextView yesTv = (TextView) dialog.findViewById(R.id.yes);
        yesTv.setText(yes);
        yesTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

}
