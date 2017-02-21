package br.com.friendlydonations.application.donate;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;

import br.com.friendlydonations.R;
import br.com.friendlydonations.network.NetworkCategory;
import br.com.friendlydonations.shared.PermissionApplication;
import br.com.friendlydonations.shared.adapter.CategoryAdapter;
import br.com.friendlydonations.shared.adapter.PictureUpdaterAdapter;
import br.com.friendlydonations.shared.application.UnknownHostOperator;
import br.com.friendlydonations.shared.models.PictureDiskModel;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by brunogabriel on 16/02/17.
 */

public class DonatePresenter {

    private final DonateView view;
    private final PictureUpdaterAdapter adapter;
    private Place inputedPlace;
    private PictureDiskModel pictureDiskModel;
    private NetworkCategory networkCategory;
    private Subscription subscription;
    private CategoryAdapter categoryAdapter;

    public DonatePresenter (DonateView view, PictureUpdaterAdapter adapter, NetworkCategory networkCategory, CategoryAdapter categoryAdapter) {
        this.view = view;
        this.adapter = adapter;
        this.networkCategory = networkCategory;
        this.categoryAdapter = categoryAdapter;
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

//        TODO: Action on click category
//        categoryAdapter.setAction();
    }

    public void startRequests() {
        subscription = networkCategory.findCategories()
                .subscribeOn(Schedulers.io())
                .lift(UnknownHostOperator.getUnknownHostOperator(() -> {
                    // TODO: Verify
                }))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> view.showLoader())
                .doAfterTerminate(() -> view.dismissLoader())
                .subscribe(result -> {
                    if (result.isStatus() && result.getCategoryModelList().size() > 0) {
                        categoryAdapter.addAll(result.getCategoryModelList());
                    } else {
                        view.onCategoryError();
                    }
                }, error -> view.onCategoryError());
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
