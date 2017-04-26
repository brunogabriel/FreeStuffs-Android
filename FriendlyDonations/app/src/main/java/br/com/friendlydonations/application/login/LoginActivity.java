package br.com.friendlydonations.application.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import br.com.friendlydonations.R;
import br.com.friendlydonations.application.main.MainActivity;
import br.com.friendlydonations.application.web.WebLoadActivity;
import br.com.friendlydonations.shared.BaseActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static br.com.friendlydonations.application.web.WebLoadActivity.TITLE_EXTRA;
import static br.com.friendlydonations.application.web.WebLoadActivity.URL_PATH_EXTRA;
import static br.com.friendlydonations.application.web.WebLoadActivity.WebUrlPath.TERMS;

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

    @OnClick(R.id.about_button)
    protected void onClickAboutButton() {
        startActivity(new Intent(this, WebLoadActivity.class)
                .putExtra(URL_PATH_EXTRA, TERMS.getValue())
                .putExtra(TITLE_EXTRA, getString(R.string.login_about_and_use_terms)));
    }
}
