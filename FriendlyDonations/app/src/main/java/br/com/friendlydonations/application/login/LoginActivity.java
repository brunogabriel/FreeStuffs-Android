package br.com.friendlydonations.application.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import br.com.friendlydonations.R;
import br.com.friendlydonations.application.account.CreateAccountActivity;
import br.com.friendlydonations.shared.BaseActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by brunogabriel on 16/02/17.
 */

public class LoginActivity extends BaseActivity implements LoginView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        unbinder = ButterKnife.bind(this);
    }

    // Actions
    @OnClick(R.id.create_account_button)
    protected void onClickCreateAccount() {
        // TODO: Put Code Extras to return back login
        startActivity(new Intent(this, CreateAccountActivity.class));
    }
}
