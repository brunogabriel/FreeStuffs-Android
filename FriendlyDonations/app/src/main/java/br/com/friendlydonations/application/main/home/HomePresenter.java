package br.com.friendlydonations.application.main.home;

import android.support.annotation.NonNull;

import br.com.friendlydonations.shared.RxHelper;
import br.com.friendlydonations.shared.UnknownHostOperator;
import br.com.friendlydonations.shared.network.service.CategoryService;
import rx.Subscription;

/**
 * Created by brunogabriel on 24/04/17.
 */

public class HomePresenter {

    private HomeView view;
    private CategoryService categoryService;
    private Subscription categorySubscription;

    public HomePresenter(@NonNull HomeView view,
                         @NonNull CategoryService categoryService) {
        this.view = view;
        this.categoryService = categoryService;
    }

    public void initialize() {
        loadCategories();
    }

    public void loadCategories() {
        categorySubscription = categoryService
                .getCategories()
                .compose(RxHelper.applyIOToMainThreadSchedulers())
                .lift(UnknownHostOperator.getUnknownHostOperator(view::tryAgainCategories))
                .doOnSubscribe(view::showCategoriesLoading)
                .doOnTerminate(view::dismissCategoriesLoading)
                .subscribe(response -> {
                    if (response.isSuccessful() && response.body().getStatus()) {
                        view.showCategories(response.body().getData());
                    } else {
                        view.tryAgainCategories();
                    }
                }, error -> view.tryAgainCategories());
    }

    public void onDestroy() {
        if (categorySubscription != null) {
            categorySubscription.unsubscribe();
        }
    }
}
