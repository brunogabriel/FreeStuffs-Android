package br.com.friendlydonations.application.account;

import android.net.Uri;
import android.support.annotation.NonNull;

import java.util.UUID;

/**
 * Created by brunogabriel on 16/03/17.
 */

public class AccountPresenter {

    private AccountView view;
    private Uri profileUri;

    public AccountPresenter(@NonNull AccountView view) {
        this.view = view;
    }

    public void tryToChangeProfileImage() {
        view.showImageUpdater();
    }

    public void openCamera() {
        view.openDeviceCamera();
    }

    public void openGallery() {
        view.openDeviceGallery();
    }

    public void tryToCrop(@NonNull Uri cameraFile) {
        view.cropImage(cameraFile, UUID.randomUUID().toString());
    }

    public void updateProfileImage(@NonNull Uri profileUri) {
        this.profileUri = profileUri;
        view.updateProfile(profileUri);
    }

    public void openPrivacyTerms() {
        view.openWebView();
    }

    public void showErrorOnCrop() {
        view.showCropError();
    }

    public Uri getProfileUri() {
        return profileUri;
    }

}
