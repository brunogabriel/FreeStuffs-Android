package br.com.friendlydonations.views.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.widget.Toast;

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
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.Locale;

import br.com.friendlydonations.R;
import br.com.friendlydonations.managers.BaseActivity;
import br.com.friendlydonations.utils.TypefaceMaker;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by brunogabriel on 8/27/16.
 */
public class LoginActivity extends BaseActivity {

    public static final String TAG = "LOGINACTIVITY";

    @BindView(R.id.tvAboutTerms)
    protected AppCompatTextView tvAboutTerms;

    @BindView(R.id.tvFacebookLogin)
    protected AppCompatTextView tvFacebookLogin;

    // Typefaces
    protected Typeface mRobotoRegular;
    protected Typeface mMonserratRegular;

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
        setupTypefaces();
        initUI();
        setupFacebookSDK();
    }

    @Override
    public void initUI() {
        // @empty
    }

    @Override
    public void setupTypefaces() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                mMonserratRegular = TypefaceMaker.createTypeFace(LoginActivity.this, TypefaceMaker.FontFamily.MontserratRegular);
                mRobotoRegular = TypefaceMaker.createTypeFace(LoginActivity.this, TypefaceMaker.FontFamily.RobotoRegular);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                setTypeface(mRobotoRegular, tvAboutTerms);
                setTypeface(mMonserratRegular, tvFacebookLogin);
            }
        }.execute();
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

        AccessToken currentAccessToken = AccessToken.getCurrentAccessToken();
        Profile currentProfile = Profile.getCurrentProfile();

        /**  AccessToken.refreshCurrentAccessTokenAsync(new AccessToken.AccessTokenRefreshCallback() {
            @Override
            public void OnTokenRefreshed(AccessToken accessToken) {
                 int x = 1 + 2;
            }

            @Override
            public void OnTokenRefreshFailed(FacebookException exception) {
                int x = 1 + 2;
            }
        }); **/

    }

    @OnClick(R.id.viewFacebookLogin)
    protected void onClickViewFaceBookLogin() {
        //LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email", "user_birthday"));
        Intent loginIntent = new Intent();
        loginIntent.setClass(this, MainActivity.class);
        startActivity(loginIntent);
        this.finish();
    }

    private void requestGraphAPI(final LoginResult loginResult) {
        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(final JSONObject object, GraphResponse response) {
                createFacebookUser(mProfile, object, loginResult.getAccessToken().getToken());
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, birthday, gender, email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    public void createFacebookUser(Profile profile, JSONObject object, String accessToken) {
        String pushToken = "";

        try {
            pushToken = FirebaseInstanceId.getInstance().getToken();
        } catch (Exception e) {
            Log.e(TAG, "Fail during getting token id from FCM: " + e.getMessage());
        }



        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", profile.getName());
            jsonObject.put("userId", profile.getId());
            jsonObject.put("email", object.optString("email", ""));
            jsonObject.put("birthday", object.optString("gender", ""));
            jsonObject.put("gender", object.optString("gender", ""));
            jsonObject.put("token", accessToken);
            jsonObject.put("pushId", pushToken);
            jsonObject.put("platform", "android");
            jsonObject.put("language", Locale.getDefault().getLanguage());
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
