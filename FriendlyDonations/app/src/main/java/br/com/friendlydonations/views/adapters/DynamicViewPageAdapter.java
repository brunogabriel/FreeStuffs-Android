package br.com.friendlydonations.views.adapters;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brunogabriel on 8/27/16.
 */
public class DynamicViewPageAdapter extends PagerAdapter {

    protected List<View> views;

    public DynamicViewPageAdapter(List<View> mViews) {
        this.views = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return views == null ? 0: views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View) object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ((ViewPager) container).addView(views.get(position), 0);
        return views.get(position);
    }
}
