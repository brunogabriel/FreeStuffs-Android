package br.com.friendlydonations.application.account;

import android.net.Uri;
import android.support.annotation.NonNull;

/**
 * Created by brunogabriel on 16/03/17.
 */

interface AccountView {
    void openDeviceCamera();

    void openDeviceGallery();

    void showImageUpdater();

    void cropImage(@NonNull Uri cameraFile, @NonNull String cropImageFileName);

    void updateProfile(@NonNull Uri profileUri);

    void openWebView();

    void showCropError();
}
