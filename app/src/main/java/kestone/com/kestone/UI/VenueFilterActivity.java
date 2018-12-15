package kestone.com.kestone.UI;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

import kestone.com.kestone.Fragments.DesignFragment;
import kestone.com.kestone.Fragments.MoreFragment;
import kestone.com.kestone.Fragments.SetupVenueFragment;
import kestone.com.kestone.Fragments.VenueFilterFragment;
import kestone.com.kestone.Fragments.VenueFragment;
import kestone.com.kestone.MODEL.Theme.RESPONSE.ThemeResponse;
import kestone.com.kestone.R;
import kestone.com.kestone.Utilities.CONSTANTS;
import kestone.com.kestone.Utilities.StorageUtilities;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class VenueFilterActivity extends AppCompatActivity implements Serializable {


    StorageUtilities storage;

    public static int index;
    public static int hallIndex;

    Dialog dialog;

    ImageView c1, c2, c3, c4, t1, t2, t3, t4;
    TextView activityTitle;
    BroadcastReceiver updateUI = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getIntExtra("frag", 10)) {
                case 1:
                    c1.setImageResource(R.drawable.circle_red);
                    c2.setImageResource(R.drawable.circle_white);
                    c3.setImageResource(R.drawable.circle_white);
                    c4.setImageResource(R.drawable.circle_white);

                    t1.setVisibility(View.INVISIBLE);
                    t2.setVisibility(View.INVISIBLE);
                    t3.setVisibility(View.INVISIBLE);
                    t4.setVisibility(View.INVISIBLE);

                    activityTitle.setText("Select Venue");

                    break;
                case 2:
                    c1.setImageResource(R.drawable.circle_white);
                    c2.setImageResource(R.drawable.circle_red);
                    c3.setImageResource(R.drawable.circle_white);
                    c4.setImageResource(R.drawable.circle_white);

                    t1.setVisibility(View.VISIBLE);
                    t2.setVisibility(View.INVISIBLE);
                    t3.setVisibility(View.INVISIBLE);
                    t4.setVisibility(View.INVISIBLE);
                    activityTitle.setText("Select Set-Up");
                    break;
                case 3:
                    c1.setImageResource(R.drawable.circle_white);
                    c2.setImageResource(R.drawable.circle_white);
                    c3.setImageResource(R.drawable.circle_red);
                    c4.setImageResource(R.drawable.circle_white);

                    t1.setVisibility(View.VISIBLE);
                    t2.setVisibility(View.VISIBLE);
                    t3.setVisibility(View.INVISIBLE);
                    t4.setVisibility(View.INVISIBLE);

                    activityTitle.setText("Select Design");
                    break;
                case 4:
                    c1.setImageResource(R.drawable.circle_white);
                    c2.setImageResource(R.drawable.circle_white);
                    c3.setImageResource(R.drawable.circle_white);
                    c4.setImageResource(R.drawable.circle_red);

                    t1.setVisibility(View.VISIBLE);
                    t2.setVisibility(View.VISIBLE);
                    t3.setVisibility(View.VISIBLE);
                    t4.setVisibility(View.INVISIBLE);

                    activityTitle.setText("Select Artists and More");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_filter);
        activityTitle = (TextView) findViewById(R.id.activityTitle);
        dialog = new Dialog(VenueFilterActivity.this);
        LocalBroadcastManager.getInstance(this).registerReceiver(updateUI, new IntentFilter("updateUI"));
        c1 = (ImageView) findViewById(R.id.c1);
        c2 = (ImageView) findViewById(R.id.c2);
        c3 = (ImageView) findViewById(R.id.c3);
        c4 = (ImageView) findViewById(R.id.c4);
        t1 = (ImageView) findViewById(R.id.s1);
        t2 = (ImageView) findViewById(R.id.s2);
        t3 = (ImageView) findViewById(R.id.s3);
        t4 = (ImageView) findViewById(R.id.s4);
        // StatusBarCompat.translucentStatusBar(VenueFilterActivity.this,true);
        if (getIntent() != null) {
            // Toast.makeText(getApplicationContext(),"Setup",Toast.LENGTH_SHORT).show();
            if (getIntent().getIntExtra(CONSTANTS.VENUESETUP_TAG, 0) == CONSTANTS.VENUESETUP_VALUE) {
                LoadSetupFragment();
            } else if (getIntent().getIntExtra(CONSTANTS.VENUEDESIGN_TAG, 0) == CONSTANTS.VENUEDESIGN_VALUE) {
                //   Toast.makeText(getApplicationContext(),"FilterIF",Toast.LENGTH_SHORT).show();
                LoadDesignFragment();
            } else if (getIntent().getIntExtra(CONSTANTS.VENUEMORE_TAG, 0) == CONSTANTS.VENUEMORE_VALUE) {
                LoadMoreFragment();
            } else {
                LoadFilterFragment();
            }
        } else {
            //Toast.makeText(getApplicationContext(),"Filter",Toast.LENGTH_SHORT).show();
            LoadFilterFragment();
        }
        storage = new StorageUtilities(getApplicationContext());


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CONSTANTS.RESULTCODE_COMPARE) {
            if (data.getBooleanExtra("hasCompare", false)) {
                index = data.getIntExtra("compare", 0);
                hallIndex = data.getIntExtra("compareHall", 0);
                FragmentManager fm = getSupportFragmentManager();
                VenueFragment fragment = (VenueFragment) fm.findFragmentByTag(CONSTANTS.FRAGMENT_VENUE);
                if (fragment != null)
                    fragment.ScrollToPosition(hallIndex, index);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    void LoadSetupFragment() {
        Bundle bundle = new Bundle();
        if (getIntent() != null)
            bundle.putSerializable("data", getIntent().getSerializableExtra("data"));
        SetupVenueFragment setupVenueFragment = new SetupVenueFragment();
        setupVenueFragment.setArguments(bundle);
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.venue_filter_placeholder, setupVenueFragment, CONSTANTS.FRAGMENT_FILTERS);
        ft.commit();
    }

    void LoadFilterFragment() {
        VenueFilterFragment fragment = new VenueFilterFragment();
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.venue_filter_placeholder, fragment, CONSTANTS.FRAGMENT_FILTERS);
        ft.commit();
    }

    void LoadDesignFragment() {
        DesignFragment fragment = new DesignFragment();
        Bundle bundle = new Bundle();

        if (getIntent() != null)
            bundle.putSerializable("data", (ThemeResponse)getIntent().getSerializableExtra("data"));
        fragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.replace(R.id.venue_filter_placeholder, fragment, CONSTANTS.FRAGMENT_DESIGN);
        ft.commit();
    }

    void LoadMoreFragment() {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.replace(R.id.venue_filter_placeholder, new MoreFragment(), CONSTANTS.FRAGMENT_MORE);
        getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        ft.commit();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}
