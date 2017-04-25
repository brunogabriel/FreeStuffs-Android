package br.com.friendlydonations.application.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import br.com.friendlydonations.R;
import br.com.friendlydonations.application.main.MainActivity;
import br.com.friendlydonations.shared.BaseActivity;
import butterknife.ButterKnife;

/**
 * Created by brunogabriel on 24/04/17.
 */

public class LoginActivity extends BaseActivity implements LoginView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_login);
        ButterKnife.bind(this);
        startActivity(new Intent(this, MainActivity.class));
    }
}
