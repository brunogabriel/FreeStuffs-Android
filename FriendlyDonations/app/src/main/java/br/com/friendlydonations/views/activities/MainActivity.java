package br.com.friendlydonations.views.activities;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import br.com.friendlydonations.R;
import br.com.friendlydonations.managers.BaseActivity;
import br.com.friendlydonations.utils.TypefaceMaker;
import br.com.friendlydonations.views.adapters.DynamicTabViewPageAdapter;
import br.com.friendlydonations.views.fragments.HomeFragment;
import br.com.friendlydonations.views.fragments.MapLocationFragment;
import br.com.friendlydonations.views.fragments.NotificationsFragment;
import br.com.friendlydonations.views.fragments.ProfileFragment;
import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by brunogabriel on 8/27/16.
 */
public class MainActivity extends BaseActivity {

    protected Typeface mMonserratRegular;

    @BindView(R.id.toolbar) protected Toolbar toolbar;
    @BindView(R.id.viewpager) protected ViewPager viewPager;
    @BindView(R.id.tabs) protected TabLayout tabs;
    @BindView(R.id.fabdonation) protected FloatingActionButton fabDonation;
    @BindView(R.id.coordinatorLayout) protected CoordinatorLayout coordinatorLayout;
    @BindView(R.id.appBar) protected AppBarLayout appBar;
    @BindArray(R.array.array_tab_main) protected String []tabArrayNames;

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
        setupTypefaces();
    }

    @Override
    public void setupTypefaces() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                mMonserratRegular = TypefaceMaker.createTypeFace(MainActivity.this, TypefaceMaker.FontFamily.MontserratRegular);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                applyTypefaceToolbar(toolbar, mMonserratRegular);
            }
        }.execute();
    }

    @Override
    public void initUI() {
        setupToolbar(toolbar, "", null, false, false);
        setupTabs(viewPager);
    }

    private void setupTabs(final ViewPager viewPager) {
        viewPagerAdapter = new DynamicTabViewPageAdapter(getSupportFragmentManager());
        if(viewPager!=null) {
            toolbar.setTitle(tabArrayNames[0]);
            try {
                // Adding Fragments
                viewPagerAdapter.addFragment(new HomeFragment(), tabArrayNames[0] );
                viewPagerAdapter.addFragment(new MapLocationFragment(), tabArrayNames[1]);
                viewPagerAdapter.addFragment(new ProfileFragment(), tabArrayNames[2]);
                viewPagerAdapter.addFragment(new NotificationsFragment(), tabArrayNames[3]);
                viewPager.setAdapter(viewPagerAdapter);
                tabs.setupWithViewPager(viewPager);
                tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        if (tab.getIcon()!=null) {
                            tab.getIcon().setAlpha(255);
                        }
                        changeTab(tab);
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

                    if (i != 0) {
                        tabs.getTabAt(i).getIcon().setAlpha(100);
                    }
                }

            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    public void changeTab(TabLayout.Tab tab) {
        if (tab != null) {
            viewPager.setCurrentItem(tab.getPosition());
            toolbar.setTitle(tabArrayNames[tab.getPosition()]);
        }
    }
}