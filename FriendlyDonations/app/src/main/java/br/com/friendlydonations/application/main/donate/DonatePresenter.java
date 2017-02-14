package br.com.friendlydonations.application.main.donate;

import android.widget.ImageView;
import java.util.ArrayList;
import java.util.List;

import br.com.friendlydonations.models.PictureUpload;
import br.com.friendlydonations.network.NetworkCategory;

/**
 * Created by brunogabriel on 13/02/17.
 */

public class DonatePresenter {

    private static final int PICTURE_ADAPTER_SIZE = 5;

    private final DonateView view;
    private final NetworkCategory networkCategory;

    public DonatePresenter(DonateView view,
                           NetworkCategory networkCategory) {
        this.view = view;
        this.networkCategory = networkCategory;
    }

    public void init() {
        addEmptyUploadPictures();
    }

    private void addEmptyUploadPictures() {
        List<PictureUpload> pictureUploadList = new ArrayList<>();
        for (int i = 0; i < PICTURE_ADAPTER_SIZE; i++) {
            pictureUploadList.add(new PictureUpload(i, null));
        }
        view.addPictures(pictureUploadList);
    }

    public void populateImage(ImageView contentImage, PictureUpload upload) {
        if (contentImage != null && upload != null && upload.getImage() != null) {
            view.changeImage(contentImage, upload);
        }
    }

    public void showChooser() {
        view.choosePictureUploadMethod();
    }

    public void uploadByType(int itemId) {
        if (itemId == DonateFragment.GALLERY_OPTION) {
            view.uploadByGallery();
        } else if (itemId == DonateFragment.CAMERA_OPTION) {
            view.uploadByCamera();
        }
    }

    public void scrollToPicture(int index) {
        if (index >= 0 && index < PICTURE_ADAPTER_SIZE ) {
            view.scrollToPictureAtIndex(index);
        }
    }
}
