package kestone.com.kestone.UI;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.appnit.plutus.pg.model.MerchantParams;
import com.appnit.plutus.pg.model.PayReqParams;
import com.appnit.plutus.pg.screen.PlutusInt;
import com.atom.mobilepaymentsdk.PayActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import io.fabric.sdk.android.services.common.CommonUtils;
import it.gmariotti.recyclerview.adapter.AlphaAnimatorAdapter;
import kestone.com.kestone.Adapters.ConsultingServiceActivity.ConsultingSearchAdapter;
import kestone.com.kestone.Adapters.ConsultingServiceActivity.ConsultingSelectionAdapter;
import kestone.com.kestone.Adapters.DialogueAdapters.PhoneNumbersAdapter;
import kestone.com.kestone.MODEL.Consulting.CheckPreviousPayment;
import kestone.com.kestone.MODEL.Consulting.ConsultingInfoResponse;
import kestone.com.kestone.MODEL.Consulting.ConsultingResponse;
import kestone.com.kestone.MODEL.EventSearch.RESPONSE.Events;
import kestone.com.kestone.MODEL.GetPaymentQuote.REQUEST.GetPaymentQuoteRequest;
import kestone.com.kestone.MODEL.GetPaymentQuote.RESPONSE.GetPaymentQuotePayload;
import kestone.com.kestone.MODEL.GetPaymentQuote.RESPONSE.GetPaymentQuoteResponse;
import kestone.com.kestone.MODEL.Payment.REQUEST.PaymentRequest;
import kestone.com.kestone.MODEL.Payment.RESPONSE.PaymentResponse;
import kestone.com.kestone.MODEL.PaymentSuccess.REQUEST.PaymentSuccessRequest;
import kestone.com.kestone.MODEL.PaymentSuccess.RESPONSE.PaymentSuccessResponse;
import kestone.com.kestone.R;
import kestone.com.kestone.Utilities.CONSTANTS;
import kestone.com.kestone.Utilities.DelayAutoCompleteTextView;
import kestone.com.kestone.Utilities.GeneralUtils;
import kestone.com.kestone.Utilities.GenericRequest;
import kestone.com.kestone.Utilities.GridSpacingItemDecoration;
import kestone.com.kestone.Utilities.StorageUtilities;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ConsultingServiceActivity extends AppCompatActivity implements ConsultingSelectionAdapter.ConsultingAdapterCallBack, View.OnClickListener {

    TextView amount;
    RecyclerView rv;
    Dialog dialog;
    TextView cancel, payment;
    DelayAutoCompleteTextView autoCompleteTextView;
    ConsultingSearchAdapter adapter;
    ConsultingSelectionAdapter consultingSelectionAdapter;
    ProgressDialog progressDialog;
    ConsultingResponse data;
    boolean eventSelected;
    String eventID;
    String GstNo;
    String transationID;
    List<GetPaymentQuotePayload> myData = null;
    String baseAmount = "";
    String venueAmount = "0", setupAmount = "0", designAmount = "0", artistAmount = "0", activitiesAmount = "0", giveawayAmount = "0";
    EditText payment_et;
    boolean isOragnizationSelected = false;

    AlphaAnimatorAdapter alphaAnimatorAdapter;
    LinearLayout linearLayout, tvABtCS;
    String ComeFrom;
    StorageUtilities storage;
    int sum = 0;
    String DesignAmount = "", MoreAmount = "", SetupAmount = "", VenueAmount = "";
    Events events;

    TextWatcher textWatcher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulting_service);
        amount = (TextView) findViewById(R.id.amount);
        rv = (RecyclerView) findViewById(R.id.rv);
        cancel = (TextView) findViewById(R.id.cancel);
        linearLayout = (LinearLayout) findViewById(R.id.venueFilterRoot);
        tvABtCS = (LinearLayout) findViewById(R.id.tvABtCS);
        tvABtCS.setOnClickListener(this);

        //Ganesh Codeing
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        progressDialog = new ProgressDialog(ConsultingServiceActivity.this);
        progressDialog.setMessage(getString(R.string.PleaseWait));
        progressDialog.setCancelable(false);
        cancel.setOnClickListener(this);
        storage = new StorageUtilities(ConsultingServiceActivity.this);
        payment = (TextView) findViewById(R.id.payment);
        payment.setOnClickListener(this);
        autoCompleteTextView = (DelayAutoCompleteTextView) findViewById(R.id.search_event);
        amount.setText("₹ " + sum);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(ConsultingServiceActivity.this, 3);
        adapter = new ConsultingSearchAdapter(ConsultingServiceActivity.this);
        rv.setLayoutManager(layoutManager);
        rv.addItemDecoration(new GridSpacingItemDecoration(3, dpToPx(10), true));
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setThreshold(0);
        autoCompleteTextView.setAutoCompleteDelay(20);
        if (getIntent() != null) {
            data = (ConsultingResponse) getIntent().getSerializableExtra("data");

            Intent intent = getIntent();
            ComeFrom = intent.getStringExtra("ComeFrom");
        }


        if (ComeFrom.equals("MoreFragment")) {
            Intent intent = getIntent();
            String eventIDE = intent.getStringExtra("eventId");
            String EventName = intent.getStringExtra("eventName");
            autoCompleteTextView.setText(EventName);
            autoCompleteTextView.setEnabled(false);
            eventID = eventIDE;


            JSONObject object = new JSONObject();
            try {
                object.put("EventID", eventID);
                object.put("userId", storage.loadID());

                if (GeneralUtils.isNetworkAvailable(ConsultingServiceActivity.this)) {
                    getCounsulting(object.toString());
                    progressDialog.show();
                } else {
                    GeneralUtils.displayNetworkAlert(ConsultingServiceActivity.this, false);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


            //  Toast.makeText(ConsultingServiceActivity.this,eventIDE+" "+EventName,Toast.LENGTH_LONG).show();

        } else {
            autoCompleteTextView.setHint("Select Your Event");
        }


        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                rv.setAdapter(null);
                amount.setText("₹ " + 0);
                sum = 0;
                venueAmount = "0";
                setupAmount = "0";
                designAmount = "0";
                artistAmount = "0";
                activitiesAmount = "0";
                giveawayAmount = "0";
                DesignAmount = "";
                SetupAmount = "";
                VenueAmount = "";
                MoreAmount = "";
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        };

        autoCompleteTextView.addTextChangedListener(textWatcher);


        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                events = (Events) adapterView.getItemAtPosition(i);
                if (!events.getEventName().equals("No Record Found")) {
                    JSONObject object = new JSONObject();
                    try {
                        autoCompleteTextView.removeTextChangedListener(textWatcher);
                        eventID = events.getEventId();
                        autoCompleteTextView.setText(events.getEventName());
                        object.put("EventID", events.getEventId());
                        object.put("userId", storage.loadID());

                        if (GeneralUtils.isNetworkAvailable(ConsultingServiceActivity.this)) {
                            getCounsulting(object.toString());
                            progressDialog.show();
                        } else {
                            GeneralUtils.displayNetworkAlert(ConsultingServiceActivity.this, false);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    autoCompleteTextView.setText("");
                }
            }
        });
    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_organization:
                if (checked)
                    payment_et.setVisibility(View.VISIBLE);
                isOragnizationSelected = true;
                break;
            case R.id.radio_individual:
                if (checked)
                    payment_et.setVisibility(View.GONE);
                isOragnizationSelected = false;
                payment_et.setText("");

                break;
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(int Amount, boolean sel, int position) {
        if (sel) {
            sum = sum + Amount;
            amount.setText("₹ " + sum);
            switch (position) {
                case 0:
                    venueAmount = String.valueOf(Amount);
                    break;
                case 1:
                    setupAmount = String.valueOf(Amount);
                    break;
                case 2:
                    designAmount = String.valueOf(Amount);
                    break;
                case 3:
                    artistAmount = String.valueOf(Amount);
                    break;
                case 4:
                    activitiesAmount = String.valueOf(Amount);
                    break;
                case 5:
                    giveawayAmount = String.valueOf(Amount);
                    break;
            }
        } else {
            sum = sum - Amount;
            amount.setText("₹ " + sum);
            switch (position) {
                case 0:
                    venueAmount = "0";
                    break;
                case 1:
                    setupAmount = "0";
                    break;
                case 2:
                    designAmount = "0";
                    break;
                case 3:
                    artistAmount = "0";
                    break;
                case 4:
                    activitiesAmount = "0";
                    break;
                case 5:
                    giveawayAmount = "0";
                    break;
            }
        }
        consultingSelectionAdapter.notifyDataSetChanged();
    }

    void resetLabels() {
        for (int i = 0; i < data.getResponse().get(0).getPayload().size(); i++) {
            data.getResponse().get(0).getPayload().get(i).setSelected(false);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                finish();
                break;
            case R.id.payment:
                if (GeneralUtils.isNetworkAvailable(ConsultingServiceActivity.this)) {
                    isOragnizationSelected = false;
                    if (!venueAmount.equals("0") || !setupAmount.equals("0") || !designAmount.equals("0") || !artistAmount.equals("0") || !activitiesAmount.equals("0") || !giveawayAmount.equals("0")) {
                        //Pay();
                        // Payment(venueAmount,setupAmount,designAmount,artistAmount,activitiesAmount,giveawayAmount,eventID,storage.loadID(),String.valueOf(sum+400),"400","12");

                        dialog.setCancelable(true);

                        dialog.setContentView(R.layout.dialogue_proceed_payment);
                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        payment_et = (EditText) dialog.findViewById(R.id.payment_et);
                        final TextView submit = (TextView) dialog.findViewById(R.id.submit);
                        submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String PaymentAmount = payment_et.getText().toString();
                                GstNo = payment_et.getText().toString();
                                if (isOragnizationSelected) {
                                    if (TextUtils.isEmpty(PaymentAmount)) {
                                        Snackbar snackbar = Snackbar.make(linearLayout, "Enter GST Number.", Snackbar.LENGTH_LONG);
                                        snackbar.show();
                                    } else {
                                        GetPaymentQuote(String.valueOf(sum), payment_et.getText().toString());
                                    }
                                } else {
                                    GetPaymentQuote(String.valueOf(sum), "");
                                    isOragnizationSelected = false;
                                }
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    } else {
                        GeneralUtils.ShowAlert(ConsultingServiceActivity.this, getString(R.string.SelectConsultingError), "Consulting Services");
                    }
                } else {
                    GeneralUtils.displayNetworkAlert(ConsultingServiceActivity.this, false);
                }

                break;

            case R.id.tvABtCS:
                if (GeneralUtils.isNetworkAvailable(ConsultingServiceActivity.this)) {
                    getCounsultingInfo();
                } else {
                    GeneralUtils.displayNetworkAlert(ConsultingServiceActivity.this, false);
                }
                break;
        }
    }

    void Payment(String venueAmount, String setupAmount, String designAmount, String artistAmount, String activitiesAmount, String giveawayAmount, String eventID, String userID, String totalAmount, String taxAmount, String taxPercent) {
        progressDialog.show();
        GenericRequest<PaymentResponse> request = new GenericRequest<PaymentResponse>(Request.Method.POST, CONSTANTS.URL_PAYMENT_API, PaymentResponse.class,
                new PaymentRequest(venueAmount, setupAmount, designAmount, artistAmount, activitiesAmount, giveawayAmount, eventID, userID, totalAmount, taxAmount, taxPercent), new Response.Listener<PaymentResponse>() {
            @Override
            public void onResponse(PaymentResponse response) {
                progressDialog.dismiss();
                GeneralUtils.ShowAlert(ConsultingServiceActivity.this, response.getResponse().get(0).getMessage());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                GeneralUtils.ShowAlert(ConsultingServiceActivity.this, getString(R.string.VolleyTimeout));
            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }

    void GetPaymentQuote(String TotalAmount, String GSTNo) {
        progressDialog.show();
        final GenericRequest<GetPaymentQuoteResponse> request = new GenericRequest<GetPaymentQuoteResponse>(Request.Method.POST, CONSTANTS.URL_GET_PAYMENT_QUOTE, GetPaymentQuoteResponse.class,
                new GetPaymentQuoteRequest(TotalAmount, GSTNo), new Response.Listener<GetPaymentQuoteResponse>() {
            @Override
            public void onResponse(GetPaymentQuoteResponse response) {
                progressDialog.dismiss();
                if (Boolean.parseBoolean(response.getResponse().get(0).getStatus())) {
                    myData = response.getResponse().get(0).getPayload();
                    String GrantToatalAmount = response.getResponse().get(0).getPayload().get(0).getGrandTotalAmount();
                    baseAmount = response.getResponse().get(0).getPayload().get(0).getGrandTotalAmount();
                    Pay(GrantToatalAmount);
                } else {
                    GeneralUtils.ShowAlert(ConsultingServiceActivity.this, response.getResponse().get(0).getMessage().toString());
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                GeneralUtils.ShowAlert(ConsultingServiceActivity.this, getString(R.string.VolleyTimeout));
            }
        });
        AppController.getInstance().addToRequestQueue(request);
    }


    void Pay(String amount) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());

        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder((100000 + rnd.nextInt(900000)));
        for (int i = 0; i < 5; i++)
            sb.append(chars[rnd.nextInt(chars.length)]);

//
        String TxnId = new StorageUtilities(this).loadID() + sb.toString();

//        PayReqParams obj = new PayReqParams();
//        MerchantParams params = new MerchantParams();
//        params.setMerAmount(amount);
//        params.setMerMID("308");
//        params.setMerAppName("kestone");
//        params.setMerAppUser("kestone");
//        params.setMerCBUrl("android");
//        params.setMerCurrency("INR");
//        params.setMerCountry("IND");
//        params.setMerOthersDtls("Other");
//        params.setMerKey("1QfmTm5mKcpptjn3ehRpB+Od5LK09rGx8MNe6fkMZtc=");
//        params.setMerTxnId(TxnId); // must be unique every time.
//        params.setActionBarTitle(""); // Actionbar Title to show on PG Screen.
//        params.setIsProductionEnv(false); // Default : false (for Test), true (for Production).
//        params.setHaveBillingDtls(false); // Default : false (So not require to set object of BillingDtls in obj ).
//        params.setHaveShippingDtls(false); // Default : false(So not require to set object of ShippingDtls in obj).
//        obj.setMerchantParams(params);
//
//        PlutusInt.getInstance(ConsultingServiceActivity.this, obj);

        Log.d("amount", amount);
        Log.d("TxnId", TxnId);
        Log.d("date", currentDateandTime);

        Intent newPayIntent = new Intent(ConsultingServiceActivity.this, PayActivity.class);
        newPayIntent.putExtra("merchantId", "61028");
        newPayIntent.putExtra("txnscamt", "0"); //Fixed. Must be 0
        newPayIntent.putExtra("loginid", "61028");
        newPayIntent.putExtra("password", "KESTONE@123");
        newPayIntent.putExtra("prodid", "MOBILEAPP");
        newPayIntent.putExtra("txncurr", "INR"); //Fixed. Must be ?INR?
        newPayIntent.putExtra("clientcode", "007");
        //newPayIntent.putExtra("custacc", "100000036600");
        newPayIntent.putExtra("custacc", "100000036600");
        newPayIntent.putExtra("amt", amount + ".000");//Should be 3 decimal number i.e 51.000
        //newPayIntent.putExtra("amt", "50" + ".000");//Should be 3 decimal number i.e 51.000
        newPayIntent.putExtra("txnid", TxnId);
        newPayIntent.putExtra("date", currentDateandTime);//Should be in same format
        //newPayIntent.putExtra("bankid", "9"); //Should be valid bank id // Optional
        newPayIntent.putExtra("discriminator", "ALL"); // NB or IMPS or All ONLY (value should be same as commented)
        //newPayIntent.putExtra("ru", "https://paynetzuat.atomtech.in/mobilesdk/param");  // FOR UAT (Testing)
        newPayIntent.putExtra("ru", "https://payment.atomtech.in/mobilesdk/param");  // FOR UAT (Testing)
        //newPayIntent.putExtra("ru", "https://paynetzuat.atomtech.in/mobilesdk/param"); // FOR UAT (Testing)
        startActivityForResult(newPayIntent, 1);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (data != null) {
                Log.d("Datastatus", data.getStringExtra("status"));
                Log.d("Data", Arrays.toString(data.getStringArrayExtra("responseKeyArray")));
                Log.d("Data", Arrays.toString(data.getStringArrayExtra("responseValueArray")));


                String message = data.getStringExtra("status");
                String[] resKey = data.getStringArrayExtra("responseKeyArray");
                String[] resValue = data.getStringArrayExtra("responseValueArray");
                if (resKey != null && resValue != null) {
                    for (int i = 0; i < resKey.length; i++)
                        System.out.println("  " + i + " resKey : " + resKey[i] + " resValue : " + resValue[i]);
                }

                try {


                    String RESVAL = resValue[2].toString();
                    GeneralUtils.log("resValuetst", RESVAL);
                    if (RESVAL.equals("null")) {
                        switch (resValue[19].toString()) {
                            case "Ok":
                                if (myData != null)
                                    transationID = resValue[19];
                                // Payment(venueAmount,setupAmount,designAmount,artistAmount,activitiesAmount,giveawayAmount,eventID,storage.loadID(),String.valueOf(sum+400),"400","12");
                                PaymentSuccess(storage.loadID(), eventID, GstNo, transationID, venueAmount, setupAmount, designAmount, artistAmount,
                                        activitiesAmount, giveawayAmount, "", myData.get(0).getBaseAmount(), myData.get(0).getCGSTTax(),
                                        myData.get(0).getCGSTTaxAmount(), myData.get(0).getSGSTTax(), myData.get(0).getSGSTTaxAmount(),
                                        myData.get(0).getIGSTTax(), myData.get(0).getIGSTTaxAmount(), myData.get(0).getTaxAmount(), myData.get(0).getGrandTotalAmount());
                                break;
                            case "F":
                                GeneralUtils.ShowAlert(ConsultingServiceActivity.this, "Transction Failure");
                                break;
                            default:
                                GeneralUtils.ShowAlert(ConsultingServiceActivity.this, "Transection Cancelled by User.");
                                break;
                        }

                    } else {
                        switch (resValue[22].toString()) {
                            case "Ok":
                                if (myData != null)
                                    transationID = resValue[19];
                                // Payment(venueAmount,setupAmount,designAmount,artistAmount,activitiesAmount,giveawayAmount,eventID,storage.loadID(),String.valueOf(sum+400),"400","12");
                                PaymentSuccess(storage.loadID(), eventID, GstNo, transationID, venueAmount, setupAmount, designAmount, artistAmount,
                                        activitiesAmount, giveawayAmount, "", myData.get(0).getBaseAmount(), myData.get(0).getCGSTTax(),
                                        myData.get(0).getCGSTTaxAmount(), myData.get(0).getSGSTTax(), myData.get(0).getSGSTTaxAmount(),
                                        myData.get(0).getIGSTTax(), myData.get(0).getIGSTTaxAmount(), myData.get(0).getTaxAmount(), myData.get(0).getGrandTotalAmount());
                                break;

                            case "F":
                                GeneralUtils.ShowAlert(ConsultingServiceActivity.this, resValue[21].toString());
                                break;
                            default:
                                GeneralUtils.ShowAlert(ConsultingServiceActivity.this, "Transection Cancelled by User.");
                                break;
                        }
                    }


                } catch (Exception e) {
                    GeneralUtils.ShowAlert(ConsultingServiceActivity.this, data.getStringExtra("status"));
                }
            }
        }
    }


    void PaymentSuccess(String userID, String eventID, String GSTNo, String transationID, String vanueAmount,
                        String setupAmount, String designAmount, String artistAmount, String activitiesAmount,
                        String giveawayAmount, String otherAmount, String baseAmount, String CGSTTax,
                        String CGSTAmount, String SGSTTax, String SGSTAmount, String IGSTTax, String
                                IGSTAmount, String totalTax, String grandTotal) {
        progressDialog.show();

        GenericRequest<PaymentSuccessResponse> request = new GenericRequest<PaymentSuccessResponse>(Request.Method.POST, CONSTANTS.URL_PAYMENT_SUCCESS,
                PaymentSuccessResponse.class, new PaymentSuccessRequest(userID, eventID, GSTNo, transationID, vanueAmount, setupAmount, designAmount, artistAmount,
                activitiesAmount, giveawayAmount, otherAmount, baseAmount, CGSTTax, CGSTAmount, SGSTTax, SGSTAmount, IGSTTax, IGSTAmount, totalTax, grandTotal),
                new Response.Listener<PaymentSuccessResponse>() {
                    @Override
                    public void onResponse(PaymentSuccessResponse response) {
                        progressDialog.dismiss();
                        if (Boolean.parseBoolean(response.getResponse().get(0).getStatus())) {
                            // GeneralUtils.ShowAlert(ConsultingServiceActivity.this,"");

                            showSuccessAlert(ConsultingServiceActivity.this, "Payment Successful");

                        } else {
                            GeneralUtils.ShowAlert(ConsultingServiceActivity.this, response.getResponse().get(0).getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                GeneralUtils.ShowAlert(ConsultingServiceActivity.this, getString(R.string.VolleyTimeout));

            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ConsultingServiceActivity.this, CitySearchActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void showSuccessAlert(Context context, String message) {

        final Dialog successDialog = new Dialog(context);
        successDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        successDialog.setCancelable(false);
        successDialog.setContentView(R.layout.dialog_general_utils_custom);
        TextView messageTitle = (TextView) successDialog.findViewById(R.id.messageTitle);
        TextView messageBody = (TextView) successDialog.findViewById(R.id.messageBody);
        ImageView iv = (ImageView) successDialog.findViewById(R.id.iv);
        iv.setImageDrawable(getResources().getDrawable(R.drawable.tick_inside_a_circle));
        messageBody.setText(message);
        TextView gotIt = (TextView) successDialog.findViewById(R.id.button_ok);
        gotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent(ConsultingServiceActivity.this, CitySearchActivity.class);
                startActivity(ii);
                successDialog.dismiss();
                finish();
            }
        });
        successDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        successDialog.show();

    }

    void getCounsultingInfo() {
        progressDialog.show();
        GenericRequest<ConsultingInfoResponse> request = new GenericRequest<ConsultingInfoResponse>(Request.Method.POST, CONSTANTS.URL_Get_Consultancy_Info, ConsultingInfoResponse.class,
                "", new Response.Listener<ConsultingInfoResponse>() {
            @Override
            public void onResponse(ConsultingInfoResponse response) {
                progressDialog.dismiss();
                if (Boolean.valueOf(response.getResponse().get(0).getStatus())) {
                    Intent intent = new Intent(ConsultingServiceActivity.this, AboutConsultingServices.class);
                    intent.putExtra("data", response);
                    startActivity(intent);

                } else {
                    GeneralUtils.ShowAlert(ConsultingServiceActivity.this, response.getResponse().get(0).getMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                GeneralUtils.ShowAlert(ConsultingServiceActivity.this, getString(R.string.VolleyTimeout));
                progressDialog.dismiss();

            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }

    void getCounsulting(String body) {
        progressDialog.show();

        GenericRequest<CheckPreviousPayment> request = new GenericRequest<CheckPreviousPayment>(Request.Method.POST, CONSTANTS.URL_Event_Search_Id, CheckPreviousPayment.class,
                body, new Response.Listener<CheckPreviousPayment>() {
            @Override
            public void onResponse(CheckPreviousPayment response) {
                progressDialog.dismiss();
                if (Boolean.valueOf(response.getResponse().get(0).getStatus())) {
                    DesignAmount = response.getResponse().get(0).getPayload().get(0).getDesignAmount();
                    SetupAmount = response.getResponse().get(0).getPayload().get(0).getSetupAmount();
                    VenueAmount = response.getResponse().get(0).getPayload().get(0).getVenueAmount();
                    MoreAmount = response.getResponse().get(0).getPayload().get(0).getMoreAmount();

//                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);


                    if (ComeFrom.equals("MoreFragment")) {
                        consultingSelectionAdapter = new ConsultingSelectionAdapter(ConsultingServiceActivity.this, data.getResponse().get(0).getPayload());
                        alphaAnimatorAdapter = new AlphaAnimatorAdapter(consultingSelectionAdapter, rv);
                        rv.setAdapter(alphaAnimatorAdapter);
                        sum = 0;
                        resetLabels();
                    } else {
                        consultingSelectionAdapter = new ConsultingSelectionAdapter(ConsultingServiceActivity.this, data.getResponse().get(0).getPayload());
                        alphaAnimatorAdapter = new AlphaAnimatorAdapter(consultingSelectionAdapter, rv);
                        rv.setAdapter(alphaAnimatorAdapter);
                        sum = 0;
                        resetLabels();
                        autoCompleteTextView.addTextChangedListener(textWatcher);
                    }
                } else {
                    autoCompleteTextView.addTextChangedListener(textWatcher);
                    GeneralUtils.ShowAlert(ConsultingServiceActivity.this, response.getResponse().get(0).getMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                GeneralUtils.ShowAlert(ConsultingServiceActivity.this, getString(R.string.VolleyTimeout));
                progressDialog.dismiss();

            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }


}
