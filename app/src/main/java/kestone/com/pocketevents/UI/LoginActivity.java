package kestone.com.pocketevents.UI;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import kestone.com.pocketevents.MODEL.Login.REQUEST.LoginRequest;
import kestone.com.pocketevents.MODEL.Login.RESPONSE.LoginResponse;
import kestone.com.pocketevents.R;
import kestone.com.pocketevents.Utilities.CONSTANTS;
import kestone.com.pocketevents.Utilities.GeneralUtils;
import kestone.com.pocketevents.Utilities.GenericRequest;
import kestone.com.pocketevents.Utilities.PrefEntities;
import kestone.com.pocketevents.Utilities.StorageUtilities;
import qiu.niorgai.StatusBarCompat;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    TextView login, signUp;
    AppCompatEditText userName, password;

    String user, pass;

    StorageUtilities storage;

    ImageView logo;

    LinearLayout root;

    LoginResponse result;

    ProgressDialog progressDialog;

    TextView forgotPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // StatusBarCompat.translucentStatusBar(LoginActivity.this,true);
        signUp = (TextView) findViewById(R.id.login_btn_signUp);
        login = (TextView) findViewById(R.id.login_btn_login);
        userName = (AppCompatEditText) findViewById(R.id.login_et_usermane);
        password = (AppCompatEditText) findViewById(R.id.login_et_password);
        password.setTransformationMethod(new PasswordTransformationMethod());

        root = (LinearLayout) findViewById(R.id.loginRoot);
        logo = (ImageView) findViewById(R.id.login_logo);
        forgotPass = (TextView) findViewById(R.id.login_forgotPassword);
        signUp.setOnClickListener(this);
        login.setOnClickListener(this);
        storage = new StorageUtilities(getApplicationContext());
        forgotPass.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.CheckCredentials));
        if (storage.isLogged() == 1) {
            Intent intent = new Intent(LoginActivity.this, CitySearchActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }


    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.login_btn_signUp:
                i = new Intent(LoginActivity.this, RegisterActivity.class);
                if (GeneralUtils.hasLollipop()) {
                    String transitionName = getString(R.string.logoAnimation);
                    View sharedView = logo;
                    startActivity(i);
                } else {
                    startActivity(i);
                }
                break;
            case R.id.login_btn_login:
                user = userName.getText().toString().trim();
                pass = password.getText().toString();
                if (GeneralUtils.isEmailValid(user)) {
                    if (GeneralUtils.isNetworkAvailable(LoginActivity.this)) {
                        if (pass.length() == 0) {
                            //GeneralUtils.ShowAlert(LoginActivity.this,"Enter Valid Password Id");
//                            Snackbar snackbar = Snackbar.make(root, "Enter Valid Password ", 3000);
//                            snackbar.show();
                            showPopUp("Password Required", "Please enter your Password");

                        } else if (pass.length() <= 3) {
                            //GeneralUtils.ShowAlert(LoginActivity.this,"Enter Valid Password Id");
//                            Snackbar snackbar = Snackbar.make(root, "Enter Valid Password ", 3000);
//                            snackbar.show();
                            showPopUp("Invalid Password", "Please enter valid Password");

                        } else {
                            Login(user, pass, "123Z");
                        }

                    } else {
                        GeneralUtils.displayNetworkAlert(LoginActivity.this, false);
                    }
                } else if (user.length() == 0) {
                    // GeneralUtils.ShowAlert(LoginActivity.this,"Enter Valid Email Id");
//                    Snackbar snackbar = Snackbar.make(root, "Enter Valid Email Id", 3000);
//                    snackbar.show();
                    showPopUp("Email ID Required", "Please enter registered Email ID");
                } else {
                    // GeneralUtils.ShowAlert(LoginActivity.this,"Enter Valid Email Id");
//                    Snackbar snackbar = Snackbar.make(root, "Enter Valid Email Id", 3000);
//                    snackbar.show();
                    showInvalidPopUp("Invalid Email ID", "Please enter valid Email ID");

                }

                break;
            case R.id.login_forgotPassword:
                i = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(i);
                break;
        }
    }


    public void Login(String user, String password, String token) {
        progressDialog.show();

        final GenericRequest<LoginResponse> request = new GenericRequest<LoginResponse>(Request.Method.POST, CONSTANTS.URL_LOGIN, LoginResponse.class, new LoginRequest(user, password, token, "0"), new Response.Listener<LoginResponse>() {
            @Override
            public void onResponse(LoginResponse response) {
                result = response;
                progressDialog.dismiss();
                if (Boolean.valueOf(result.getResponse().get(0).getStatus())) {
                    if (result.getResponse().get(0).getCode().equals("OK")) {
                        if (result.getResponse().get(0).getPayloads().size() > 0) {
                            storage.storeEmail(result.getResponse().get(0).getPayloads().get(0).getEmail());
                            storage.storeFirstName(result.getResponse().get(0).getPayloads().get(0).getFirstName());
//                            storage.storeLastName(result.getResponse().get(0).getPayloads().get(0).getLastName());
                            storage.storeID(result.getResponse().get(0).getPayloads().get(0).getId());
                            storage.storeProfilePic(result.getResponse().get(0).getPayloads().get(0).getProfilePic());
                            storage.isLogIn(1);
                            storage.storePhone(result.getResponse().get(0).getPayloads().get(0).getMobile());

                            storage.StoreKey(PrefEntities.COMPANY, result.getResponse().get(0).getPayloads().get(0).getOrganisation());
                            storage.StoreKey(PrefEntities.DESIGNATION, result.getResponse().get(0).getPayloads().get(0).getDesignation());
                            storage.StoreKey(PrefEntities.ADDRESSS, result.getResponse().get(0).getPayloads().get(0).getAddress());


                            Intent i = new Intent(LoginActivity.this, CitySearchActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);

                        } else {
                            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
//                            Snackbar snackbar = Snackbar
//                                    .make(root, getString(R.string.InvalidCredentials), 3000);
                            showPopUp("Invalid Credentials", "Please enter valid credentials");

                            //snackbar.show();
                            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                            // Vibrate for 500 milliseconds
                            v.vibrate(150);
                            Animation shake = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.shake);
                            root.setAnimation(shake);
                        }

                    } else {
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
//                        Snackbar snackbar = Snackbar
//                                .make(root, getString(R.string.InvalidCredentials), 3000);
//
//                        snackbar.show();
                        showPopUp("Invalid Credentials", "Please enter valid credentials");

                        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        // Vibrate for 500 milliseconds
                        v.vibrate(150);
                        Animation shake = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.shake);
                        root.setAnimation(shake);
                    }
                } else {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
//                    Snackbar snackbar = Snackbar
//                            .make(root, getString(R.string.InvalidCredentials), 3000);
//
//                    snackbar.show();
                    showPopUp("Invalid Credentials", "Please enter valid credentials");

                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    // Vibrate for 500 milliseconds
                    v.vibrate(150);
                    Animation shake = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.shake);
                    root.setAnimation(shake);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                GeneralUtils.ShowAlert(LoginActivity.this, getString(R.string.VolleyTimeout));
                progressDialog.dismiss();

            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void showPopUp(String title, String body) {
        final Dialog dialog = new Dialog(LoginActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_incorrect_credentials);
        TextView titleTv = (TextView) dialog.findViewById(R.id.titleTv);
        titleTv.setText(title);
        TextView bodyTv = (TextView) dialog.findViewById(R.id.bodyTv);
        bodyTv.setText(body);
        TextView yesTv = (TextView) dialog.findViewById(R.id.yes);
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

    public void showInvalidPopUp(String title, String body) {
        final Dialog dialog = new Dialog(LoginActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_incorrect_credentials);
        TextView titleTv = (TextView) dialog.findViewById(R.id.titleTv);
        titleTv.setText(title);
        TextView bodyTv = (TextView) dialog.findViewById(R.id.bodyTv);
        bodyTv.setText(body);
        TextView yesTv = (TextView) dialog.findViewById(R.id.yes);
        yesTv.setText("Retry");
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
