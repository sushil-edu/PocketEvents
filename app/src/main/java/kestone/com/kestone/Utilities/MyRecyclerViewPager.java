package kestone.com.kestone.Utilities;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

/**
 * Created by karan on 7/7/2017.
 */

public class MyRecyclerViewPager extends RecyclerViewPager {
    public MyRecyclerViewPager(Context context) {
        super(context);
    }

    public MyRecyclerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecyclerViewPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_DOWN && this.getScrollState() == RecyclerViewPager.SCROLL_STATE_SETTLING) {
            this.stopScroll();
        }
        return super.onInterceptTouchEvent(e);
    }
}
