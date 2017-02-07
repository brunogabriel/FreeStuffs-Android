package br.com.friendlydonations.application.login;

import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import br.com.friendlydonations.R;
import br.com.friendlydonations.manager.BaseActivity;
import br.com.friendlydonations.manager.CustomApplication;
import retrofit2.Retrofit;

/**
 * Created by brunogabriel on 05/02/17.
 */

public class LoginActivity extends BaseActivity implements LoginView {

    @Inject
    protected Retrofit retrofit;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getNetworkComponent().inject(this);
    }

    @Override
    public void showLoginLoader() {

    }

    @Override
    public void dismissLoginLoader() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
