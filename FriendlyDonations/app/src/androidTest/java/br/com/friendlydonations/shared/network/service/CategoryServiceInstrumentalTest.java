package br.com.friendlydonations.shared.network.service;

import android.support.test.runner.AndroidJUnit4;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import br.com.friendlydonations.Helper;
import br.com.friendlydonations.shared.models.category.CategoryData;
import retrofit2.Response;
import rx.observers.TestSubscriber;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertTrue;


/**
 * Created by brunogabriel on 26/04/17.
 */

@RunWith(AndroidJUnit4.class)
public class CategoryServiceInstrumentalTest {

    public static final String CATEGORIES_JSON = "assets/categories.json";
    public static final String CATEGORIES_URL = "/product_categories";

    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    @Test
    public void shouldLoadCategories() throws IOException {
        // given
        String jsonCategories = Helper.readFileContent(this, CATEGORIES_JSON);
        TestSubscriber testSubscriber = new TestSubscriber();
        wireMockRule
                .stubFor(get(urlPathEqualTo(CATEGORIES_URL))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody(jsonCategories)));
        CategoryService categoryService = Helper.createService(CategoryService.class);

        // when
        categoryService.getCategories().subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();

        // then
        List<Response<CategoryData>> onNextEvents = testSubscriber.getOnNextEvents();
        assertEquals(1, onNextEvents.size());
        Response<CategoryData> categoryDataResponse = onNextEvents.get(0);
        assertEquals(200, categoryDataResponse.code());
        CategoryData categoryData = categoryDataResponse.body();
        assertTrue(categoryData.isStatus());
        assertEquals(3, categoryData.getData().size());
    }

}
