package kestone.com.kestone.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import kestone.com.kestone.MODEL.Consulting.ConsultingInfoResponse;
import kestone.com.kestone.MODEL.Consulting.InfoPayload;
import kestone.com.kestone.R;

public class AboutConsultingServices extends AppCompatActivity {

    ConsultingInfoResponse data;
    List<InfoPayload> dataList;
    TextView body1, body2, body3, body4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_consulting_services);

        body1 = (TextView) findViewById(R.id.body1);
        body2 = (TextView) findViewById(R.id.body2);
        body3 = (TextView) findViewById(R.id.body3);
        body4 = (TextView) findViewById(R.id.body4);

        if (getIntent() != null) {
            data = (ConsultingInfoResponse) getIntent().getSerializableExtra("data");
            dataList = data.getResponse().get(0).getPayload();
            if (dataList != null && dataList.size() > 0) {
                body1.setText(Html.fromHtml(dataList.get(0).getDesc()));
                body2.setText(Html.fromHtml(dataList.get(1).getDesc()));
                body3.setText(Html.fromHtml(dataList.get(2).getDesc()));
                body4.setText(Html.fromHtml(dataList.get(3).getDesc()));
            }
        }

    }
}
