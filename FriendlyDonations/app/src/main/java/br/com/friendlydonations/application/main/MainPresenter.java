package br.com.friendlydonations.application.main;

import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import br.com.friendlydonations.adapters.BadgeTabViewAdapter;
import br.com.friendlydonations.application.main.home.HomeFragment;

/**
 * Created by brunogabriel on 10/02/17.
 */
public class MainPresenter {

    private MainView mainView;
    private List<Fragment> fragmentList = new ArrayList<>();

    public MainPresenter(MainView mainView) {
        this.mainView = mainView;
        initFragmentList();
    }

    public void init(BadgeTabViewAdapter dynamicTabViewPageAdapter) {
        if (dynamicTabViewPageAdapter != null) {
            for (int i = 0; i < fragmentList.size(); ++i) {
                dynamicTabViewPageAdapter.addFragment(fragmentList.get(i));
            }

            mainView.setupTabStructure(getFragmentListSize(), dynamicTabViewPageAdapter);
        }
    }

    public int getFragmentListSize() {
        return fragmentList.size();
    }

    private void initFragmentList() {
        fragmentList.add(new HomeFragment());
        fragmentList.add(new HomeFragment());
        fragmentList.add(new HomeFragment());
        fragmentList.add(new HomeFragment());
    }
}
