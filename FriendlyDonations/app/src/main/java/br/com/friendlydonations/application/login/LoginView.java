package br.com.friendlydonations.application.login;


import rx.functions.Action0;

/**
 * Created by brunogabriel on 05/02/17.
 */
public interface LoginView {
    void showError(String errorMessage);

    void facebookLoginCancelled();

    void showLoader();

    void dismissLoader();

    void showNetworkUnavailable();

    void actionOnKeyBack(Action0 actionOnKeyBack);

    void userLogged();
}
