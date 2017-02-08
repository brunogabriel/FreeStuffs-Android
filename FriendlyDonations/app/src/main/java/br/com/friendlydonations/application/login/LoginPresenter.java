package br.com.friendlydonations.application.login;

import android.app.Activity;

import com.facebook.CallbackManager;
import com.facebook.login.LoginResult;

import br.com.friendlydonations.manager.BaseActivity;

/**
 * Created by brunogabriel on 05/02/17.
 */

public interface LoginPresenter {
    void init();
    void requestGraphAPI(final LoginResult loginResult);
    void loginWithFacebook(BaseActivity activity);
    CallbackManager getCallbackManager();
}
