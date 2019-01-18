package kestone.com.pocketevents.UI;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import kestone.com.pocketevents.Adapters.MoreActivity.MoreAdapterLeft;
import kestone.com.pocketevents.Adapters.MoreActivity.MoreAdapterRight;
import kestone.com.pocketevents.Adapters.MorefilterActivity.MoreFilterAdapterRight;
import kestone.com.pocketevents.MODEL.Manager.MySingleton;
import kestone.com.pocketevents.R;
import kestone.com.pocketevents.Utilities.StorageUtilities;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MoreActivity extends AppCompatActivity implements View.OnClickListener, MoreAdapterLeft.MoreAdapterLeftCallBack, MoreAdapterRight.MoreAdapterRightcallback{
    RecyclerView rvLeft, rvRight;
    TextView select, clear;
    TextView label;
    StorageUtilities storage;
    boolean noSelection = true;
    MoreAdapterLeft moreAdapterLeft;
    MoreAdapterRight moreAdapterRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        noSelection = true;
        rvLeft = (RecyclerView) findViewById(R.id.more_rv_left);
        rvRight = (RecyclerView) findViewById(R.id.more_rv_right);
        select = (TextView) findViewById(R.id.moreSelect);
        select.setText("Select "+MySingleton.getInstance().getLabelMore());
        clear = (TextView) findViewById(R.id.moreClear);
        select.setOnClickListener(this);
        clear.setOnClickListener(this);
        storage = new StorageUtilities(MoreActivity.this);
        label = (TextView)findViewById(R.id.Label);
        label.setText(MySingleton.getInstance().getLabelMore());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView.LayoutManager layoutManagerR = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rvLeft.setLayoutManager(layoutManager);
        moreAdapterLeft = new MoreAdapterLeft(MoreActivity.this,0);
        moreAdapterRight = new MoreAdapterRight(MoreActivity.this,0,0,0);
        rvLeft.setAdapter(moreAdapterLeft);
        rvRight.setLayoutManager(layoutManagerR);
        rvRight.setAdapter(moreAdapterRight);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.moreSelect :
                storage.storeMoreData(MySingleton.getInstance().getMoreResponse());
                finish();
                break;
            case R.id.moreClear :
                noSelection = true;

                final Dialog clearAll = new Dialog(MoreActivity.this);
                clearAll.requestWindowFeature(Window.FEATURE_NO_TITLE);
                clearAll.setCancelable(true);
                clearAll.setContentView(R.layout.dialog_clear_all_filter);
                TextView no = (TextView) clearAll.findViewById(R.id.no);
                TextView yes = (TextView) clearAll.findViewById(R.id.yes);
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        storage.storeMoreData(storage.loadMoreDataDefault());
                        MySingleton.getInstance().setMoreResponse(storage.loadMoreData());
                        finish();
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
        }
    }


    @Override
    public void OnClickLeft(int position) {
        moreAdapterLeft = new MoreAdapterLeft(MoreActivity.this,position);
        rvLeft.setAdapter(moreAdapterLeft);

        moreAdapterRight = new MoreAdapterRight(MoreActivity.this,0,position,1);
        rvRight.setAdapter(moreAdapterRight);

    }

    @Override
    public void OnClickRight(int POSITION, int position) {
        moreAdapterRight.getSelectedItems();
        moreAdapterRight = new MoreAdapterRight(MoreActivity.this,position,POSITION,1);
        rvRight.setAdapter(moreAdapterRight);
        noSelection = false;
    }

    @Override
    public void onBackPressed() {
        if (noSelection){
            MoreActivity.super.onBackPressed();
        }else {
            new AlertDialog.Builder(MoreActivity.this)
                    .setTitle("Save")
                    .setMessage("Save Selections?")
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            MoreActivity.super.onBackPressed();
                        }
                    })
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            storage.storeMoreData(MySingleton.getInstance().getMoreResponse());
                            finish();
                        }
                    }).create().show();
        }
    }
}
