package br.com.friendlydonations.shared.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;

/**
 * Created by brunogabriel on 17/02/17.
 */

public class NoSwipeViewPager extends ViewPager {

    public NoSwipeViewPager(Context context) {
        super(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

}
