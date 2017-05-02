package br.com.friendlydonations.shared.network.service;

import br.com.friendlydonations.shared.models.category.CategoryData;
import retrofit2.Response;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by brunogabriel on 26/04/17.
 */

public interface CategoryService {
    @GET(value = "/product_categories")
    Observable<Response<CategoryData>> getCategories();
}
