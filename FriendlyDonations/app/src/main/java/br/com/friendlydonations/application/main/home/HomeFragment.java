package br.com.friendlydonations.application.main.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.friendlydonations.R;
import br.com.friendlydonations.shared.BaseFragment;
import br.com.friendlydonations.shared.models.category.Category;
import br.com.friendlydonations.shared.network.RetrofitHelper;
import br.com.friendlydonations.shared.network.service.CategoryService;
import br.com.friendlydonations.shared.ui.DynamicBoxHelper;
import butterknife.BindView;
import butterknife.ButterKnife;

import static br.com.friendlydonations.shared.ui.DynamicBoxHelper.DynamicBoxView.LOADING_HORIZONTAL;

/**
 * Created by brunogabriel on 24/04/17.
 */

public class HomeFragment extends BaseFragment implements HomeView {

    @BindView(R.id.category_recyclerview)
    protected RecyclerView categoryRecyclerView;

    protected HomePresenter presenter;
    private DynamicBoxHelper categoryLoadingBox;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        unbinder = ButterKnife.bind(this, getActivity());
        initUI();
        presenter = new HomePresenter(this, RetrofitHelper.getInstance().getRetrofit().create(CategoryService.class));
        presenter.initialize();
    }

    private void initUI() {
        categoryLoadingBox = new DynamicBoxHelper(getActivity(), categoryRecyclerView);
    }

    @Override
    public void showCategoriesLoading() {
        categoryLoadingBox.showCachedView(LOADING_HORIZONTAL);
    }

    @Override
    public void dismissCategoriesLoading() {
        categoryLoadingBox.hideAll();
    }

    @Override
    public void showCategories(@NonNull List<Category> data) {
        HomeCategoryAdapter categoryAdapter = new HomeCategoryAdapter(getContext(), data);
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void tryAgainCategories() {
        // TODO: Show
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}