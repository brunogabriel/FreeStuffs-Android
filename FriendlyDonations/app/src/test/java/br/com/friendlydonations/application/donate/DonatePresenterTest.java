package br.com.friendlydonations.application.donate;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.location.places.Place;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import br.com.friendlydonations.R;
import br.com.friendlydonations.helpers.RxHelperTest;
import br.com.friendlydonations.network.NetworkCategory;
import br.com.friendlydonations.shared.adapter.CategoryAdapter;
import br.com.friendlydonations.shared.adapter.PictureUpdaterAdapter;
import br.com.friendlydonations.shared.models.category.CategoryAnswerModel;
import br.com.friendlydonations.shared.models.category.CategoryModel;
import retrofit2.Response;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by brunogabriel on 18/02/17.
 */

@RunWith(MockitoJUnitRunner.Silent.class)
public class DonatePresenterTest {

    @Mock
    DonateView view;

    @Mock
    PictureUpdaterAdapter pictureUpdaterAdapter;

    @Mock
    NetworkCategory networkCategory;

    @Mock
    CategoryAdapter categoryAdapter;

    @Mock
    Place place;

    private DonatePresenter presenter;
    private RxHelperTest rxHelperTest = new RxHelperTest();

    @Before
    public void setUp() {
//        rxHelperTest.setUp();
        presenter = new DonatePresenter(view, pictureUpdaterAdapter, networkCategory, categoryAdapter);
    }

    @After
    public void tearDown() {
//        rxHelperTest.tearDown();
    }

    @Test
    public void shouldCreateGooglePlayServicesDialogErrorWithNotInstalledMessage() {
        // given
        int contentString = R.string.google_play_services_not_installed;
        Throwable throwable = new GooglePlayServicesNotAvailableException(0);

        // when
        presenter.createGooglePlayServicesDialogError(throwable);

        // then
        verify(view).emitGooglePlayServicesError(R.string.google_play_services_error_title, contentString, R.string.ok, false);
    }

    @Test
    public void shouldCreateGooglePlayServicesDialogErrorWithGenericError() {
        // given
        int contentString = R.string.google_play_services_not_available;
        Throwable throwable =  new RuntimeException();

        // when
        presenter.createGooglePlayServicesDialogError(throwable);

        // then
        verify(view).emitGooglePlayServicesError(R.string.google_play_services_error_title, contentString, R.string.ok, false);
    }

    @Test
    public void shouldPopulateAddressWhenReceiveValidPlace() {
        // given
        when(place.getAddress()).thenReturn("anyAddress");

        // when
        presenter.onPlaceApiAnswer(place);

        // then
        verify(view).showPlace("anyAddress");
    }

    @Test
    public void shouldFinish() {
        CategoryAnswerModel categoryAnswerModel = createCategoryAnswer();
        when(networkCategory.findCategories()).thenReturn(Observable.just(Response.success(categoryAnswerModel)));
        TestSubscriber testSubscriber = TestSubscriber.create();
        networkCategory.findCategories().subscribe(testSubscriber);

        presenter.startRequests();

        verify(view).showLoader();

    }

//    @Test
//    public void shouldAddCategoriesWhenNetworkAnswerIsValid() {
//        CategoryAnswerModel categoryAnswerModel = createCategoryAnswer();
//        when(networkCategory.findCategories()).thenReturn(Observable.just(Response.success(categoryAnswerModel)));
//        presenter.startRequests();
//        verify(view).showLoader();
//        verify(view).dismissLoader();
//        verify(categoryAdapter).addAll(categoryAnswerModel.getCategoryModelList());
//    }
//
//    @Test
//    public void shouldShowErrorWhenStatusIsFalse() {
//        CategoryAnswerModel categoryAnswerModel = createCategoryAnswer();
//        categoryAnswerModel.setStatus(false);
//        when(networkCategory.findCategories()).thenReturn(Observable.just(Response.success(categoryAnswerModel)));
//        presenter.startRequests();
//        verify(view).showLoader();
//        verify(view).dismissLoader();
//        verify(view).showCategoriesError();
//    }

    private CategoryAnswerModel createCategoryAnswer() {
        CategoryAnswerModel categoryAnswerModel = new CategoryAnswerModel();
        List<CategoryModel> list = new ArrayList<>();
        list.add(new CategoryModel());
        categoryAnswerModel.setStatus(true);
        categoryAnswerModel.setCategoryModelList(list);
        return categoryAnswerModel;
    }
}
