package br.com.friendlydonations.application.donate;

import rx.functions.Action0;

/**
 * Created by brunogabriel on 16/02/17.
 */
public interface DonateView {
    void emitGooglePlayServicesError(int titleId, int contentId, int yesId, boolean isScrollable);
    void onClickChangePicture();

    void takePicture();

    void requestCameraPermissions(String[] permissions);

    void selectPictureFromGallery();
    void requestWriteExternalPermission(String permissions);

    void showLoader();

    void dismissLoader();

    void showPlace(String address);

    void showGenericMessage(int unknown_result_error);

    void showCategoriesError();
}
