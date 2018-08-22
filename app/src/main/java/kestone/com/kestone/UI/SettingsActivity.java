package kestone.com.kestone.UI;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import kestone.com.kestone.R;
import kestone.com.kestone.Utilities.StorageUtilities;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
/**
 * Created by Xyz on 1/29/2018.
 */
public class SettingsActivity  extends AppCompatActivity implements View.OnClickListener {
    StorageUtilities storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        TextView termCondition= (TextView) findViewById(R.id.termsConditions);
        TextView privacyPolicy= (TextView) findViewById(R.id.privacyPolicy);
        TextView changePassword= (TextView) findViewById(R.id.changePassword);
        termCondition.setOnClickListener(this);
        privacyPolicy.setOnClickListener(this);
        changePassword.setOnClickListener(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.termsConditions:
                Intent intent=new Intent(SettingsActivity.this,TermsAndConditions.class);
                startActivity(intent);
                break;
                case R.id.privacyPolicy:
                    Intent intent2=new Intent(SettingsActivity.this,PrivacyAndPolicy.class);
                    startActivity(intent2);
                break;
                case R.id.changePassword:
                    Intent intent3=new Intent(SettingsActivity.this,ChangePasswrodActivity.class);
                    startActivity(intent3);
                break;


        }

    }

}
