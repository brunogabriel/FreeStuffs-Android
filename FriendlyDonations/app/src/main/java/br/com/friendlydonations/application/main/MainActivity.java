package br.com.friendlydonations.application.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import br.com.friendlydonations.R;
import br.com.friendlydonations.application.main.donate.DonateFragment;
import br.com.friendlydonations.application.main.home.HomeFragment;
import br.com.friendlydonations.application.main.notification.NotificationFragment;
import br.com.friendlydonations.application.main.profile.ProfileFragment;
import br.com.friendlydonations.shared.BaseActivity;
import br.com.friendlydonations.shared.widgets.NonSwipeableViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by brunogabriel on 24/04/17.
 */

public class MainActivity extends BaseActivity implements MainView {

    private static final float TAB_SELECTED = 1.0f;
    private static final float TAB_UNSELECTED = 0.54f;

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.tab_layout)
    protected TabLayout tabLayout;

    @BindView(R.id.app_bar_layout)
    protected AppBarLayout appBarLayout;

    @BindView(R.id.view_pager)
    protected NonSwipeableViewPager viewPager;

    @BindView(R.id.coordinatorLayout)
    protected CoordinatorLayout coordinatorLayout;

    private int[] tabIcons = {
            R.drawable.ic_tab_home,
            R.drawable.ic_tab_donation,
            R.drawable.ic_tab_profile,
            R.drawable.ic_tab_notification
    };

    // Fragments
    private CustomFragmentPagerAdapter customFragmentPagerAdapter;
    private HomeFragment homeFragment;
    private DonateFragment donateFragment;
    private ProfileFragment profileFragment;
    private NotificationFragment notificationFragment;

    // Presenter
    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupToolbar(toolbar, null, null, false, false);
        initializeUI();
        mainPresenter = new MainPresenter(this);
    }

    private void initializeUI() {
        startViewPager();
        startTabs();
    }

    private void startViewPager() {
        homeFragment = new HomeFragment();
        homeFragment.setFragmentTitle(getString(R.string.fragment_title_home));

        donateFragment = new DonateFragment();
        donateFragment.setFragmentTitle(getString(R.string.fragment_title_donate));

        profileFragment = new ProfileFragment();
        profileFragment.setFragmentTitle(getString(R.string.fragment_title_profile));

        notificationFragment = new NotificationFragment();
        notificationFragment.setFragmentTitle(getString(R.string.fragment_title_notifications));

        customFragmentPagerAdapter = new CustomFragmentPagerAdapter(getSupportFragmentManager());
        customFragmentPagerAdapter.addFragment(homeFragment);
        customFragmentPagerAdapter.addFragment(donateFragment);
        customFragmentPagerAdapter.addFragment(profileFragment);
        customFragmentPagerAdapter.addFragment(notificationFragment);
        viewPager.setAdapter(customFragmentPagerAdapter);
    }

    private void startTabs() {
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabIcons.length; i++) {
            tabLayout.getTabAt(i).setCustomView(createCustomView(tabIcons[i]));
        }

        tabLayout.getTabAt(0).getCustomView().setAlpha(TAB_SELECTED);
        setToolbarTitle(customFragmentPagerAdapter.getTitleAtPosition(0));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getCustomView().setAlpha(TAB_SELECTED);
                setToolbarTitle(customFragmentPagerAdapter.getTitleAtPosition(tab.getPosition()));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getCustomView().setAlpha(TAB_UNSELECTED);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                tab.getCustomView().setAlpha(TAB_SELECTED);
            }
        });
    }

    private View createCustomView(int iconDrawable) {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_material_tab, (ViewGroup) findViewById(android.R.id.content), false);
        ((ImageView) view.findViewById(R.id.image_tab)).setImageResource(iconDrawable);
        return view;
    }

}
