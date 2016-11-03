package br.com.friendlydonations.views.fragments;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import br.com.friendlydonations.R;
import br.com.friendlydonations.managers.BaseFragment;
import br.com.friendlydonations.models.CategoryModel;
import br.com.friendlydonations.models.DonationModel;
import br.com.friendlydonations.models.LoaderModel;
import br.com.friendlydonations.views.adapters.HomeDonationsAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by brunogabriel on 8/27/16.
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recyclerView)
    protected RecyclerView recyclerView;

    protected StaggeredGridLayoutManager gridLayoutManager;
    protected HomeDonationsAdapter adapter;

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
        adapter = new HomeDonationsAdapter(getActivity(), new LoaderModel());
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        // Swipe to Refresh Layout
        startSwipeLayout();

        // Fake Objects
        List<Object> mItens = new ArrayList<>();
        List<Object> mCategories = new ArrayList<>();

        mCategories.add(new CategoryModel("Todas", true));
        mCategories.add(new CategoryModel("Alimentos", false));
        mCategories.add(new CategoryModel("Animais", false));
        mCategories.add(new CategoryModel("Eletrônicos", false));
        mCategories.add(new CategoryModel("Móveis", false));
        mCategories.add(new CategoryModel("Roupas", false));
        mCategories.add(new CategoryModel("Serviços", false));

        mItens.add(mCategories);

        for (int i = 0; i < 10; ++i) {
            mItens.add(new DonationModel());
        }

        Observable.timer(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(oObject -> {
                    adapter.addAll(mItens);
                });
    }

    private void startSwipeLayout() {
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(() -> {
            // TODO: refreshItens()
            Observable.timer(1, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(oObject -> {
                        swipeRefreshLayout.setRefreshing(false);
                    });
        });
    }
}
