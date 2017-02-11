package br.com.friendlydonations.application.login;

import rx.functions.Action0;

/**
 * Created by brunogabriel on 05/02/17.
 */
public interface LoginView {
    void showLoader();
    void dismissLoader();
    void actionOnKeyBack(Action0 action0);
    void showNetworkNotAvailable();
    void showFacebookLoginCancel();
    void showError(String errorMessage);
    void showLogIn();
}
