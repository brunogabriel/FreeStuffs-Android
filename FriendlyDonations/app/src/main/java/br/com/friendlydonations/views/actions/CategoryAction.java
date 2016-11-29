package br.com.friendlydonations.views.actions;

import br.com.friendlydonations.models.category.CategoryModel;

/**
 * Created by brunogabriel on 29/11/16.
 */
public interface CategoryAction {
    /**
     * Provides a listener to execute actions related a specific category
     * @param position index of adapter
     * @param categoryModel data of category
     */
    void onClickAtPosition(int position, CategoryModel categoryModel);
}
