package br.com.friendlydonations.views.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.com.friendlydonations.R;
import br.com.friendlydonations.managers.BaseActivity;
import br.com.friendlydonations.managers.BaseFragment;
import br.com.friendlydonations.views.adapters.HomeCardAdapter;
import br.com.friendlydonations.views.adapters.OwnDonationsAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by brunogabriel on 8/27/16.
 */
public class ProfileFragment extends BaseFragment {

    // Views
    protected View rootView;

    @BindView(R.id.recyclerViewProfile)
    protected RecyclerView recyclerView;

    protected LinearLayoutManager linearLayoutManager;
    protected OwnDonationsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // mainActivity = (MainActivity) getActivity();
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, rootView);
        //setHasOptionsMenu(true);
        initUI();
        return rootView;
    }

    @Override
    public void initUI() {
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        adapter = new OwnDonationsAdapter((BaseActivity) getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        List<Object> mItens = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            mItens.add(new String());
        }

        adapter.addAll(mItens);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflater.inflate(R.menu.menu_homefragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            /* case R.id.action_exit:
                mainActivity.doExit();
                return true; */
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
