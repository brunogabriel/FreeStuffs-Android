package br.com.friendlydonations.application.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import javax.inject.Inject;

import br.com.friendlydonations.R;
import br.com.friendlydonations.manager.BaseActivity;
import br.com.friendlydonations.manager.CustomApplication;
import br.com.friendlydonations.network.NetworkLogin;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Retrofit;

/**
 * Created by brunogabriel on 05/02/17.
 */

public class LoginActivity extends BaseActivity implements LoginView {

    @Inject
    protected Retrofit retrofit;

    // Butterknife
    private Unbinder unbinder;

    // Presenter
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getNetworkComponent().inject(this);
        unbinder = ButterKnife.bind(this);

        retrofit.create(NetworkLogin.class);
        loginPresenter = new DefaultLoginPresenter(this, CallbackManager.Factory.create(), retrofit.create(NetworkLogin.class));
        loginPresenter.init();
    }

    @OnClick(R.id.view_facebook_login)
    protected void onClickFacebookLogin() {
        loginPresenter.loginWithFacebook(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginPresenter.getCallbackManager().onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void showNetworkNotAvailable() {
        Toast.makeText(this, "Sem internet teste", Toast.LENGTH_SHORT).show();
    }
}
