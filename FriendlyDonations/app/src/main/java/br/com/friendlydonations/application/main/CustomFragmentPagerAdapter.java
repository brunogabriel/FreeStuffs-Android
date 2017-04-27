package br.com.friendlydonations.application.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import br.com.friendlydonations.shared.BaseFragment;

/**
 * Created by brunogabriel on 26/04/17.
 */

public class CustomFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> fragments = new ArrayList<>();

    public CustomFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void addFragment(BaseFragment fragment) {
        fragments.add(fragment);
    }

    public String getTitleAtPosition(int position) {
        return position < getCount() ? ((BaseFragment) getItem(position)).getFragmentTitle() : "";
    }
}
