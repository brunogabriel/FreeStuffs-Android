package br.com.friendlydonations.application.donate;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;

import br.com.friendlydonations.R;
import br.com.friendlydonations.shared.PermissionApplication;
import br.com.friendlydonations.shared.adapter.PictureUpdaterAdapter;
import br.com.friendlydonations.shared.models.PictureDiskModel;
import rx.functions.Action1;

/**
 * Created by brunogabriel on 16/02/17.
 */

public class DonatePresenter {

    private final DonateView view;
    private final PictureUpdaterAdapter adapter;
    private Place inputedPlace;
    private PictureDiskModel pictureDiskModel;

    public DonatePresenter (DonateView view, PictureUpdaterAdapter adapter) {
        this.view = view;
        this.adapter = adapter;
    }

    public void onPlaceApiAnswer(Place place) {

    }

    public void onPlaceApiAnswerError(Status status) {

    }

    public void onPlaceApiAnswerCanceled() {
    }

    public void onPlaceApiAnswerUnknown() {

    }

    public void createGooglePlayServicesDialogError(Throwable throwable) {
        int errorContentMessage;
        if (throwable instanceof GooglePlayServicesNotAvailableException) {
            errorContentMessage = R.string.google_play_services_not_installed;
        } else {
            errorContentMessage = R.string.google_play_services_not_available;
        }

        view.emitGooglePlayServicesError(R.string.google_play_services_error_title, errorContentMessage, R.string.ok, false);
    }

    public void initialize(RecyclerView recyclerViewPictures) {
        recyclerViewPictures.setAdapter(adapter);
        adapter.setActionClickPictureModel(new Action1<PictureDiskModel>() {
            @Override
            public void call(PictureDiskModel pictureDiskModel) {
                DonatePresenter.this.pictureDiskModel = pictureDiskModel;
                view.onClickChangePicture();
            }
        });
    }

    public void verifyCameraPermissions(Context context, String[] permissions) {
        if (PermissionApplication.areAllPermissionsEnabled(context, permissions)) {
            view.takePicture();
        } else {
            view.requestCameraPermissions(permissions);
        }
    }

    public void verifyGalleryPermission(Context context, String permission) {
        if (PermissionApplication.isPermissionEnabled(context, permission)) {
            view.selectPictureFromGallery();
        } else {
            view.requestWriteExternalPermission(permission);
        }
    }
}
