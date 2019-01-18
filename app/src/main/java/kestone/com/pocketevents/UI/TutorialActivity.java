package kestone.com.pocketevents.UI;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rd.PageIndicatorView;

import java.util.Timer;
import java.util.TimerTask;

import kestone.com.pocketevents.Adapters.TutorialActivity.TutorialPagerAdapter;
import kestone.com.pocketevents.R;
import kestone.com.pocketevents.Utilities.GeneralUtils;
import kestone.com.pocketevents.Utilities.StorageUtilities;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TutorialActivity extends AppCompatActivity implements View.OnClickListener
{
    ViewPager viewPager;
    TextView login, signUp;
    TutorialPagerAdapter tutorialPagerAdapter;
    Timer timer;
    ImageView logo;
    PageIndicatorView circleIndicator;
    StorageUtilities storage;

    int slider [] = {R.drawable.search_event_venue, R.drawable.configure,R.drawable.design_event_theme};
    String sliderText [] = {"Detailed information from over 10,000\n" + "event venues across India", "Guided control over technical set-up including stage,\n" + "lights,sound and more","Pre-Designed themes and communication templates available for download"};
    String sliderTextTitle [] = {"Search Event Venue", "Configure Events Like a Pro","Design Event Theme at Ease"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
      //  StatusBarCompat.translucentStatusBar(TutorialActivity.this,true);
        storage = new StorageUtilities(getApplicationContext());
        if (storage.isLogged()==1){
            Intent intent = new Intent(TutorialActivity.this, CitySearchActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        viewPager = (ViewPager)findViewById(R.id.tutorial_viewPager);
        login = (TextView) findViewById(R.id.tutorial_btn_login);
        signUp = (TextView) findViewById(R.id.tutorial_btn_signUp);
        logo = (ImageView)findViewById(R.id.tutorial_logo);
        login.setOnClickListener(this);
        signUp.setOnClickListener(this);

        circleIndicator = (PageIndicatorView) findViewById(R.id.indicator);
        tutorialPagerAdapter = new TutorialPagerAdapter(TutorialActivity.this,slider, sliderText, sliderTextTitle);
        viewPager.setAdapter(tutorialPagerAdapter);
        circleIndicator.setViewPager(viewPager);
        timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTask() , 5000, 10000);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //timer.scheduleAtFixedRate(new SliderTask() , 2000, 4000);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    @Override
    public void onClick(View view) {
        Intent i ;
        switch (view.getId()){
            case R.id.tutorial_btn_login :
                i = new Intent(TutorialActivity.this, LoginActivity.class);
                if (GeneralUtils.hasLollipop()){
                    String transitionName = getString(R.string.logoAnimation);
                    View sharedView = logo;
                    ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(TutorialActivity.this, sharedView, transitionName);
                    startActivity(i);
                }else {
                    startActivity(i);
                }
                break;
            case R.id.tutorial_btn_signUp :
                i = new Intent(TutorialActivity.this, RegisterActivity.class);
                if (GeneralUtils.hasLollipop()){
                    String transitionName = getString(R.string.logoAnimation);
                    View sharedView = logo;
                    ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(TutorialActivity.this, sharedView, transitionName);
                    startActivity(i);
                }else {
                    startActivity(i);
                }
                break;
        }
    }

    public class SliderTask extends TimerTask {

        @Override
        public void run() {
            TutorialActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() == 0){
                        viewPager.setCurrentItem(1);
                        circleIndicator.setSelection(1);
                    }
                    else if (viewPager.getCurrentItem() == 1){
                        viewPager.setCurrentItem(2);
                        circleIndicator.setSelection(2);
                    }
                    else if (viewPager.getCurrentItem()==2){
                        viewPager.setCurrentItem(0);
                        circleIndicator.setSelection(0);
                    }


                }
            });
        }
    }



    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }



}
