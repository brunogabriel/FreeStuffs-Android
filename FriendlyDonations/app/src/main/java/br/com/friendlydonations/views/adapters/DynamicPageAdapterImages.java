package br.com.friendlydonations.views.adapters;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by brunogabriel on 9/10/16.
 */
public class DynamicPageAdapterImages extends PagerAdapter {

    private List<View> mViews;

    public DynamicPageAdapterImages(List<View> mViews) {
        this.mViews = mViews;
    }

    @Override
    public int getCount() {
        return this.mViews == null ? 0 : this.mViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View) object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViews.get(position), 0);
        return mViews.get(position);
    }
}