package br.com.friendlydonations.views.fragments;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.concurrent.TimeUnit;

import br.com.friendlydonations.R;
import br.com.friendlydonations.managers.BaseFragment;
import br.com.friendlydonations.models.NotificationModel;
import br.com.friendlydonations.views.adapters.NotificationAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by brunogabriel on 8/27/16.
 */
public class NotificationsFragment extends BaseFragment {

    // Views
    protected View rootView;

    @BindView(R.id.recyclerView)
    protected RecyclerView recyclerView;

    @BindView(R.id.swipeRefreshLayout)
    protected SwipeRefreshLayout swipeRefreshLayout;

    NotificationAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_notifications, container, false);
        ButterKnife.bind(this, rootView);
        initUI();
        return rootView;
    }

    @Override
    public void initUI() {
        adapter = new NotificationAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        // Swipe Layout
        startSwipeLayout();

        adapter.add(new NotificationModel());
        adapter.add(new NotificationModel());
        adapter.add(new NotificationModel());
        adapter.add(new NotificationModel());
        adapter.add(new NotificationModel());
        adapter.add(new NotificationModel());
        adapter.add(new NotificationModel());
        adapter.add(new NotificationModel());
        adapter.add(new NotificationModel());
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
