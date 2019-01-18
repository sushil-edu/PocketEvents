package kestone.com.pocketevents.Adapters.FAQActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import kestone.com.pocketevents.R;

/**
 * Created by karan on 8/3/2017.
 */

public class FAQAdapter extends BaseAdapter {
    String [] ques = {"HOW CAN I CHANGE MY SHIPPING ADDRESS?","HOW DO I ACTIVATE MY ACCOUNT?","WHAT DO YOU MEAN BY POINTS? HOW DO I EARN IT?","WHY IS THERE A CHECKOUT LIMIT? / WHAT ARE ALL THE CHECKOUT LIMITS?",
            "HOW CAN I TRACK MY ORDERS & PAYMENT?"};

    String [] ans = {"By default, the last used shipping address will be saved into your Sample Store account. When you are checking out your order, the default shipping address will be displayed and you have the option to amend it if you need to.",
            "The instructions to activate your account will be sent to your email once you have submitted the registration form. If you did not receive this email, your email service provider’s mailing software may be blocking it. You can try checking your junk / spam folder or contact us at help@samplestore.com",
            "Because you are important to us, we want to know what you think about the products. As an added value, every time you rate the products you earn points which go straight to your account. 1 point is added to your account for every review that you give. You will need those points in order to redeem the sample products. So keep rating the products to keep earning points!",
            "Sample Store is a popular spot and gets lots of shoppers at a time. These limits are in place to make sure everyone has a good time trying and purchasing their products. So...\n" +
                    "\n" +
                    "- Each account is entitled to only one (1) order of up to 4 different samples per day\n" +
                    "\n" +
                    "- Each account is entitled to only one (1) redemption per sample.\n" +
                    "\n" +
                    "- Your account must have sufficient points before you can checkout the sample products.\n" +
                    "\n" +
                    "- Kindly clear all pending payments before another checkout.",
            "After logging into your account, the status of your checkout history can be found under Order History. For orders via registered postage, a tracking number (article tracking number) will be given to you after the receipt given from Singapore Post Limited (SingPost)."};

    private Context mcontext;
    private LayoutInflater inflater = null;



    public FAQAdapter(Context context) {
        mcontext = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return ques.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertview, ViewGroup viewGroup) {
        View view = convertview;


        if (convertview == null) {
            view = inflater.inflate(R.layout.item_faq, viewGroup,false);}

        TextView textView1 = (TextView)view.findViewById(R.id.faq_ques);
        TextView textView2 = (TextView)view.findViewById(R.id.faq_ans);

        textView1.setText("Q. " + ques[i]);
        textView2.setText("A. "+ ans[i]);
        return view;
    }
}
