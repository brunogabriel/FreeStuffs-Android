package br.com.friendlydonations.application.donate;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.friendlydonations.R;

import static org.mockito.Mockito.verify;

/**
 * Created by brunogabriel on 18/02/17.
 */

@RunWith(MockitoJUnitRunner.Silent.class)
public class DonatePresenterTest {

    @Mock
    DonateView view;

    private DonatePresenter presenter;

    @Before
    public void setUp() {
        presenter = new DonatePresenter(view);
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
}
