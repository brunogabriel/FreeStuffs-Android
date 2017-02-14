package br.com.friendlydonations.application.main.donate;

import android.net.Uri;
import android.widget.ImageView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.friendlydonations.models.PictureUpload;
import br.com.friendlydonations.network.NetworkCategory;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by brunogabriel on 13/02/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class DonatePresenterTest {

    @Mock
    DonateView view;

    @Mock
    NetworkCategory networkCategory;

    @Mock
    PictureRecyclerViewAdapter pictureRecyclerViewAdapter;

    @Mock
    ImageView imageView;

    @Mock
    PictureUpload pictureUpload;

    @Mock
    Uri uri;

    private DonatePresenter donatePresenter;

    @Before
    public void setUp() {
        donatePresenter = new DonatePresenter(view, networkCategory);
    }

    @Test
    public void shouldAddFivePictureUploadSpacesAfterInit() {
        // given
        donatePresenter.init();

        // then
        verify(view, times(1)).addPictures(any());
    }

    @Test
    public void shouldPopulateImageWhenImageViewAndPictureUploadAreValdid() {
        // given
        when(pictureUpload.getImage()).thenReturn(uri);

        // when
        donatePresenter.populateImage(imageView, pictureUpload);
        // then
        verify(view, times(1)).changeImage(imageView, pictureUpload);
    }

    @Test
    public void shoudNotPopulateImageWhenImageViewOrPictureUploadAreInvalid() {
        // given
        when(pictureUpload.getImage()).thenReturn(null);

        // when
        donatePresenter.populateImage(imageView, pictureUpload);

        // then
        verify(view, times(0)).changeImage(imageView, pictureUpload);
    }

    @Test
    public void shouldCallUploadGalleryWhenSelectedOptionGallery() {
        // given
        int menuPopupClicked = DonateFragment.GALLERY_OPTION;
        // when
        donatePresenter.uploadByType(menuPopupClicked);
        // then
        verify(view, times(1)).uploadByGallery();
    }

    @Test
    public void shouldCallCameraWhenSelectedOptionCamera() {
        // given
        int menuPopupClicked = DonateFragment.CAMERA_OPTION;

        // when
        donatePresenter.uploadByType(menuPopupClicked);
        // then
        verify(view, times(1)).uploadByCamera();
    }

    @Test
    public void shouldNotCallCameraOrGalleryWhenPassingInvalidOption() {
        // given
        int menuPopupClicked = -1;
        // when
        donatePresenter.uploadByType(menuPopupClicked);
        // then
        verify(view, never()).uploadByGallery();
        verify(view, never()).uploadByCamera();
    }

    @Test
    public void shouldScrollToValidPosition() {
        // given
        int position = 0;
        // when
        donatePresenter.scrollToPicture(position);
        // then
        verify(view, times(1)).scrollToPictureAtIndex(position);
    }

    @Test
    public void shouldNotScrollToInvalidPosition() {
        // given
        int position = 5;
        // when
        donatePresenter.scrollToPicture(position);
        // then
        verify(view, never()).scrollToPictureAtIndex(position);
    }

    @Test
    public void shouldCallChooseSelectorAfterClickedAtHolder() {
        // when
        donatePresenter.showChooser();
        // then
        verify(view, times(1)).choosePictureUploadMethod();
    }
}
