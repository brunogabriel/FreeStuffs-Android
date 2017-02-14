package br.com.friendlydonations.application.main.donate;

import android.widget.ImageView;

import java.util.List;

import br.com.friendlydonations.models.PictureUpload;

/**
 * Created by brunogabriel on 13/02/17.
 */
public interface DonateView {
    void addPictures(List<PictureUpload> pictureUploadList);
    void changeImage(ImageView contentImage, PictureUpload upload);
    void choosePictureUploadMethod();
    void uploadByGallery();
    void uploadByCamera();
    void scrollToPictureAtIndex(int index);
}
