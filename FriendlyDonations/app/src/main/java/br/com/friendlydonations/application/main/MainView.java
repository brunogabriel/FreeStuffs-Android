package br.com.friendlydonations.application.main;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.RelativeLayout;

/**
 * Created by brunogabriel on 10/02/17.
 */
public interface MainView {
    void setToolbarTitle(String title);
    void changeTab(TabLayout.Tab tab);
    void setupTabStructure(int offScreenLimit, FragmentPagerAdapter fragmentPagerAdapter);
    RelativeLayout inflaterRelativeBadge();
}
