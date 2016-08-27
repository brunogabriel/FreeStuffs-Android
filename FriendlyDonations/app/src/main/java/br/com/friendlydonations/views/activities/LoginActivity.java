package br.com.friendlydonations.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.util.Arrays;

import br.com.friendlydonations.R;
import br.com.friendlydonations.managers.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by brunogabriel on 8/27/16.
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.toolbar) protected Toolbar mToolbar;
    @BindView(R.id.button) protected Button loginButton;

    // Facebook
    protected CallbackManager callbackManager;
    private ProfileTracker mProfileTracker;
    private Profile mProfile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initUI();
        setupFacebookSDK();

        // Test
        createFacebookUser(null, null);
    }

    @Override
    public void initUI() {
        setupToolbar(mToolbar,  "Teste", null, false, false);
    }

    private void setupFacebookSDK() {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                if (Profile.getCurrentProfile() == null) {
                    mProfileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                            mProfile = currentProfile;
                            requestGraphAPI(loginResult);
                            mProfileTracker.stopTracking();
                        }
                    };
                } else {
                    mProfile = Profile.getCurrentProfile();
                    requestGraphAPI(loginResult);
                }
            }

            @Override
            public void onCancel() {
                // Toast.makeText(LoginActivity.this, R.string.login_activity_cancelled, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        AccessToken currentAccessToken = AccessToken.getCurrentAccessToken();
        Profile currentProfile = Profile.getCurrentProfile();
        AccessToken.refreshCurrentAccessTokenAsync(new AccessToken.AccessTokenRefreshCallback() {
            @Override
            public void OnTokenRefreshed(AccessToken accessToken) {
                // Log.d(TAG, "Hello");
            }

            @Override
            public void OnTokenRefreshFailed(FacebookException exception) {
                // Log.d(TAG, "Hello");
            }
        });

    }

    @OnClick(R.id.button)
    protected void onClickFacebookButton() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email", "user_birthday"));
    }

    private void requestGraphAPI(LoginResult loginResult) {
        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(final JSONObject object, GraphResponse response) {
                createFacebookUser(mProfile, object);
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, birthday, gender, email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    public void createFacebookUser(Profile profile, JSONObject object) {
        Intent loginIntent = new Intent();
        loginIntent.setClass(this, MainActivity.class);
        startActivity(loginIntent);
        this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
