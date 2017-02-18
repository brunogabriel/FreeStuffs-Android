package br.com.friendlydonations.application.main;

import android.support.design.widget.TabLayout;

import com.annimon.stream.Stream;

import br.com.friendlydonations.application.donate.DonateFragment;
import br.com.friendlydonations.shared.adapter.DynamicTabPageAdapter;
import br.com.friendlydonations.shared.widgets.BadgeImageView;

/**
 * Created by brunogabriel on 16/02/17.
 */

public class MainPresenter {
    public static final float TAB_ACTIVATED = 1f;
    public static final float TAB_DESACTIVATED = .54f;

    private MainView view;
    private DynamicTabPageAdapter adapter;
    private BadgeImageView badgeNotification;

    public MainPresenter(MainView view, DynamicTabPageAdapter dynamicTabPageAdapter) {
        this.view = view;
        this.adapter = dynamicTabPageAdapter;
    }

    public void initialize() {
        adapter.addFragment(new DonateFragment());
        adapter.addFragment(new DonateFragment());
        adapter.addFragment(new DonateFragment());
        adapter.addFragment(new DonateFragment());
        view.setAdapterSettings(adapter);
    }

    public void setupTab(int tabCount) {
        Stream.range(0, tabCount)
                .forEach(i -> {
                    BadgeImageView badgeImageView = adapter.badgeImageView(i);
                    if (i == tabCount - 1) {
                        badgeNotification = badgeImageView;
                    }
                    badgeImageView.getRootView().setAlpha(TAB_DESACTIVATED);
                    view.setCustomView(i, badgeImageView.getRootView());
                });

        view.addTabSelectedListener();
        view.changeTabAlpha(TAB_ACTIVATED);
    }

    public void onNotificationReceiver(int numberOfNotifications) {
        badgeNotification.setOrChangeNotificationCount(numberOfNotifications, true);
    }
}
