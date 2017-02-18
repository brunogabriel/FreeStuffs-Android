package br.com.friendlydonations.shared.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import br.com.friendlydonations.R;
import br.com.friendlydonations.shared.widgets.BadgeImageView;

/**
 * Created by brunogabriel on 16/02/17.
 */

public class DynamicTabPageAdapter extends FragmentPagerAdapter {
    private static final int[]tabIcons = new int[]{
            R.drawable.ic_tab_house,
            R.drawable.ic_tab_donation,
            R.drawable.ic_tab_profile,
            R.drawable.ic_tab_notifications
    };
    private final Context context;
    private List<Fragment> fragmentList = new ArrayList<>();

    public DynamicTabPageAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        this.context = context;
    }

    public void addFragment(Fragment fragment) {
        fragmentList.add(fragment);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

    public BadgeImageView badgeImageView (int position) {
        BadgeImageView badgeImageView = new BadgeImageView(context);
        badgeImageView.setupBadge(tabIcons[position]);
        return badgeImageView;
    }
}
