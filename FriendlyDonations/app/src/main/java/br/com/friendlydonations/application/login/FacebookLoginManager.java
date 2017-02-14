package br.com.friendlydonations.application.login;

/**
 * Created by brunogabriel on 11/02/17.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import rx.functions.Action3;

/**
 * Created by brunogabriel on 09/02/17.
 */

public class FacebookLoginManager {

    private static List<String> FACEBOOK_PERMISSIONS_FIELDS = Arrays.asList("public_profile", "email", "user_birthday");
    private final FacebookCallback<LoginResult> facebookCallback;
    private ProfileTracker profileTracker;
    private Profile facebookProfile;
    private CallbackManager callbackManager;
    private Action3<JSONObject, GraphResponse, Profile> facebookLoginAction;

    public FacebookLoginManager(@NonNull Action3<JSONObject, GraphResponse, Profile> loginFacebookAction,
                                @NonNull CallbackManager callbackManager,
                                @NonNull FacebookCallback<LoginResult> facebookCallback) {
        this.facebookLoginAction = loginFacebookAction;
        this.callbackManager = callbackManager;
        this.facebookCallback = facebookCallback;
        initSDK();
    }

    public void initSDK() {
        LoginManager.getInstance().registerCallback(callbackManager, facebookCallback);
    }

    public void requestGraphAPI(@NonNull LoginResult loginResult) {
        GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), createGraphJSONCallback());
        graphRequest.setParameters(requestBundle());
        graphRequest.executeAsync();
    }

    public void requestActivityLogin(@NonNull Activity activity) {
        LoginManager.getInstance().logInWithReadPermissions(activity, FACEBOOK_PERMISSIONS_FIELDS);
    }

    public void setFacebookProfile(Profile facebookProfile) {
        this.facebookProfile = facebookProfile;
    }

    public ProfileTracker getProfileTracker() {
        return profileTracker;
    }

    public void setProfileTracker(ProfileTracker profileTracker) {
        this.profileTracker = profileTracker;
    }

    private Bundle requestBundle() {
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, birthday, gender, email");
        return parameters;
    }

    private GraphRequest.GraphJSONObjectCallback createGraphJSONCallback() {
        return (jsonObject, graphResponse) -> facebookLoginAction.call(jsonObject, graphResponse, facebookProfile);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
