package br.com.friendlydonations.application.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.widget.Toast;

import com.facebook.CallbackManager;

import javax.inject.Inject;

import br.com.friendlydonations.R;
import br.com.friendlydonations.application.main.MainActivity;
import br.com.friendlydonations.manager.BaseActivity;
import br.com.friendlydonations.network.NetworkLogin;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Retrofit;
import rx.functions.Action0;

import static android.app.ProgressDialog.STYLE_SPINNER;

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
        loginPresenter = new LoginPresenter(this, CallbackManager.Factory.create(), retrofit.create(NetworkLogin.class));
        loginPresenter.init();
    }

    @OnClick(R.id.view_facebook_login)
    protected void onClickFacebookLogin() {
        loginPresenter.loginWithFacebook(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void showLoader() {
        showDialog(STYLE_SPINNER, null, "Carregando", true, false);
    }

    @Override
    public void dismissLoader() {
        getProgressDialog().dismiss();
    }

    @Override
    public void actionOnKeyBack(Action0 action0) {
        getProgressDialog().setOnKeyListener((dialog, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                dialog.dismiss();
                action0.call();
            }
            return true;
        });
    }

    @Override
    public void showNetworkNotAvailable() {
        Toast.makeText(this, "Sem internet teste", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showFacebookLoginCancel() {
        showError(getString(R.string.login_with_facebook_cancelled));
    }

    @Override
    public void showError(String errorMessage) {
        showToast(errorMessage, Toast.LENGTH_LONG);
    }

    @Override
    public void showLogIn() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
