package kestone.com.pocketevents.UI;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import kestone.com.pocketevents.MODEL.ForgotPassword.REQUEST.ForegotPasswordRequest;
import kestone.com.pocketevents.MODEL.ForgotPassword.RESPONSE.ForgotPasswordResponse;
import kestone.com.pocketevents.R;
import kestone.com.pocketevents.Utilities.CONSTANTS;
import kestone.com.pocketevents.Utilities.GeneralUtils;
import kestone.com.pocketevents.Utilities.GenericRequest;
import qiu.niorgai.StatusBarCompat;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class ForgotPasswordActivity extends AppCompatActivity {

    AppCompatEditText emailTextField;
    TextView submit;

    LinearLayout root;
    TextView root1, root2;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        // StatusBarCompat.translucentStatusBar(ForgotPasswordActivity.this,true);
        progressDialog = new ProgressDialog(ForgotPasswordActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        emailTextField = (AppCompatEditText) findViewById(R.id.forgotPass_et);
        submit = (TextView) findViewById(R.id.forgotPasswordSubmit);
        root = (LinearLayout) findViewById(R.id.forgotPasswordRoot);
        root1 = (TextView) findViewById(R.id.forgotPassroot2);
        root2 = (TextView) findViewById(R.id.forgotPassroot3);

        root2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });

        root1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });

        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);


                if (GeneralUtils.isEmailValid(emailTextField.getText().toString())) {
                    if (GeneralUtils.isNetworkAvailable(ForgotPasswordActivity.this)) {

                        ForgotPassword(emailTextField.getText().toString());
                        progressDialog.show();
                    } else {
                        GeneralUtils.displayNetworkAlert(ForgotPasswordActivity.this, false);
                    }


                } else {
                    GeneralUtils.ShowAlert(ForgotPasswordActivity.this, getResources().getString(R.string.ValidEmail));
                }
            }
        });

    }


    void ForgotPassword(String email) {
        GenericRequest<ForgotPasswordResponse> request = new GenericRequest<ForgotPasswordResponse>(Request.Method.POST, CONSTANTS.URL_FORGOT_PASSWORD, ForgotPasswordResponse.class, new ForegotPasswordRequest(email),
                new Response.Listener<ForgotPasswordResponse>() {
                    @Override
                    public void onResponse(ForgotPasswordResponse response) {
                        progressDialog.dismiss();
                        if (Boolean.valueOf(response.getResponse().get(0).getStatus())) {
                            final Dialog successDialog = new Dialog(ForgotPasswordActivity.this);
                            successDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            successDialog.setCancelable(true);
                            successDialog.setContentView(R.layout.dialogue_forget_password);
                            TextView gotIt = (TextView) successDialog.findViewById(R.id.eventSaved);
                            gotIt.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    emailTextField.setText("");
                                    Intent iq = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                                    iq.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(iq);
                                    finish();
                                }
                            });
                            successDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            successDialog.show();
                            //  GeneralUtils.ShowAlert(ForgotPasswordActivity.this,response.getResponse().get(0).getPayload().get(0).getActive());
                        } else {
                            GeneralUtils.ShowAlert(ForgotPasswordActivity.this, response.getResponse().get(0).getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                GeneralUtils.ShowAlert(ForgotPasswordActivity.this, getResources().getString(R.string.VolleyTimeout));
            }
        });
        AppController.getInstance().addToRequestQueue(request);
        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
