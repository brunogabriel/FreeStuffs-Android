package br.com.friendlydonations.application.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import br.com.friendlydonations.R;
import br.com.friendlydonations.adapters.BadgeTabViewAdapter;
import br.com.friendlydonations.manager.BaseActivity;
import br.com.friendlydonations.widgets.BadgeView;
import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import uk.co.chrisjenx.calligraphy.TypefaceUtils;

/**
 * Created by brunogabriel on 10/02/17.
 */

public class MainActivity extends BaseActivity implements MainView {

    private static float ACTIVATED_TAB = 1.0f;
    private static float DESACTIVATED_TAB = 0.38f;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindArray(R.array.array_tab_main)
    protected String []tabArrayNames;

    protected int []tabIcons = {
            R.drawable.ic_home_tab,
            R.drawable.ic_donation_tab,
            R.drawable.ic_profile_tab,
            R.drawable.ic_notifications_tab
    };
    
    private MainPresenter mainPresenter;
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        setupToolbar(toolbar, "", null, false, false);
        applyTypefaceToolbar(toolbar, TypefaceUtils.load(getAssets(), "fonts/Montserrat-Regular.ttf"));
        mainPresenter = new MainPresenter(this);
        mainPresenter.init(new BadgeTabViewAdapter(getSupportFragmentManager()));
    }

    @Override
    public void setToolbarTitle(String title) {
        toolbar.setTitle(title);
    }

    @Override
    public void changeTab(TabLayout.Tab tab) {
        if (tab != null) {
            viewPager.setCurrentItem(tab.getPosition());
            setToolbarTitle(tabArrayNames[tab.getPosition()]);
        }
    }

    @Override
    public void setupTabStructure(int offScreenLimit, FragmentPagerAdapter fragmentPagerAdapter) {
        viewPager.setOffscreenPageLimit(offScreenLimit);
        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getCustomView().setAlpha(ACTIVATED_TAB);
                changeTab(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getCustomView().setAlpha(DESACTIVATED_TAB);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                tab.getCustomView().setAlpha(ACTIVATED_TAB);
            }
        });

        for (int i = 0; i < tabIcons.length; ++i) {
            RelativeLayout relativeBadge = inflaterRelativeBadge();
            BadgeView badgeView = new BadgeView(relativeBadge);
            Picasso.with(this).load(tabIcons[i]).into(badgeView.getBadgeImage());
            tabLayout.getTabAt(i).setCustomView(relativeBadge);
            if (i != 0) tabLayout.getTabAt(i).getCustomView().setAlpha(DESACTIVATED_TAB);
        }

        setToolbarTitle(tabArrayNames[0]);
    }

    @Override
    public RelativeLayout inflaterRelativeBadge() {
        return (RelativeLayout)
                LayoutInflater.from(this).inflate(R.layout.widget_badgeview, null, false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
