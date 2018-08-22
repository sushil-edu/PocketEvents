package kestone.com.kestone.Adapters.TutorialActivity;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import kestone.com.kestone.R;

/**
 * Created by karan on 7/5/2017.
 */

public class TutorialPagerAdapter extends PagerAdapter {
    Context mContext;
    LayoutInflater mLayoutInflater;
    int [] mResources;
    String [] mStrings, mTitle;

    public TutorialPagerAdapter(Context context, int [] data, String [] text, String [] title) {
        mContext = context;
        mResources = data;
        mTitle = title;
        mStrings = text;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mResources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.tutorial_pager_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.sliderImage);
        TextView titleText = (TextView) itemView.findViewById(R.id.sliderTitle);
        TextView stringText = (TextView) itemView.findViewById(R.id.sliderText);

        imageView.setImageResource(mResources[position]);
        titleText.setText(mTitle[position]);
        stringText.setText(mStrings[position]);



        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
