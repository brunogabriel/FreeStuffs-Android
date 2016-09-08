package br.com.friendlydonations.views.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.friendlydonations.R;
import br.com.friendlydonations.managers.BaseActivity;
import br.com.friendlydonations.views.adapters.DynamicTabViewPageAdapter;
import br.com.friendlydonations.views.fragments.HomeFragment;
import br.com.friendlydonations.views.fragments.MapFragment;
import br.com.friendlydonations.views.fragments.NotificationsFragment;
import br.com.friendlydonations.views.fragments.ProfileFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by brunogabriel on 8/27/16.
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar) protected Toolbar toolbar;
    @BindView(R.id.viewpager) protected ViewPager viewPager;
    @BindView(R.id.tabs) protected TabLayout tabs;
    @BindView(R.id.fabdonation) protected FloatingActionButton fabDonation;
    @BindView(R.id.coordinatorLayout) protected CoordinatorLayout coordinatorLayout;
    @BindView(R.id.appBar) protected AppBarLayout appBar;

    // Adapter
    private String []tabArrayNames;
    private int[]tabIcons = {
            R.drawable.ic_home_tab,
            R.drawable.ic_location_tab,
            R.drawable.ic_profile_tab,
            R.drawable.ic_notifications_tab
    };

    private DynamicTabViewPageAdapter viewPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initUI();
    }

    @Override
    public void setupTypefaces() {}

    @Override
    public void initUI() {
        setupToolbar(toolbar, "MAIN", null, false, false);
        setupTabs(viewPager);
        
        //page change listener
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                /* CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBar.getLayoutParams();
                AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
                behavior.onNestedFling(coordinatorLayout, appBar, null, 0, -1000, true);
                fabDonation.setVisibility(position == 0 ? View.VISIBLE : View.INVISIBLE); */
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

    }

    private void setupTabs(final ViewPager viewPager) {
        viewPagerAdapter = new DynamicTabViewPageAdapter(getSupportFragmentManager());
        if(viewPager!=null) {

            // Lista de fragments disponiveis
            tabArrayNames = getResources().getStringArray(R.array.array_tab_main);
            try {

                // Adding Fragments
                viewPagerAdapter.addFragment(new HomeFragment(), tabArrayNames[0] );
                viewPagerAdapter.addFragment(new MapFragment(), tabArrayNames[1]);
                viewPagerAdapter.addFragment(new ProfileFragment(), tabArrayNames[2]);
                viewPagerAdapter.addFragment(new NotificationsFragment(), tabArrayNames[3]);
                viewPager.setAdapter(viewPagerAdapter);

                // add tabs
                tabs.setupWithViewPager(viewPager);
                tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {

                        if (tab.getIcon()!=null) {
                            tab.getIcon().setAlpha(255);
                        }

                        viewPager.setCurrentItem(tab.getPosition());
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        if (tab.getIcon()!=null) {
                            tab.getIcon().setAlpha(100);
                        }
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        if (tab.getIcon()!=null) {
                            tab.getIcon().setAlpha(255);
                        }
                    }
                });

                // Custom View
                for (int i = 0; i < tabIcons.length; ++i) {
                    tabs.getTabAt(i).setIcon(tabIcons[i]);

                    if (i!=0) {
                        tabs.getTabAt(i).getIcon().setAlpha(100);
                    }
                }

            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }

            // viewPager.setOffscreenPageLimit(viewPagerAdapter.getCount());
        }
    }

    public void changeTab() {
        viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
    }
}