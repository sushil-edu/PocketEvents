package kestone.com.pocketevents.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import kestone.com.pocketevents.Adapters.FAQActivity.FAQAdapter;
import kestone.com.pocketevents.R;

public class FAQActivity extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        listView = (ListView)findViewById(R.id.faq_list);
        FAQAdapter adapter = new FAQAdapter(this);
        listView.setAdapter(adapter);
    }


}
