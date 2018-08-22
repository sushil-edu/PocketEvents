package kestone.com.kestone.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import kestone.com.kestone.Adapters.CompareActivity.CompareAdapter;
import kestone.com.kestone.MODEL.Manager.CompareLeftRight;
import kestone.com.kestone.MODEL.Manager.MySingleton;
import kestone.com.kestone.R;
import kestone.com.kestone.Utilities.CONSTANTS;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CompareActivity extends AppCompatActivity implements View.OnClickListener, CompareAdapter.CompareAdapterCallback {

    ListView listView;
    CompareAdapter adapter;

    ToggleButton toggle;

    boolean bool= true;

    TextView back, selec1, selec2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);
        setupAdapter();
        back = (TextView) findViewById(R.id.compareBack);
        selec1 = (TextView) findViewById(R.id.CompareListSelect1);
        selec2 = (TextView) findViewById(R.id.CompareListSelect2);
        selec1.setOnClickListener(this);
        selec2.setOnClickListener(this);

        back.setOnClickListener(this);
        toggle = (ToggleButton)findViewById(R.id.toggle);
        listView = (ListView)findViewById(R.id.compareList);
        listView.setAdapter(adapter);

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                     adapter = new CompareAdapter(CompareActivity.this, MySingleton.getInstance().getCompare().get(0),MySingleton.getInstance().getCompare().get(1),b);
                    listView.setAdapter(adapter);
                }else {
                     adapter = new CompareAdapter(CompareActivity.this, MySingleton.getInstance().getCompare().get(1),MySingleton.getInstance().getCompare().get(2),b);
                    listView.setAdapter(adapter);
                }
            }
        });


    }








    void setupAdapter(){
        switch (MySingleton.getInstance().getCompareCount()){
            case 0 :
                break;
            case 1 :
                break;
            case 2 :
                if (MySingleton.getInstance().getCompare().get(0)!= null && MySingleton.getInstance().getCompare().get(1)!=null){
                    adapter = new CompareAdapter(CompareActivity.this, MySingleton.getInstance().getCompare().get(0),MySingleton.getInstance().getCompare().get(1),bool);

                }else if (MySingleton.getInstance().getCompare().get(1) !=null && MySingleton.getInstance().getCompare().get(2)!=null){
                    adapter = new CompareAdapter(CompareActivity.this, MySingleton.getInstance().getCompare().get(1),MySingleton.getInstance().getCompare().get(2),bool);

                }else {
                    adapter = new CompareAdapter(CompareActivity.this, MySingleton.getInstance().getCompare().get(0),MySingleton.getInstance().getCompare().get(2),bool);
                }
                break;
            case 3 :
                adapter = new CompareAdapter(CompareActivity.this, MySingleton.getInstance().getCompare().get(0),MySingleton.getInstance().getCompare().get(1),bool);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        CompareLeftRight compareLeftRight = new CompareLeftRight();
        switch (view.getId()){
            case R.id.compareBack :
                onBackPressed();
                break;
            case R.id.CompareListSelect1 :
                intent = new Intent();
                intent.putExtra("hasCompare",true);
                compareLeftRight = (CompareLeftRight) adapter.getItem(0);
                intent.putExtra("compare",compareLeftRight.getLeft());
                intent.putExtra("compareHall",compareLeftRight.getHallLeft());
                setResult(CONSTANTS.RESULTCODE_COMPARE,intent);
                finish();
                break;
            case R.id.CompareListSelect2 :
                intent = new Intent();
                intent.putExtra("hasCompare",true);
                compareLeftRight = (CompareLeftRight) adapter.getItem(0);
                intent.putExtra("compare",compareLeftRight.getRight());
                intent.putExtra("compareHall",compareLeftRight.getHallRight());
                setResult(CONSTANTS.RESULTCODE_COMPARE,intent);
                finish();
                break;
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("compare",999);
        intent.putExtra("hasCompare",false);
        setResult(CONSTANTS.RESULTCODE_COMPARE,intent);
        finish();
        super.onBackPressed();
    }

    @Override
    public void onNextClicked() {
        toggle.toggle();
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }




}
