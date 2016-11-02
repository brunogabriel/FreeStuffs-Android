package br.com.friendlydonations.views.fragments;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.com.friendlydonations.R;
import br.com.friendlydonations.managers.BaseFragment;
import br.com.friendlydonations.models.CategoryModel;
import br.com.friendlydonations.models.DonationModel;
import br.com.friendlydonations.views.adapters.HomeCardAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by brunogabriel on 8/27/16.
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    protected RecyclerView recyclerView;

    protected StaggeredGridLayoutManager gridLayoutManager;
    protected HomeCardAdapter adapter;

    // Views
    protected View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, rootView);
        initUI();
        return rootView;
    }

    @Override
    public void initUI() {
        gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        adapter = new HomeCardAdapter(getActivity());
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        List<Object> mItens = new ArrayList<>();
        List<Object> mCategories = new ArrayList<>();

        for (int i = 0; i < 10; ++i) {
            mCategories.add(new CategoryModel("", i == 0 ? true: false));
        }

        mItens.add(mCategories);

        for (int i = 0; i < 10; ++i) {
            mItens.add(new DonationModel());
        }

        adapter.addAll(mItens);
    }
}
