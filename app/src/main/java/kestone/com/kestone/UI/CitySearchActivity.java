package kestone.com.kestone.UI;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import kestone.com.kestone.Adapters.CitySearchActivity.CitySearchAutoCompleteAdapter;
import kestone.com.kestone.MODEL.CitySearch.RESPONSE.City;
import kestone.com.kestone.MODEL.Consulting.ConsultingResponse;
import kestone.com.kestone.MODEL.ContactUs.ContactUsResponse;
import kestone.com.kestone.MODEL.Filters.REQUEST.FilterRequest;
import kestone.com.kestone.MODEL.Filters.RESPONSE.GetVenueFiltersResponse;
import kestone.com.kestone.MODEL.Filters.RESPONSE.Payload;
import kestone.com.kestone.MODEL.GetSavedVenue.REQUEST.GetSavedVenueRequest;
import kestone.com.kestone.MODEL.GetSavedVenue.RESPONSE.GetSavedEventListResponse;
import kestone.com.kestone.MODEL.Login.REQUEST.LoginRequest;
import kestone.com.kestone.MODEL.Login.RESPONSE.LoginResponse;
import kestone.com.kestone.MODEL.Logout.REQUEST.LogoutRequest;
import kestone.com.kestone.MODEL.Logout.RESPONSE.LogoutResponse;
import kestone.com.kestone.MODEL.Manager.MySingleton;
import kestone.com.kestone.MODEL.MyOrders.REQUEST.MyOrdersRequest;
import kestone.com.kestone.MODEL.MyOrders.RESPONSE.MyOrdersResponse;
import kestone.com.kestone.MODEL.RewardsReferrals.REQUEST.ReferralsRewardRequest;
import kestone.com.kestone.MODEL.RewardsReferrals.RESPONSE.RewardsReferralsResponse;
import kestone.com.kestone.R;
import kestone.com.kestone.Utilities.CONSTANTS;
import kestone.com.kestone.Utilities.DelayAutoCompleteTextView;
import kestone.com.kestone.Utilities.GeneralUtils;
import kestone.com.kestone.Utilities.GenericRequest;
import kestone.com.kestone.Utilities.PrefEntities;
import kestone.com.kestone.Utilities.StorageUtilities;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CitySearchActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    TextView allEvents;
    DelayAutoCompleteTextView enterCity;
    TextView proceed;
    RelativeLayout root;

    StorageUtilities storage;
    CitySearchAutoCompleteAdapter adapter;

    String cityID = "9999";


    List<Payload> p1 = new ArrayList<>();
    List<Payload> p2 = new ArrayList<>();

    ProgressDialog progressDialog;

    NavigationView navigationView;

    TextView nav_Name, nav_Email;
    LinearLayout nav_home, nav_Myevents, nav_Conslting, nav_MyOrders, nav_support, rewards_referral, nav_change, nav_terms, nav_privacy, nav_logOut;

    CircleImageView nav_Profile;
    ImageView edit_icon;
    RelativeLayout topRll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_search);
        storage = new StorageUtilities(CitySearchActivity.this);
        //StatusBarCompat.translucentStatusBar(CitySearchActivity.this, true);
        Toolbar toolbar = (Toolbar) findViewById(R.id.ToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        cityID = "9999";
        CleanStorage();


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.PleaseWait));


        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        adapter = new CitySearchAutoCompleteAdapter(CitySearchActivity.this);


        if (!GeneralUtils.hasLollipop()) {
            TextView button = (TextView) findViewById(R.id.hamburger);
            toolbar.setVisibility(View.GONE);
            button.setVisibility(View.VISIBLE);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (drawer.isDrawerOpen(Gravity.LEFT)) {
                        drawer.closeDrawer(Gravity.LEFT);
                    } else {
                        drawer.openDrawer(Gravity.LEFT);
                    }
                }
            });
            toolbar.setNavigationIcon((Drawable) null);
        }

        //Find Views
        allEvents = (TextView) findViewById(R.id.citySearch_btn_allEvents);
        allEvents.setOnClickListener(this);

        nav_Name = (TextView) findViewById(R.id.nav_name);
        nav_Email = (TextView) findViewById(R.id.nav_email);
        nav_Profile = (CircleImageView) findViewById(R.id.nav_profileImage);
        // nav_view = (LinearLayout) findViewById(R.id.nav_view);
        edit_icon = (ImageView) findViewById(R.id.edit_icon);
        topRll = (RelativeLayout) findViewById(R.id.topRll);

        enterCity = (DelayAutoCompleteTextView) findViewById(R.id.citySearch_et_enterCity);
        proceed = (TextView) findViewById(R.id.citySearch_btn_proceed);
        root = (RelativeLayout) findViewById(R.id.citySearchRoot);
        enterCity.setAdapter(adapter);
        enterCity.setThreshold(0);
        enterCity.setAutoCompleteDelay(30);

        allEvents.setOnClickListener(this);
        proceed.setOnClickListener(this);

        nav_home = (LinearLayout) findViewById(R.id.nav_home);
        nav_home.setOnClickListener(this);
        nav_Myevents = (LinearLayout) findViewById(R.id.nav_Myevents);
        nav_Myevents.setOnClickListener(this);
        nav_Conslting = (LinearLayout) findViewById(R.id.nav_Conslting);
        nav_Conslting.setOnClickListener(this);
        nav_MyOrders = (LinearLayout) findViewById(R.id.nav_MyOrders);
        nav_MyOrders.setOnClickListener(this);
        nav_support = (LinearLayout) findViewById(R.id.nav_support);
        nav_support.setOnClickListener(this);
        rewards_referral = (LinearLayout) findViewById(R.id.rewards_referral);
        rewards_referral.setOnClickListener(this);
        nav_change = (LinearLayout) findViewById(R.id.nav_change);
        nav_change.setOnClickListener(this);
        nav_terms = (LinearLayout) findViewById(R.id.nav_terms);
        nav_terms.setOnClickListener(this);
        nav_privacy = (LinearLayout) findViewById(R.id.nav_privacy);
        nav_privacy.setVisibility( View.GONE );//.setOnClickListener(this);
        nav_logOut = (LinearLayout) findViewById(R.id.nav_logOut);
        nav_logOut.setOnClickListener(this);


        enterCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (GeneralUtils.isNetworkAvailable(CitySearchActivity.this)) {
                    City city = (City) adapterView.getItemAtPosition(i);
                    enterCity.setText(city.getCityName());
                    storage.storeCity(city.getCityName());
                    cityID = city.getParentID();
                    //  Toast.makeText(getApplicationContext(),cityID,Toast.LENGTH_LONG).show();
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    proceed.setBackground(getResources().getDrawable(R.drawable.btn_design_solid));
                    proceed.setEnabled(true);
                } else {
                    GeneralUtils.displayNetworkAlert(CitySearchActivity.this, false);
                }

            }
        });

        enterCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                cityID = "999";
                proceed.setEnabled(false);
                proceed.setBackground(getResources().getDrawable(R.drawable.btn_grey_solid_dark));

            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });

        setUpNavHeader();

        topRll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GeneralUtils.isNetworkAvailable(CitySearchActivity.this)) {

                    progressDialog.show();
                    GenericRequest<LoginResponse> request = new GenericRequest<LoginResponse>(Request.Method.POST, CONSTANTS.URL_LOGIN, LoginResponse.class, new LoginRequest("", "", "", storage.loadID()),
                            new Response.Listener<LoginResponse>() {
                                @Override
                                public void onResponse(LoginResponse result) {
                                    progressDialog.dismiss();
                                    if (Boolean.valueOf(result.getResponse().get(0).getStatus())) {
                                        if (result.getResponse().get(0).getCode().equals("OK")) {
                                            if (result.getResponse().get(0).getPayloads().size() > 0) {
                                                storage.storeEmail(result.getResponse().get(0).getPayloads().get(0).getEmail());
                                                storage.storeFirstName(result.getResponse().get(0).getPayloads().get(0).getFirstName());
//                                            storage.storeLastName(result.getResponse().get(0).getPayloads().get(0).getLastName());
                                                storage.storeID(result.getResponse().get(0).getPayloads().get(0).getId());
                                                storage.storeProfilePic(result.getResponse().get(0).getPayloads().get(0).getProfilePic());
                                                storage.storePhone(result.getResponse().get(0).getPayloads().get(0).getMobile());
                                                storage.StoreKey(PrefEntities.ADDRESSS, result.getResponse().get(0).getPayloads().get(0).getAddress());
                                                storage.StoreKey(PrefEntities.COMPANY, result.getResponse().get(0).getPayloads().get(0).getOrganisation());
                                                storage.StoreKey(PrefEntities.DESIGNATION, result.getResponse().get(0).getPayloads().get(0).getDesignation());
                                                Intent intent = new Intent(CitySearchActivity.this, UpdateDetailsActivity.class);
                                                if (GeneralUtils.hasLollipop()) {
                                                    View sharedView = nav_Profile;
                                                    String transitionName = getString(R.string.profileAnimation);
                                                    ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(CitySearchActivity.this, sharedView, transitionName);
                                                    startActivity(intent, transitionActivityOptions.toBundle());
                                                } else {
                                                    startActivity(intent);
                                                }
                                            }
                                        }
                                    } else {
                                        GeneralUtils.ShowAlert(CitySearchActivity.this, getResources().getString(R.string.SomethingWentWrong));
                                    }


                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            GeneralUtils.ShowAlert(CitySearchActivity.this, getString(R.string.VolleyTimeout));
                            progressDialog.dismiss();

                        }
                    });
                    AppController.getInstance().addToRequestQueue(request);
                } else {
                    GeneralUtils.displayNetworkAlert(CitySearchActivity.this, false);
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.app_name))
                    .setMessage("Are you sure you want to exit?")
                    .setNegativeButton("No", null)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            finish();
                        }
                    }).create().show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        storage.ClearEventID();
        setUpNavHeader();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        // Handle navigation view item clicks here.
        int id = item.getItemId();


        switch (id) {


             /*   break; case R.id.my_earnings:
                    if (GeneralUtils.isNetworkAvailable(CitySearchActivity.this)) {
                      //  RefferalReward(storage.loadID());
                        RefferalRewardList(storage.loadID());
                    }
                    else {
                        GeneralUtils.displayNetworkAlert(CitySearchActivity.this, false);
                    }
                break;*/
        }


//        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(enterCity.getWindowToken(), 0);

//        InputMethodManager inputMethodManager = (InputMethodManager)  this.getSystemService(Activity.INPUT_METHOD_SERVICE);
//        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        switch (view.getId()) {


            case R.id.citySearch_btn_allEvents:

                if (GeneralUtils.isNetworkAvailable(CitySearchActivity.this)) {
                    GetallEvents();
                    progressDialog.show();
                } else {
                    GeneralUtils.displayNetworkAlert(CitySearchActivity.this, false);
                }

                break;
            case R.id.citySearch_btn_proceed:

                if (GeneralUtils.isNetworkAvailable(CitySearchActivity.this)) {
                    GetFilter(cityID);
                    storage.storeCityID(cityID);
                } else {
                    GeneralUtils.displayNetworkAlert(CitySearchActivity.this, false);
                }
                break;

            case R.id.nav_home:
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_change:
                intent = new Intent(CitySearchActivity.this, ChangePasswrodActivity.class);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_terms:
                intent = new Intent(CitySearchActivity.this, TermsAndConditions.class);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_privacy:
                intent = new Intent(CitySearchActivity.this, PrivacyAndPolicy.class);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_Myevents:

                if (GeneralUtils.isNetworkAvailable(CitySearchActivity.this)) {
                    GetallEvents();
                    progressDialog.show();
                } else {
                    GeneralUtils.displayNetworkAlert(CitySearchActivity.this, false);
                }
                drawer.closeDrawer(GravityCompat.START);

                break;
            case R.id.nav_logOut:
                drawer.closeDrawer(GravityCompat.START);

                final Dialog clearAll = new Dialog(CitySearchActivity.this);
                clearAll.requestWindowFeature(Window.FEATURE_NO_TITLE);
                clearAll.setCancelable(true);
                clearAll.setContentView(R.layout.dialog_log_out);
                TextView no = (TextView) clearAll.findViewById(R.id.no);
                TextView yes = (TextView) clearAll.findViewById(R.id.yes);
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Logout();
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clearAll.dismiss();
//
                    }
                });
                clearAll.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                clearAll.show();
                break;
            case R.id.nav_Conslting:
                if (GeneralUtils.isNetworkAvailable(CitySearchActivity.this)) {
                    getCounsultingPricing();
                } else {
                    GeneralUtils.displayNetworkAlert(CitySearchActivity.this, false);
                }
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_MyOrders:

                if (GeneralUtils.isNetworkAvailable(CitySearchActivity.this)) {
                    getMyOrders(storage.loadID());
                } else {
                    GeneralUtils.displayNetworkAlert(CitySearchActivity.this, false);
                }
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_support:

                if (GeneralUtils.isNetworkAvailable(CitySearchActivity.this)) {
                    ContactUs();
                } else {
                    GeneralUtils.displayNetworkAlert(CitySearchActivity.this, false);
                }
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.rewards_referral:
                if (GeneralUtils.isNetworkAvailable(CitySearchActivity.this)) {
                    RefferalReward(storage.loadID());
                } else {
                    GeneralUtils.displayNetworkAlert(CitySearchActivity.this, false);
                }
                drawer.closeDrawer(GravityCompat.START);
                break;
        }

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    void GetFilter(String id) {
        progressDialog.show();
        GenericRequest<GetVenueFiltersResponse> request = new GenericRequest<GetVenueFiltersResponse>(Request.Method.POST, CONSTANTS.URL_GET_VENUE_FILTERS, GetVenueFiltersResponse.class,
                new FilterRequest(id),
                new Response.Listener<GetVenueFiltersResponse>() {
                    @Override
                    public void onResponse(GetVenueFiltersResponse response) {

                        if (Boolean.valueOf(response.getResponse().get(0).getStatus())) {
                            new setUpSingleton().execute(response);
                        } else {
                            progressDialog.dismiss();
                            GeneralUtils.ShowAlert(CitySearchActivity.this, response.getResponse().get(0).getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Snackbar snackbar = Snackbar
                        .make(root, getResources().getString(R.string.SomethingWentWrong), Snackbar.LENGTH_LONG);

                snackbar.show();

            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }

    class setUpSingleton extends AsyncTask<GetVenueFiltersResponse, Void, String> {

        @Override
        protected String doInBackground(GetVenueFiltersResponse... getVenueFiltersResponses) {

            p1.clear();
            p2.clear();
            GetVenueFiltersResponse response = getVenueFiltersResponses[0];

            if (MySingleton.getInstance() == null) {
                MySingleton.initInstance();
            }

            MySingleton.getInstance().setModel(response);
            for (int i = 0; i < response.getResponse().get(0).getPayloads().size(); i++) {
                if (response.getResponse().get(0).getPayloads().get(i).getMoreType().equals("0")) {
                    p1.add(response.getResponse().get(0).getPayloads().get(i));
                } else {
                    p2.add(response.getResponse().get(0).getPayloads().get(i));
                }

            }
            MySingleton.getInstance().setPayload1(p1);
            MySingleton.getInstance().setPayload2(p2);
            storage.storePayload1(p1);
            storage.storePayload1Original(p1);
            storage.storePayload2(p2);
            storage.storePayload3(p2);


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            super.onPostExecute(s);
            if (Boolean.valueOf(MySingleton.getInstance().getModel().getResponse().get(0).getStatus())) {
                Intent i = new Intent(CitySearchActivity.this, VenueFilterActivity.class);
                startActivity(i);
            } else {
                Snackbar snackbar = Snackbar
                        .make(root, MySingleton.getInstance().getModel().getResponse().get(0).getMessage(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        enterCity.setText("");
    }


    void GetallEvents() {
        GenericRequest<GetSavedEventListResponse> request = new GenericRequest<GetSavedEventListResponse>(Request.Method.POST, CONSTANTS.URL_GET_SAVED_EVENT_LIST, GetSavedEventListResponse.class,
                new GetSavedVenueRequest(storage.loadID())
                , new Response.Listener<GetSavedEventListResponse>() {
            @Override
            public void onResponse(GetSavedEventListResponse response) {
                progressDialog.dismiss();
                if (Boolean.valueOf(response.getResponse().get(0).getStatus())) {
                    Intent i = new Intent(CitySearchActivity.this, AllEventsActivity.class);
                    i.putExtra("events", response);
                    startActivity(i);
                } else {

                    final Dialog successDialog = new Dialog(CitySearchActivity.this);
                    successDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    successDialog.setCancelable(true);
                    successDialog.setContentView(R.layout.dialogue_event_saved);
                    TextView messageTitle = (TextView) successDialog.findViewById(R.id.messageTitle);
                    TextView messageBody = (TextView) successDialog.findViewById(R.id.messageBody);
                    ImageView dialog_icon = (ImageView) successDialog.findViewById(R.id.dialog_icon);
                    dialog_icon.setImageResource(R.drawable.result_not_found);
                    messageTitle.setText("Nothing Yet");
                    messageBody.setText("We could not find any events saved by you.\nCreate one, now.");

                    TextView gotIt = (TextView) successDialog.findViewById(R.id.eventSaved);
                    gotIt.setText("Get Started");
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
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                GeneralUtils.ShowAlert(CitySearchActivity.this, getString(R.string.VolleyTimeout));
            }
        });

        AppController.getInstance().addToRequestQueue(request);
//        request.setRetryPolicy(new DefaultRetryPolicy(
//                500,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }


    void setUpNavHeader() {
//        View header = navigationView.getHeaderView(0);
//        nav_Name = (TextView) header.findViewById(R.id.nav_name);
//        nav_Email = (TextView) header.findViewById(R.id.nav_email);
//        nav_Profile = (CircleImageView) header.findViewById(R.id.nav_profileImage);
//        nav_view = (LinearLayout) header.findViewById(R.id.nav_view);
//        edit_icon = (ImageView) header.findViewById(R.id.edit_icon);
//        topRll = (RelativeLayout) header.findViewById(R.id.topRll);


        if (storage.loadProfilePic().length() > 5) {
            try {
                byte[] decodedString = Base64.decode(storage.loadProfilePic(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                nav_Profile.setImageBitmap(decodedByte);
            } catch (Exception e) {
                nav_Profile.setImageResource(R.drawable.user_profile_image_grey);
            }
        } else {
            nav_Profile.setImageResource(R.drawable.user_profile_image_grey);
        }

     /*   nav_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (GeneralUtils.isNetworkAvailable(CitySearchActivity.this)){
                    progressDialog.show();
                    GenericRequest<LoginResponse> request = new GenericRequest<LoginResponse>(Request.Method.POST, CONSTANTS.URL_LOGIN, LoginResponse.class, new LoginRequest("", "", "", storage.loadID()),
                            new Response.Listener<LoginResponse>() {
                                @Override
                                public void onResponse(LoginResponse result) {
                                    progressDialog.dismiss();
                                    if (Boolean.valueOf(result.getResponse().get(0).getStatus())) {
                                        if (result.getResponse().get(0).getCode().equals("OK")) {
                                            if (result.getResponse().get(0).getPayloads().size() > 0) {
                                                storage.storeEmail(result.getResponse().get(0).getPayloads().get(0).getEmail());
                                                storage.storeFirstName(result.getResponse().get(0).getPayloads().get(0).getFirstName());
//                                               storage.storeLastName(result.getResponse().get(0).getPayloads().get(0).getLastName());
                                                storage.storeID(result.getResponse().get(0).getPayloads().get(0).getId());
                                                storage.storeProfilePic(result.getResponse().get(0).getPayloads().get(0).getProfilePic());
                                                storage.storePhone(result.getResponse().get(0).getPayloads().get(0).getMobile());
                                                storage.StoreKey(PrefEntities.ADDRESSS,result.getResponse().get(0).getPayloads().get(0).getAddress());
                                                storage.StoreKey(PrefEntities.COMPANY,result.getResponse().get(0).getPayloads().get(0).getOrganisation());
                                                storage.StoreKey(PrefEntities.DESIGNATION,result.getResponse().get(0).getPayloads().get(0).getDesignation());
                                                Intent intent = new Intent(CitySearchActivity.this, UpdateDetailsActivity.class);
                                                if (GeneralUtils.hasLollipop()) {
                                                    View sharedView = nav_Profile;
                                                    String transitionName = getString(R.string.profileAnimation);
                                                    ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(CitySearchActivity.this, sharedView, transitionName);
                                                    startActivity(intent, transitionActivityOptions.toBundle());
                                                } else {
                                                    startActivity(intent);
                                                }
                                            }
                                        }
                                    } else {
                                        GeneralUtils.ShowAlert(CitySearchActivity.this, getResources().getString(R.string.SomethingWentWrong));
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            GeneralUtils.ShowAlert(CitySearchActivity.this,getString(R.string.VolleyTimeout));
                            progressDialog.dismiss();
                        }
                    });
                    AppController.getInstance().addToRequestQueue(request);

                }
                else {
                    GeneralUtils.displayNetworkAlert(CitySearchActivity.this,false);
                }


            }
        });*/


//        nav_view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//            }
//        });


        nav_Name.setText(storage.loadfirstName());
        nav_Email.setText(storage.loadEmail());
    }


    void Logout() {
        progressDialog.show();
        GenericRequest<LogoutResponse> request = new GenericRequest<LogoutResponse>(Request.Method.POST, CONSTANTS.URL_LOGOUT, LogoutResponse.class, new LogoutRequest(storage.loadID()),
                new Response.Listener<LogoutResponse>() {
                    @Override
                    public void onResponse(LogoutResponse response) {
                        progressDialog.dismiss();
                        if (Boolean.valueOf(response.getResponse().get(0).getStatus())) {
                            String token = storage.loadDeviceToken();
                            storage.clearStorage();
                            storage.storeDeviceToken(token);
                            Intent i = new Intent(CitySearchActivity.this, TutorialActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                        } else {
                            GeneralUtils.ShowAlert(CitySearchActivity.this, getResources().getString(R.string.SomethingWentWrong));
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                GeneralUtils.ShowAlert(CitySearchActivity.this, getResources().getString(R.string.VolleyTimeout));

            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }

    void CleanStorage() {
        storage.ClearKey(PrefEntities.EVENTID);
        storage.ClearKey(PrefEntities.EVENTDATE);
        storage.ClearKey(PrefEntities.EVENTNAME);
        storage.ClearKey(PrefEntities.HALLID);
        storage.ClearKey(PrefEntities.VENUEID);
        storage.ClearKey(PrefEntities.SETUPID);
        storage.ClearKey(PrefEntities.DESIGNID);
    }


    void getCounsultingPricing() {
        progressDialog.show();
        GenericRequest<ConsultingResponse> request = new GenericRequest<ConsultingResponse>(Request.Method.POST, CONSTANTS.URL_CONSULTING_PRICE, ConsultingResponse.class,
                null, new Response.Listener<ConsultingResponse>() {
            @Override
            public void onResponse(ConsultingResponse response) {
                progressDialog.dismiss();
                if (Boolean.valueOf(response.getResponse().get(0).getStatus())) {
                    Intent intent = new Intent(CitySearchActivity.this, ConsultingServiceActivity.class);
                    intent.putExtra("data", response);
                    intent.putExtra("ComeFrom", "CitySearchActivity");
                    startActivity(intent);

                } else {
                    GeneralUtils.ShowAlert(CitySearchActivity.this, response.getResponse().get(0).getMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                GeneralUtils.ShowAlert(CitySearchActivity.this, getString(R.string.VolleyTimeout));
                progressDialog.dismiss();

            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }

    void getMyOrders(String UserID) {
        progressDialog.show();
        GenericRequest<MyOrdersResponse> request = new GenericRequest<MyOrdersResponse>(Request.Method.POST, CONSTANTS.URL_MYORDERS, MyOrdersResponse.class,
                new MyOrdersRequest(UserID), new Response.Listener<MyOrdersResponse>() {
            @Override
            public void onResponse(MyOrdersResponse response) {
                progressDialog.dismiss();
                if (Boolean.parseBoolean(response.getResponse().get(0).getStatus())) {
                    Intent intent = new Intent(CitySearchActivity.this, MyOrdersActivity.class);
                    intent.putExtra("data", response);
                    startActivity(intent);
                } else {
                    CustomAlert("My Orders", "You have not placed any orders yet. Proceed with placing your first order.");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                GeneralUtils.ShowAlert(CitySearchActivity.this, getString(R.string.VolleyTimeout));

            }
        });
        AppController.getInstance().addToRequestQueue(request);
//        request.setRetryPolicy(new DefaultRetryPolicy(
//                30000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    void ContactUs() {
        progressDialog.show();
        GenericRequest<ContactUsResponse> request = new GenericRequest<ContactUsResponse>(Request.Method.GET, CONSTANTS.URL_CONTACT_US, ContactUsResponse.class,
                null, new Response.Listener<ContactUsResponse>() {
            @Override
            public void onResponse(ContactUsResponse response) {
                progressDialog.dismiss();
                if (Boolean.valueOf(response.getResponse().get(0).getStatus())) {
                    Intent intent = new Intent(CitySearchActivity.this, ContactUsActivity.class);
                    intent.putExtra("data", response);
                    startActivity(intent);

                } else {
                    GeneralUtils.ShowAlert(CitySearchActivity.this, getString(R.string.VolleyTimeout));
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                GeneralUtils.ShowAlert(CitySearchActivity.this, getString(R.string.VolleyTimeout));

            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }


    void RefferalReward(String userId) {
        progressDialog.show();
        GenericRequest<RewardsReferralsResponse> request = new GenericRequest<RewardsReferralsResponse>(Request.Method.POST, CONSTANTS.URL_REWARDS_REFERRALS, RewardsReferralsResponse.class,
                new ReferralsRewardRequest(userId), new Response.Listener<RewardsReferralsResponse>() {
            @Override
            public void onResponse(RewardsReferralsResponse response) {
                progressDialog.dismiss();
                //Toast.makeText(RewardsAndReferrals.this,response.getResponse().get(0).getStatus().toString(),Toast.LENGTH_SHORT).show();

                if (Boolean.valueOf(response.getResponse().get(0).getStatus())) {
                    Intent intent = new Intent(CitySearchActivity.this, RewardsAndReferrals.class);
                    intent.putExtra("referral_codes", response.getResponse().get(0).getPayloads().get(0).getReferral_codes());
                    intent.putExtra("totalEarningpoint", response.getResponse().get(0).getPayloads().get(0).getTotalEarningpoint());
                    startActivity(intent);
                } else {
                    GeneralUtils.ShowAlert(CitySearchActivity.this, getString(R.string.VolleyTimeout));
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                GeneralUtils.ShowAlert(CitySearchActivity.this, getString(R.string.VolleyTimeout));

            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }

    public void CustomAlert(String TitleMessage, String BodyMessage) {

        final Dialog successDialog = new Dialog(CitySearchActivity.this);
        successDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        successDialog.setCancelable(true);
        successDialog.setContentView(R.layout.dialog_general_utils_custom);
        TextView messageTitle = (TextView) successDialog.findViewById(R.id.messageTitle);
        TextView messageBody = (TextView) successDialog.findViewById(R.id.messageBody);
        if (TitleMessage.equals("") || TitleMessage.equals(null)) {
            messageTitle.setText(R.string.app_name);
        } else {
            messageTitle.setText(TitleMessage);
        }
        if (BodyMessage.equals("") || BodyMessage.equals(null)) {
            messageBody.setText("Something doesn't see right.");
        } else {
            messageBody.setText(BodyMessage);
        }


        TextView gotIt = (TextView) successDialog.findViewById(R.id.button_ok);
        gotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                successDialog.dismiss();
                if (GeneralUtils.isNetworkAvailable(CitySearchActivity.this)) {
                    getCounsultingPricing();
                } else {
                    GeneralUtils.displayNetworkAlert(CitySearchActivity.this, false);
                }
            }
        });
        successDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        successDialog.show();

    }

}

//FilterID 1 ; FilterValue 1,2,3