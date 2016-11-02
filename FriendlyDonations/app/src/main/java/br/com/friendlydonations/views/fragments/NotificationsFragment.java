package br.com.friendlydonations.views.fragments;

import android.app.Notification;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import br.com.friendlydonations.R;
import br.com.friendlydonations.managers.BaseFragment;
import br.com.friendlydonations.models.NotificationModel;
import br.com.friendlydonations.views.adapters.NotificationAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by brunogabriel on 8/27/16.
 */
public class NotificationsFragment extends BaseFragment {

    // Views
    protected View rootView;

    @BindView(R.id.recyclerView)
    protected RecyclerView recyclerView;

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
}
