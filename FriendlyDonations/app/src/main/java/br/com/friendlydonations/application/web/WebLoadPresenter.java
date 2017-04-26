package br.com.friendlydonations.application.web;

import android.support.annotation.NonNull;

/**
 * Created by brunogabriel on 25/04/17.
 */

public class WebLoadPresenter {

    private WebLoadView view;

    public WebLoadPresenter(@NonNull WebLoadView view) {
        this.view = view;
    }

    public void refreshUrl(@NonNull String urlPathName) {
        view.onLoad(urlPathName);
    }
}
