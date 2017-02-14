package br.com.friendlydonations.application.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.view.KeyEvent;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import javax.inject.Inject;

import br.com.friendlydonations.R;
import br.com.friendlydonations.application.main.MainActivity;
import br.com.friendlydonations.shared.BaseActivity;
import br.com.friendlydonations.network.NetworkLogin;
import br.com.friendlydonations.shared.firebase.FirebaseServiceID;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;
import rx.functions.Action0;
import rx.functions.Action3;

import static android.app.ProgressDialog.STYLE_SPINNER;

/**
 * Created by brunogabriel on 05/02/17.
 */
public class LoginActivity extends BaseActivity implements LoginView {

    @Inject
    protected Retrofit retrofit;
    @BindView(R.id.coordinator_layout)
    protected CoordinatorLayout coordinatorLayout;
    private FacebookLoginManager facebookLoginManager;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getNetworkComponent().inject(this);
        unbinder = ButterKnife.bind(this);
        retrofit.create(NetworkLogin.class);
        presenter = new LoginPresenter(this, retrofit.create(NetworkLogin.class));
        facebookLoginManager = new FacebookLoginManager(loginWithFacebookAction(), CallbackManager.Factory.create(), receiveFacebookDataCallback());
    }

    private Action3<JSONObject, GraphResponse, Profile> loginWithFacebookAction() {
        return (jsonObject, graphResponse, profile) -> presenter.loginWithFacebook(FirebaseServiceID.getDeviceToken(), jsonObject, graphResponse, profile);
    }

    private FacebookCallback<LoginResult> receiveFacebookDataCallback() {
        return new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(@NonNull final LoginResult loginResult) {
                checkFacebookProfile(loginResult);
            }

            @Override
            public void onCancel() {
                facebookLoginCancelled();
            }

            @Override
            public void onError(FacebookException error) {
                showNetworkUnavailable();
            }
        };
    }

    private void checkFacebookProfile(@NonNull final LoginResult loginResult) {
        if (Profile.getCurrentProfile() == null) {
            facebookLoginManager.setProfileTracker(new ProfileTracker() {
                @Override
                protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                    facebookLoginManager.setFacebookProfile(currentProfile);
                    facebookLoginManager.requestGraphAPI(loginResult);
                    facebookLoginManager.getProfileTracker().stopTracking();
                }
            });

            facebookLoginManager.getProfileTracker().startTracking();
        } else {
            facebookLoginManager.setFacebookProfile(Profile.getCurrentProfile());
            facebookLoginManager.requestGraphAPI(loginResult);
        }
    }

    @OnClick(R.id.view_facebook_login)
    protected void onClickFacebookLogin() {
        facebookLoginManager.requestActivityLogin(this);
    }

    @Override
    public void showError(String errorMessage) {
        showSimpleSnackbar(coordinatorLayout, errorMessage);
    }

    @Override
    public void facebookLoginCancelled() {
        showError(getString(R.string.login_with_facebook_cancelled));
    }

    @Override
    public void showLoader() {
        showDialog(STYLE_SPINNER, null, getString(R.string.loading), true, false);
    }

    @Override
    public void dismissLoader() {
        getProgressDialog().dismiss();
    }

    @Override
    public void showNetworkUnavailable() {
        showSnackbarNetworkUnavailable(coordinatorLayout);
    }

    @Override
    public void actionOnKeyBack(Action0 actionOnKeyBack) {
        getProgressDialog().setOnKeyListener((dialog, keyCode, keyEvent) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                dialog.dismiss();
                actionOnKeyBack.call();
            }

            return true;
        });
    }

    @Override
    public void userLogged() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebookLoginManager.onActivityResult(requestCode, resultCode, data);
    }
}
