package br.com.friendlydonations.application.main;

import android.support.design.widget.TabLayout;
import android.widget.FrameLayout;

import br.com.friendlydonations.shared.adapter.DynamicTabPageAdapter;

/**
 * Created by brunogabriel on 16/02/17.
 */
public interface MainView {
    void setAdapterSettings(DynamicTabPageAdapter dynamicTabPageAdapter);
    void selectTab(TabLayout.Tab tab);
    void setCustomView(int position, FrameLayout rootView);
    void addTabSelectedListener();
    void changeTabAlpha(float alphaValue);
    void changeToolbarTitle(int position);
}
