package br.com.friendlydonations.views.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.widget.Toast;
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
import com.google.firebase.iid.FirebaseInstanceId;
import org.json.JSONObject;
import java.io.InvalidObjectException;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Locale;

import javax.inject.Inject;

import br.com.friendlydonations.R;
import br.com.friendlydonations.dagger.components.DaggerSharedPreferencesComponent;
import br.com.friendlydonations.dagger.modules.SharedPreferencesModule;
import br.com.friendlydonations.managers.App;
import br.com.friendlydonations.managers.BaseActivity;
import br.com.friendlydonations.network.NetworkInterface;
import br.com.friendlydonations.utils.ApplicationUtilities;
import br.com.friendlydonations.utils.ConstantsTypes;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by brunogabriel on 8/27/16.
 */
public class LoginActivity extends BaseActivity {

    public static final String TAG = "LOGIN_ACT";

    @Inject
    protected Retrofit retrofit;

    protected SharedPreferences sharedPreferences;

    @BindView(R.id.tvAboutTerms)
    protected AppCompatTextView tvAboutTerms;

    @BindView(R.id.tvFacebookLogin)
    protected AppCompatTextView tvFacebookLogin;

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
        ((App) getApplication()).getmNetcomponent().inject(this);
        sharedPreferences = DaggerSharedPreferencesComponent.builder()
                .sharedPreferencesModule(new SharedPreferencesModule(getApplication()))
                .build()
                .getSharedPreferences();

        ButterKnife.bind(this);
        initUI();
        setupFacebookSDK();
    }

    @Override
    public void initUI() {
        // @empty
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

                    mProfileTracker.startTracking();
                } else {
                    mProfile = Profile.getCurrentProfile();
                    requestGraphAPI(loginResult);
                }
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, R.string.login_with_facebook_error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("LOGINACTIVITY", "Facebook Exception: " + error.getMessage());
                Toast.makeText(LoginActivity.this, R.string.login_with_facebook_error, Toast.LENGTH_SHORT).show();
            }
        });

        /** AccessToken currentAccessToken = AccessToken.getCurrentAccessToken();
        Profile currentProfile = Profile.getCurrentProfile(); **/

        /**  AccessToken.refreshCurrentAccessTokenAsync(new AccessToken.AccessTokenRefreshCallback() {
            @Override
            public void OnTokenRefreshed(AccessToken accessToken) {

            }

            @Override
            public void OnTokenRefreshFailed(FacebookException exception) {
            }
        }); **/

    }

    @OnClick(R.id.viewFacebookLogin)
    protected void onClickViewFaceBookLogin() {
        /** if (isNetworkEnabled()) {
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email", "user_birthday"));
        } else {
            ApplicationUtilities.showSnackBar(getWindow().getDecorView(), getString(R.string.network_not_detected),
                    Snackbar.LENGTH_LONG, getString(R.string.try_again),
                    view -> findViewById(R.id.viewFacebookLogin).performClick());
        } **/

        // Only in dev
        Intent mIntent = new Intent(this, MainActivity.class);
        startActivity(mIntent);
        this.finish();
    }

    private void requestGraphAPI(final LoginResult loginResult) {
        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(final JSONObject object, GraphResponse response) {
                executeFacebookLogin(mProfile, object, loginResult.getAccessToken().getToken());
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, birthday, gender, email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    protected Action1<Throwable> throwableError = throwable -> {
        getProgressDialog().dismiss();
        ApplicationUtilities.showSnackBar(getWindow().getDecorView(), throwable.getMessage(), Snackbar.LENGTH_LONG, null, null);
    };

    public void executeFacebookLogin(Profile profile, JSONObject object, String accessToken) {
        String pushId = "";

        try {
            pushId = FirebaseInstanceId.getInstance().getToken();
        } catch (Exception exception) {
            Log.e(TAG, "Fail on getting push identifier: " + exception.getMessage());
        }

        showDialog(ProgressDialog.STYLE_SPINNER, null, "Connecting to server", true, false);

        try {

            retrofit.create(NetworkInterface.class).doLogin(
                    profile.getName(),
                    profile.getId(),
                    object.optString("email", ""),
                    object.optString("gender", ""),
                    object.optString("gender", ""),
                    accessToken, pushId,
                    ConstantsTypes.PLATFORM,
                    Locale.getDefault().getLanguage().toString()
            ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(result -> {
                        getProgressDialog().dismiss();
                        if (result.isStatus()) {
                            Intent mIntent = new Intent(this, MainActivity.class);
                            startActivity(mIntent);
                            this.finish();
                        } else {
                            throwableError.call(new InvalidObjectException(result.getMessage()));
                        }

                    }, throwableError);

        } catch (Exception e) {
            Log.e(TAG, "Fail during getting token id from FCM: " + e.getMessage());
            throwableError.call(new InvalidParameterException(getString(R.string.login_with_facebook_error)));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
