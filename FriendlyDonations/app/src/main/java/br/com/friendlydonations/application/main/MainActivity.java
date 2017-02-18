package br.com.friendlydonations.application.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import br.com.friendlydonations.R;
import br.com.friendlydonations.shared.BaseActivity;
import br.com.friendlydonations.shared.adapter.DynamicTabPageAdapter;
import br.com.friendlydonations.shared.events.NotificationBusEvent;
import br.com.friendlydonations.shared.widgets.NonSwipeableViewPager;
import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;

import static br.com.friendlydonations.application.main.MainPresenter.TAB_ACTIVATED;
import static br.com.friendlydonations.application.main.MainPresenter.TAB_DESACTIVATED;

/**
 * Created by brunogabriel on 16/02/17.
 */

public class MainActivity extends BaseActivity implements MainView {
    @BindArray(R.array.array_tab_main)
    String[] tabArrayTitles;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.view_pager)
    NonSwipeableViewPager viewPager;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    MainPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        setupToolbar(toolbar, tabArrayTitles[0], "", false, false);
        presenter = new MainPresenter(this, new DynamicTabPageAdapter(getSupportFragmentManager(), this));
        presenter.initialize();
    }

    @Override
    public void setAdapterSettings(DynamicTabPageAdapter dynamicTabPageAdapter) {
        viewPager.setOffscreenPageLimit(dynamicTabPageAdapter.getCount());
        viewPager.setAdapter(dynamicTabPageAdapter);
        tabLayout.setupWithViewPager(viewPager);
        presenter.setupTab(tabLayout.getTabCount());
    }

    @Override
    public void selectTab(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void setCustomView(int position, FrameLayout rootView) {
        tabLayout.getTabAt(position).setCustomView(rootView);
    }

    @Override
    public void addTabSelectedListener() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getCustomView().setAlpha(TAB_ACTIVATED);
                selectTab(tab);
                changeToolbarTitle(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getCustomView().setAlpha(TAB_DESACTIVATED);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                tab.getCustomView().setAlpha(TAB_DESACTIVATED);
            }
        });
    }

    @Override
    public void changeTabAlpha(float alphaValue) {
        tabLayout.getTabAt(tabLayout.getSelectedTabPosition()).getCustomView().setAlpha(alphaValue);
    }

    @Override
    public void changeToolbarTitle(int position) {
        toolbar.setTitle(tabArrayTitles[position]);
    }

    @Subscribe()
    public void onNotificationReceiver(NotificationBusEvent notificationBusEvent) {
        if (presenter != null) {
            presenter.onNotificationReceiver(notificationBusEvent.getNumberOfNotifications());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
