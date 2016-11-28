package br.com.friendlydonations.views.fragments;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import br.com.friendlydonations.R;
import br.com.friendlydonations.managers.BaseFragment;
import br.com.friendlydonations.models.donation.DonationModel;
import br.com.friendlydonations.models.LoaderModel;
import br.com.friendlydonations.models.ProfileDonationModel;
import br.com.friendlydonations.views.adapters.ProfileDonationAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by brunogabriel on 8/27/16.
 */
public class ProfileFragment extends BaseFragment {

    // Views
    protected View rootView;

    @BindView(R.id.recyclerView)
    protected RecyclerView recyclerView;

    //@BindView(R.id.swipeRefreshLayout)
    protected SwipeRefreshLayout swipeRefreshLayout;

    protected LinearLayoutManager linearLayoutManager;
    protected ProfileDonationAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, rootView);
        initUI();
        return rootView;
    }

    @Override
    public void initUI() {
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        adapter = new ProfileDonationAdapter(getActivity(), new LoaderModel());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        List<Object> items = new ArrayList<>();
        items.add(new ProfileDonationModel());

        List<Object> mDonations = new ArrayList<>();
        for (int i = 0; i < 5; ++i) {
            mDonations.add(new DonationModel());
            mDonations.add(new DonationModel());
            mDonations.add(new DonationModel());
            mDonations.add(new DonationModel());
        }
        items.add(mDonations);

        adapter.addAll(items);
        //startSwipeLayout();
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
