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

import javax.inject.Inject;

import br.com.friendlydonations.R;
import br.com.friendlydonations.managers.App;
import br.com.friendlydonations.managers.BaseFragment;
import br.com.friendlydonations.models.ImageModel;
import br.com.friendlydonations.models.LoaderModel;
import br.com.friendlydonations.models.category.CategoryModel;
import br.com.friendlydonations.network.NetworkInterface;
import br.com.friendlydonations.views.activities.MainActivity;
import br.com.friendlydonations.views.adapters.HomeDonationsAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import mehdi.sakout.dynamicbox.DynamicBox;
import retrofit2.Retrofit;
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
    private Subscription donationSubscription;
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
        // Setup List and Adapter
        gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        adapter = new HomeDonationsAdapter(getActivity(), new LoaderModel());
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setCategoryAction((position, categoryModel) -> {
            reloadDonations();
        });

        // Setup swipe
        swipeRefreshLayout.setEnabled(false);
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(() -> {
            if (isCategoryLoaded) {
                reloadDonations();
            } else {
                startRequest();
            }
        });

        // Request Starting
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
    }

    private Action1<Throwable> throwableError = throwable -> {
        updateSwipe(true, false);
    };

    /**
     * Start request categories and if has success, execute a new request to get donations
     */
    private void startRequest() {
        if (!isCategoryLoaded) {
            retrofit.create(NetworkInterface.class)
                    .loadCategories(((MainActivity) getActivity()).getToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(result -> {
                        updateSwipe(true, false);
                        if (result.isStatus() && result.getCategoryModelList().size() > 0) {
                            List<Object> mItens = new ArrayList<>();
                            // All
                            CategoryModel categoryModel = new CategoryModel();
                            categoryModel.setId(null);
                            categoryModel.setChecked(true);
                            categoryModel.setName("Todos");
                            categoryModel.setImageModel(new ImageModel());
                            result.getCategoryModelList().add(0, categoryModel);

                            mItens.add(result.getCategoryModelList());
                            adapter.addAllWithLoading(mItens);
                            this.isCategoryLoaded = true;
                            loadDonations();
                        } else {
                            adapter.removeLoader();
                        }
                    }, throwableError);
        } else {
            loadDonations();
        }
    }

    /**
     * Basic method to request donations
     */
    private void loadDonations() {
        String token = ((MainActivity) getActivity()).getToken();
        donationSubscription = retrofit.create(NetworkInterface.class)
                .loadDonations(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    updateSwipe(true, false);
                    if (result.isStatus() && result.getDonationModelList().size() > 0) {
                        List<Object> mItens = new ArrayList<>();
                        mItens.addAll(result.getDonationModelList());
                        adapter.addAll(mItens);
                    } else {
                        adapter.removeLoader();
                    }
                }, throwableError);
    }

    /**
     * Apply changes to Swipe Refresh Layout
     * @param isEnabled indicate state of swipe (enable to use)
     * @param isRefreshing indicate if swipe is running now
     */
    private void updateSwipe(boolean isEnabled, boolean isRefreshing) {
        swipeRefreshLayout.setEnabled(isEnabled);
        swipeRefreshLayout.setRefreshing(isRefreshing);
    }

    /**
     * By default remove other requests because cancel subscription, maintain categories
     * and start new request (page 0)
     */
    private void reloadDonations() {
        if (donationSubscription != null) {
            donationSubscription.unsubscribe();
        }
        adapter.removeAllExceptCategories(true);
        loadDonations();
    }
}
