package br.com.friendlydonations.application.main.home;

import android.support.annotation.NonNull;

import java.util.List;

import br.com.friendlydonations.shared.models.category.Category;

/**
 * Created by brunogabriel on 24/04/17.
 */

public interface HomeView {

    // Categories
    void showCategoriesLoading();
    void dismissCategoriesLoading();
    void showCategories(@NonNull List<Category> data);
    void tryAgainCategories();
}
