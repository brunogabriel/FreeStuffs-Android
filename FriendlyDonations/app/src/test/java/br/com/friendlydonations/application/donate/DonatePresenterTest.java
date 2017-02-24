package br.com.friendlydonations.application.donate;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.location.places.Place;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.friendlydonations.R;
import br.com.friendlydonations.network.NetworkCategory;
import br.com.friendlydonations.shared.adapter.CategoryAdapter;
import br.com.friendlydonations.shared.adapter.PictureUpdaterAdapter;

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

    @Before
    public void setUp() {
        presenter = new DonatePresenter(view, pictureUpdaterAdapter, networkCategory, categoryAdapter);
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
}
