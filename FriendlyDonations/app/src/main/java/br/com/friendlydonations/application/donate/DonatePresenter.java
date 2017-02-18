package br.com.friendlydonations.application.donate;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;

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

    public void onPlaceApiAnswerunknown() {

    }
}
