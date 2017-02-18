package br.com.friendlydonations.application.login;

import android.os.Bundle;
import android.support.annotation.Nullable;

import br.com.friendlydonations.R;
import br.com.friendlydonations.shared.BaseActivity;

/**
 * Created by brunogabriel on 16/02/17.
 */

public class LoginActivity extends BaseActivity implements LoginView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
