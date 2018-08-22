package kestone.com.kestone.UI;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import kestone.com.kestone.MODEL.ChangePassword.REQUEST.ChangePasswordRequst;
import kestone.com.kestone.MODEL.ChangePassword.RESPONSE.ChangePasswordResponse;
import kestone.com.kestone.MODEL.Logout.REQUEST.LogoutRequest;
import kestone.com.kestone.MODEL.Logout.RESPONSE.LogoutResponse;
import kestone.com.kestone.R;
import kestone.com.kestone.Utilities.CONSTANTS;
import kestone.com.kestone.Utilities.GeneralUtils;
import kestone.com.kestone.Utilities.GenericRequest;
import kestone.com.kestone.Utilities.StorageUtilities;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ChangePasswrodActivity extends AppCompatActivity {

    AppCompatEditText newPassword,confirm_password,oldPassword;
    TextView changePassword;
    ProgressDialog progressDialog;
    StorageUtilities storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.PleaseWait));

        newPassword=(AppCompatEditText)findViewById(R.id.newPassword);
        newPassword.setTransformationMethod(new PasswordTransformationMethod());
        confirm_password=(AppCompatEditText)findViewById(R.id.confirm_password);
        confirm_password.setTransformationMethod(new PasswordTransformationMethod());
        oldPassword=(AppCompatEditText)findViewById(R.id.oldPassword);
        oldPassword.setTransformationMethod(new PasswordTransformationMethod());
        storage = new StorageUtilities(ChangePasswrodActivity.this);


        changePassword=(TextView)findViewById(R.id.changePassword);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });

    }



    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }
    public void validation() {

        String newPass=newPassword.getText().toString();
        String conPass=confirm_password.getText().toString();
        String oldPass=oldPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(oldPass) || !isPasswordValid(oldPass)) {
            GeneralUtils.CustomDialog(ChangePasswrodActivity.this,"","Enter valid old password");
            focusView = oldPassword;
            cancel = true;
        }

        else if (TextUtils.isEmpty(newPass) || !isPasswordValid(newPass)) {
            CustomDialog2(ChangePasswrodActivity.this,"New Password Required","Please enter New Password.","Retry");
            focusView = newPassword;
            cancel = true;
        }

        else if (TextUtils.isEmpty(conPass) || !isPasswordValid(conPass)) {
           GeneralUtils.CustomDialog(ChangePasswrodActivity.this,"","Enter Confirm Password");
            focusView = confirm_password;
            cancel = true;
        }
        else if (!(newPass.equals(conPass))){
            CustomAlert("Password Mismatch","Passwords entered do not match. Please retry.");
            focusView = confirm_password;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            focusView.requestFocus();
        }
        else {

            if (GeneralUtils.isNetworkAvailable(ChangePasswrodActivity.this)){

               ChangePasswordService(storage.loadID(),oldPass,conPass);

            }
            else {
                GeneralUtils.displayNetworkAlert(ChangePasswrodActivity.this,false);
            }
        }

    }




    void ChangePasswordService(String UserId,String OldPassword,String NewPassword) {
        progressDialog.show();
        GenericRequest<ChangePasswordResponse> request = new GenericRequest<ChangePasswordResponse>(Request.Method.POST, CONSTANTS.URL_FOR_CHANGEPASSWORD, ChangePasswordResponse.class,
                new ChangePasswordRequst(UserId,OldPassword,NewPassword),
                new Response.Listener<ChangePasswordResponse>() {
                    @Override
                    public void onResponse(ChangePasswordResponse response) {
                        progressDialog.dismiss();
                        if (Boolean.valueOf(response.getResponse().get(0).getStatus())) {
                            //GeneralUtils.ShowAlert(ChangePasswrodActivity.this, "Your Password has been changed successfuly", "Password Changed");

                            final Dialog successDialog = new Dialog(ChangePasswrodActivity.this);
                            successDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            successDialog.setCancelable(false);
                            successDialog.setContentView(R.layout.dialog_password_change_success);
                            TextView messageTitle=(TextView)successDialog.findViewById(R.id.messageTitle);
                            messageTitle.setText("Password Changed Successfully");
                            TextView messageBody=(TextView)successDialog.findViewById(R.id.messageBody);
                            messageBody.setText("Your password has been changed successfully.");
                            TextView gotIt = (TextView) successDialog.findViewById(R.id.button_ok);
                            gotIt.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    successDialog.dismiss();
                                    startActivity(new Intent(ChangePasswrodActivity.this,CitySearchActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));
                                }
                            });
                            successDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            successDialog.show();

                        } else {

                            GeneralUtils.ShowAlert(ChangePasswrodActivity.this, getResources().getString(R.string.SomethingWentWrong));
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                GeneralUtils.ShowAlert(ChangePasswrodActivity.this, getResources().getString(R.string.VolleyTimeout));

            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void CustomAlert(String TitleMessage,String BodyMessage){

        final Dialog successDialog = new Dialog(ChangePasswrodActivity.this);
        successDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        successDialog.setCancelable(true);
        successDialog.setContentView(R.layout.dialog_general_utils_custom);
        TextView messageTitle=(TextView)successDialog.findViewById(R.id.messageTitle);
        TextView messageBody=(TextView)successDialog.findViewById(R.id.messageBody);
        if (TitleMessage.equals("")||TitleMessage.equals(null)){
            messageTitle.setText(R.string.app_name);
        }
        else {
            messageTitle.setText(TitleMessage);
        }
        if (BodyMessage.equals("")||BodyMessage.equals(null)){
            messageBody.setText("Something doesn't see right.");
        }
        else {
            messageBody.setText(BodyMessage);
        }


        TextView gotIt = (TextView) successDialog.findViewById(R.id.button_ok);
        gotIt.setText("Retry");
        gotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                successDialog.dismiss();

            }
        });
        successDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        successDialog.show();

    }

    public void CustomDialog2(Context context,String TitleMessage,String BodyMessage, String str){

        final Dialog successDialog = new Dialog(context);
        successDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        successDialog.setCancelable(true);
        successDialog.setContentView(R.layout.dialog_general_utils_custom);
        TextView messageTitle=(TextView)successDialog.findViewById(R.id.messageTitle);
        TextView messageBody=(TextView)successDialog.findViewById(R.id.messageBody);
        TextView gotIt = (TextView) successDialog.findViewById(R.id.button_ok);
        if (TitleMessage.equals("")||TitleMessage.equals(null)){
            messageTitle.setText(R.string.app_name);
        }
        else {
            messageTitle.setText(TitleMessage);
        }
        if (str.equals("")||str.equals(null)){
            gotIt.setText("Ok");
        }
        else {
            gotIt.setText(str);
        }

        if (BodyMessage.equals("")||BodyMessage.equals(null)){
            messageBody.setText("Something doesn't see right.");
        }
        else {
            messageBody.setText(BodyMessage);
        }

        gotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                successDialog.dismiss();
            }
        });
        successDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        successDialog.show();

    }

}
