package br.com.friendlydonations.application.login;


import com.facebook.GraphResponse;
import com.facebook.Profile;

import org.json.JSONObject;

import br.com.friendlydonations.models.login.LoginModel;
import br.com.friendlydonations.network.NetworkLogin;
import br.com.friendlydonations.util.UnknownHostOperator;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by brunogabriel on 05/02/17.
 */

public class LoginPresenter {

    private NetworkLogin networkLogin;
    private LoginView view;
    private Subscription subscription;

    public LoginPresenter(LoginView view, NetworkLogin networkLogin) {
        this.view = view;
        this.networkLogin = networkLogin;
    }

    public void loginWithFacebook(String firebaseDeviceToken, JSONObject jsonObject, GraphResponse graphResponse, Profile profile) {
        subscription =
                createLoginObservable(firebaseDeviceToken, jsonObject, graphResponse, profile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .lift(UnknownHostOperator.getUnknownHostOperator(networkUnavailable()))
                .doOnSubscribe(() -> view.showLoader())
                .doOnTerminate(() -> view.dismissLoader())
                .subscribe(result -> {
                    view.userLogged();
                }, throwable -> {
                    view.showError(throwable.getMessage());
                });

        view.actionOnKeyBack(()-> {
            subscription.unsubscribe();
            view.facebookLoginCancelled();
        });
    }

    public Action0 networkUnavailable() {
        return () -> view.showNetworkUnavailable();
    }

    private Observable<LoginModel> createLoginObservable(String firebaseDeviceToken, JSONObject jsonObject, GraphResponse graphResponse, Profile profile) {
        // TODO: Verify params, in the future will use google plus and application login
        return networkLogin.login(profile.getName(),
                profile.getId(),
                jsonObject.optString("email", ""),
                jsonObject.optString("gender", ""),
                jsonObject.optString("birthday", ""),
                graphResponse.getRequest().getAccessToken().getToken(),
                firebaseDeviceToken,
                "android",
                "pt-br",
                false);
    }
}
