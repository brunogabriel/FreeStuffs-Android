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

import javax.inject.Inject;

import br.com.friendlydonations.R;
import br.com.friendlydonations.managers.App;
import br.com.friendlydonations.managers.BaseFragment;
import br.com.friendlydonations.models.LoaderModel;
import br.com.friendlydonations.network.NetworkInterface;
import br.com.friendlydonations.views.activities.LoginActivity;
import br.com.friendlydonations.views.activities.MainActivity;
import br.com.friendlydonations.views.adapters.HomeDonationsAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import mehdi.sakout.dynamicbox.DynamicBox;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by brunogabriel on 8/27/16.
 */
public class HomeFragment extends BaseFragment {

    // Constants
    private static final String DYNAMICBOX_DISCONNECTED = "dynamicboxdisconnected";

    @Inject
    protected Retrofit retrofit;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recyclerView)
    protected RecyclerView recyclerView;

    protected StaggeredGridLayoutManager gridLayoutManager;
    protected HomeDonationsAdapter adapter;

    // Views
    protected View rootView;
    protected View dynamicBoxNoInternet;
    protected DynamicBox dynamicBox;

    // Vars to help requests
    private boolean isCategoryLoaded;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        dynamicBoxNoInternet = inflater.inflate(R.layout.dynamicbox_networkoff, container, false);
        ((App) getActivity().getApplication()).getmNetcomponent().inject(this);
        ButterKnife.bind(this, rootView);
        initUI();
        return rootView;
    }

    @Override
    public void initUI() {
        swipeRefreshLayout.setEnabled(false);
        gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        adapter = new HomeDonationsAdapter(getActivity(), new LoaderModel());
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        if(!isNetworkEnabled()) {
            dynamicBox = new DynamicBox(getActivity(), recyclerView);
            dynamicBox.addCustomView(dynamicBoxNoInternet, DYNAMICBOX_DISCONNECTED);
            dynamicBox.showCustomView(DYNAMICBOX_DISCONNECTED);
            dynamicBoxNoInternet.findViewById(R.id.btnTryAgain).setOnClickListener(v -> {
                if (isNetworkEnabled()) {
                    dynamicBox.hideAll();
                    startRequest();
                }
            });
        } else {
            startRequest();
        }

        // Only to test below
        //startSwipeLayout();
//
//        // Fake Objects
//        List<Object> mItens = new ArrayList<>();
//        List<Object> mCategories = new ArrayList<>();
//
//        mCategories.add(new CategoryModel("Todas", true));
//        mCategories.add(new CategoryModel("Alimentos", false));
//        mCategories.add(new CategoryModel("Animais", false));
//        mCategories.add(new CategoryModel("Eletrônicos", false));
//        mCategories.add(new CategoryModel("Móveis", false));
//        mCategories.add(new CategoryModel("Roupas", false));
//        mCategories.add(new CategoryModel("Serviços", false));
//
//        mItens.add(mCategories);
//
//        for (int i = 0; i < 10; ++i) {
//            mItens.add(new DonationModel());
//        }
//
//        Observable.timer(3, TimeUnit.SECONDS)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(oObject -> {
//                    adapter.addAll(mItens);
//                });
    }

    private void startRequest() {

        Subscription subscription;

        if (!isCategoryLoaded) {
            subscription = retrofit.create(NetworkInterface.class)
                    .loadCategories(((MainActivity) getActivity()).getToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(result -> {
                        if (result.isStatus() && result.getCategoryModelList().size() > 0) {
                            List<Object> mItens = new ArrayList<>();
                            mItens.add(result.getCategoryModelList());
                            adapter.addAllWithLoading(mItens);
                        }
                    }, throwableError);
        }
    }

    protected Action1<Throwable> throwableError = throwable -> {
        int x = 1;
    };

    private void startSwipeLayout() {
        swipeRefreshLayout.setEnabled(true);
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
