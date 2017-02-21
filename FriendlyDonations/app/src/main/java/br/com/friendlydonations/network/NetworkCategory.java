package br.com.friendlydonations.network;

import br.com.friendlydonations.shared.models.category.CategoryAnswerModel;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by brunogabriel on 13/02/17.
 */

public interface NetworkCategory {

    @GET("product_categories")
    Observable<CategoryAnswerModel> findCategories();

}
