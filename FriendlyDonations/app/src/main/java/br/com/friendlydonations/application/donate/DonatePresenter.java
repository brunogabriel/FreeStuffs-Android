package br.com.friendlydonations.application.donate;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;

import br.com.friendlydonations.R;

/**
 * Created by brunogabriel on 16/02/17.
 */

public class DonatePresenter {

    private final DonateView view;
    private Place inputedPlace;

    public DonatePresenter (DonateView view) {
        this.view = view;
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
}
