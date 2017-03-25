package br.com.friendlydonations.application.account;

import android.net.Uri;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by brunogabriel on 24/03/17.
 */

@RunWith(MockitoJUnitRunner.Silent.class)
public class AccountPresenterTest {

    @Mock
    public AccountView view;

    @Mock
    public Uri mockUri;

    private AccountPresenter presenter;

    @Before
    public void setUp() {
        presenter = new AccountPresenter(view);
    }

    @Test
    public void shouldShowImageUpdaterWhenTryToChangeProfileImage() {
        // when
        presenter.tryToChangeProfileImage();

        // then
        verify(view).showImageUpdater();
    }

    @Test
    public void shouldOpenDeviceCameraWhenOpenCameraIsCalled() {
        // when
        presenter.openCamera();

        // then
        verify(view).openDeviceCamera();
    }

    @Test
    public void shouldOpenDeviceGalleryWhenOpenGalleryIsCalled() {
        // when
        presenter.openGallery();

        // then
        verify(view).openDeviceGallery();
    }

    @Test
    public void shouldTryToCropWhenReceiveIntent() {
        // when
        presenter.tryToCrop(mockUri);

        // then
        verify(view).cropImage(eq(mockUri), any(String.class));
    }

    @Test
    public void shouldUpdateProfileWhenCallUpdateProfileImage() {
        // when
        presenter.updateProfileImage(mockUri);

        // then
        assertEquals(presenter.getProfileUri(), mockUri);
        verify(view).updateProfile(mockUri);
    }

    @Test
    public void shouldOpenPrivacyTermsWhenClickAtPrivacyTermsText() {
        // when
        presenter.openPrivacyTerms();

        // then
        verify(view).openWebView();
    }

    @Test
    public void shouldShowErrorCropWhenCropHasErrors() {
        // then
        presenter.showErrorOnCrop();

        // then
        verify(view).showCropError();
    }
}
