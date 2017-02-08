package br.com.friendlydonations.application.login;

import android.os.Bundle;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;
import java.util.List;

import br.com.friendlydonations.manager.BaseActivity;
import br.com.friendlydonations.manager.CustomApplication;
import br.com.friendlydonations.network.NetworkLogin;

/**
 * Created by brunogabriel on 05/02/17.
 */

public class DefaultLoginPresenter implements LoginPresenter {
    private static List<String> FACEBOOK_LOGIN_PERMISSIONS = Arrays.asList("public_profile", "email", "user_birthday");
    private CallbackManager callbackManager;
    private ProfileTracker profileTracker;
    private Profile facebookProfile;
    private LoginView loginView;
    private NetworkLogin networkLogin;

    public DefaultLoginPresenter(LoginView loginView, CallbackManager callbackManager, NetworkLogin networkLogin) {
        this.loginView = loginView;
        this.callbackManager = callbackManager;
        this.networkLogin = networkLogin;
    }

    @Override
    public void init() {
        setupFacebookSdk();
    }

    @Override
    public void requestGraphAPI(LoginResult loginResult) {
        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), (object, response) -> {
            // TODO: Get Firebase ID
            String pushId = "";
            // TODO: Retrofit call
            String localeStr = CustomApplication.locale.getLanguage() + " - " + CustomApplication.locale.getCountry();
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, birthday, gender, email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public void loginWithFacebook(BaseActivity activity) {
        if (activity.isNetworkEnable()) {
            LoginManager.getInstance().logInWithReadPermissions(activity, FACEBOOK_LOGIN_PERMISSIONS);
        } else {
            loginView.showNetworkNotAvailable();
        }
    }

    private void setupFacebookSdk() {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                if (Profile.getCurrentProfile() == null) {
                    profileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                            facebookProfile = currentProfile;
                            requestGraphAPI(loginResult);
                            profileTracker.stopTracking();
                        }
                    };
                    profileTracker.startTracking();
                } else {
                    facebookProfile = Profile.getCurrentProfile();
                    requestGraphAPI(loginResult);
                }
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
            }
        });
    }

    @Override
    public CallbackManager getCallbackManager() {
        return this.callbackManager;
    }
}
