package br.com.friendlydonations.views.activities;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
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
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
    };

    private DynamicTabViewPageAdapter viewPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // Apply FAB Color
        fabDonation.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimaryDark));
        initUI();
    }

    @Override
    public void initUI() {
        setupToolbar(toolbar, "MAIN", null, false, false);
        setupTabs(viewPager);

        //page change listener
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBar.getLayoutParams();
                AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
                behavior.onNestedFling(coordinatorLayout, appBar, null, 0, -1000, true);
                fabDonation.setVisibility(position == 0 ? View.VISIBLE : View.INVISIBLE);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

    }

    private void setupTabs(ViewPager viewPager) {
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
                        // TODO: apply 100 % alpha in active
                        // TODO: apply 50 % alpha in others
                        // getSupportActionBar().setTitle(tabArrayNames[tab.getPosition()]);
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        // TODO: apply 100 % alpha in active
                        // TODO: apply 50 % alpha in others
                        // getSupportActionBar().setTitle(tabArrayNames[tab.getPosition()]);
                    }
                });

                // Custom View
                for (int i = 0; i < tabArrayNames.length; ++i) {
                    TextView mCustomTab = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
                    mCustomTab.setText(tabArrayNames[i]);
                    mCustomTab.setCompoundDrawablesWithIntrinsicBounds(0, tabIcons[i], 0, 0);
                    tabs.getTabAt(i).setCustomView(mCustomTab);
                }

            } catch (ArrayIndexOutOfBoundsException e) {}

            // viewPager.setOffscreenPageLimit(viewPagerAdapter.getCount());
        }
    }

    public void changeTab() {
        viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
    }
}