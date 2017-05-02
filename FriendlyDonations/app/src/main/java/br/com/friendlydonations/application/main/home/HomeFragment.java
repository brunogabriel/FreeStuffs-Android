package br.com.friendlydonations.application.main.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.com.friendlydonations.R;
import br.com.friendlydonations.shared.BaseFragment;
import br.com.friendlydonations.shared.models.category.Category;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by brunogabriel on 24/04/17.
 */

public class HomeFragment extends BaseFragment implements HomeView {

    @BindView(R.id.category_recyclerview)
    RecyclerView categoryRecyclerView;

    HomePresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        unbinder = ButterKnife.bind(this, getActivity());
        presenter = new HomePresenter();

        List<Category> categories = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            categories.add(new Category());
        }

        HomeCategoryAdapter categoryAdapter = new HomeCategoryAdapter(categories);
        categoryRecyclerView.setAdapter(categoryAdapter);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(categoryRecyclerView);
    }

}
