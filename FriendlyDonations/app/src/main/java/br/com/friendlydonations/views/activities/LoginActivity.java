package br.com.friendlydonations.views.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.firebase.iid.FirebaseInstanceId;
import java.io.InvalidObjectException;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Locale;

import javax.inject.Inject;

import br.com.friendlydonations.R;
import br.com.friendlydonations.dagger.components.DaggerSharedPreferencesComponent;
import br.com.friendlydonations.dagger.components.SharedPreferencesComponent;
import br.com.friendlydonations.dagger.modules.SharedPreferencesModule;
import br.com.friendlydonations.managers.App;
import br.com.friendlydonations.managers.BaseActivity;
import br.com.friendlydonations.models.login.LoginModel;
import br.com.friendlydonations.models.login.LoginPreferenceModel;
import br.com.friendlydonations.network.NetworkInterface;
import br.com.friendlydonations.utils.ConstantsTypes;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by brunogabriel on 8/27/16.
 */
public class LoginActivity extends BaseActivity {

    // Constants
    private static final String TAG = "LOGIN_ACT";
    private static final String LOGIN_TOKEN = "login_token";
    public static final String LOGIN_SERIALIZATION = "login_serialization";
    public static final String LOGIN_NOTIFICATIONS = "login_notifications";

    // Views
    @BindView(R.id.tvAboutTerms)
    protected AppCompatTextView tvAboutTerms;

    @BindView(R.id.tvFacebookLogin)
    protected AppCompatTextView tvFacebookLogin;

    // Facebook
    private CallbackManager callbackManager;
    private ProfileTracker mProfileTracker;
    private Profile mProfile;

    @Inject
    protected Retrofit retrofit;
    private LoginPreferenceModel loginPreferenceModel;
    private SharedPreferences sharedPreferences;

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
        setupFacebookSDK();
        initUI();
    }

    @Override
    public void initUI() {
        try {
            loginPreferenceModel = LoginPreferenceModel.
                    jsonToObject(sharedPreferences.getString(SharedPreferencesComponent.LOGIN_PREFERENCES, null));

            if (loginPreferenceModel != null) {
                if (isNetworkEnabled()) {
                    executePreferenceLogin(loginPreferenceModel);
                } else {
                    Toast.makeText(LoginActivity.this, R.string.network_not_detected, Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception ex) {
            Log.e(TAG, "Fail getting login preferences: " + ex.getMessage());
        }
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
                Toast.makeText(LoginActivity.this, R.string.login_with_facebook_cancelled, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
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
        if (isNetworkEnabled()) {
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email", "user_birthday"));
        } else {
            Toast.makeText(LoginActivity.this, R.string.network_not_detected, Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.tvAboutTerms)
    protected void onClickTvAlert() {
        View mView = LayoutInflater.from(this).inflate(R.layout.alert_about_terms, null, false);
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setView(mView);
        mBuilder.show();
    }

    private void requestGraphAPI(final LoginResult loginResult) {
        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), (object, response) -> {
            String pushId = "";

            try {
                pushId = FirebaseInstanceId.getInstance().getToken();
            } catch (Exception exception) {
                Log.e(TAG, "Fail on getting push identifier: " + exception.getMessage());
            }

            executeLogin(
                    mProfile.getName(),
                    mProfile.getId(),
                    object.optString("email", ""),
                    object.optString("gender", ""),
                    object.optString("birthday", ""),
                    loginResult.getAccessToken().getToken(), pushId,
                    ConstantsTypes.PLATFORM,
                    Locale.getDefault().getLanguage(),
                    false);
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, birthday, gender, email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private final Action1<Throwable> throwableError = throwable -> {
        clearPreference();
        getProgressDialog().dismiss();
        Toast.makeText(LoginActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
    };

    private void executePreferenceLogin(LoginPreferenceModel loginPreferenceModel) {
        try {

            String pushId = "";

            try {
                pushId = FirebaseInstanceId.getInstance().getToken();
            } catch (Exception exception) {
                Log.e(TAG, "Fail on getting push identifier: " + exception.getMessage());
            }

            executeLogin(loginPreferenceModel.getName(), loginPreferenceModel.getProfileId(),
                    loginPreferenceModel.getEmail(), loginPreferenceModel.getBirthday(),
                    loginPreferenceModel.getGender(), loginPreferenceModel.getAccessToken(),
                    pushId, ConstantsTypes.PLATFORM, Locale.getDefault().getLanguage(), false);
        } catch (Exception ex) {
            Log.e(TAG, "Fail during preference login: " + ex.getMessage());
        }
    }

    private void executeLogin(String name, String userId, String email, String birthday, String gender, String accessToken, String pushId, String platform, String language, boolean isTermsOfUse) {
        showDialog(ProgressDialog.STYLE_SPINNER, null, getString(R.string.connecting_to_server), true, false);

        try {
            Subscription subscription = retrofit.create(NetworkInterface.class).doLogin(
                    name, userId, email, birthday, gender, accessToken, pushId, platform, language, isTermsOfUse)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(result -> {
                        getProgressDialog().dismiss();
                        if (result.isStatus()) {
                            if (!result.getData().isTermsOfUse()) {
                                View mView = LayoutInflater.from(this).inflate(R.layout.alert_about_terms, null, false);
                                AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
                                mBuilder.setView(mView);
                                final AlertDialog mAlertDialog = mBuilder.show();
                                mView.findViewById(R.id.tvAccept).setOnClickListener(v -> {
                                    mAlertDialog.dismiss();
                                    executeLogin(name, userId, email, birthday, gender, accessToken, pushId, platform, language, true);
                                });
                                mView.findViewById(R.id.tvRefuse).setOnClickListener(v -> mAlertDialog.dismiss());
                            } else {
                                savePreferencesAndLogin(name, userId, email, birthday, gender, accessToken, result);
                            }
                        } else {
                            throwableError.call(new InvalidObjectException(result.getMessage()));
                        }
                    }, throwableError);

            getProgressDialog().setOnKeyListener((dialog, keyCode, event) -> {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.dismiss();
                    subscription.unsubscribe();
                    Toast.makeText(LoginActivity.this, R.string.login_cancelled, Toast.LENGTH_SHORT).show();
                }
                return true;
            });

        } catch (Exception e) {
            Log.e(TAG, "Fail during getting token id from FCM: " + e.getMessage());
            throwableError.call(new InvalidParameterException(getString(R.string.login_with_facebook_error)));
        }
    }

    private void savePreferencesAndLogin(String name, String userId, String email, String birthday, String gender, String accessToken, LoginModel result) {
        LoginPreferenceModel mPrefLogin = new LoginPreferenceModel(name, userId, email, gender, birthday, accessToken);
        mPrefLogin.saveOnPreference(sharedPreferences);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable(LOGIN_SERIALIZATION, result);
        mBundle.putInt(LOGIN_NOTIFICATIONS, result.getNotifications());
        mBundle.putString(LOGIN_TOKEN, mPrefLogin.getAccessToken());
        Intent mIntent = new Intent(this, MainActivity.class);
        mIntent.putExtras(mBundle);
        startActivity(mIntent);
        this.finish();
    }

    private void clearPreference() {
        try {
            sharedPreferences.edit().remove(SharedPreferencesComponent.LOGIN_PREFERENCES).apply();
        } catch (Exception ex) {
            Log.e(TAG, "Fail on erasing preference");
        } finally {
            loginPreferenceModel = null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
