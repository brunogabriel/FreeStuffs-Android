package br.com.friendlydonations.application.login;

import android.app.Activity;
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

public class FacebookSocialLogin {

    private static List<String> FACEBOOK_LOGIN_PERMISSIONS = Arrays.asList("public_profile", "email", "user_birthday");
    private ProfileTracker profileTracker;
    private Profile facebookProfile;
    private Action3<JSONObject, GraphResponse, Profile> loginWithFacebook;

    public FacebookSocialLogin(Action3<JSONObject, GraphResponse, Profile> loginWithFacebook) {
        this.loginWithFacebook = loginWithFacebook;
    }

    public void initSDK(@NonNull CallbackManager callbackManager,
                        @NonNull LoginManager loginManager,
                        @NonNull FacebookCallback<LoginResult> facebookCallback) {
        loginManager.registerCallback(callbackManager, facebookCallback);
    }

    public void requestGraphAPI(@NonNull LoginResult loginResult) {
        GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), graphRequest());
        graphRequest.setParameters(requestBundle());
        graphRequest.executeAsync();
    }

    public void requestActivityLogin(@NonNull Activity activity) {
        LoginManager.getInstance().logInWithReadPermissions(activity, FACEBOOK_LOGIN_PERMISSIONS);
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

    private GraphRequest.GraphJSONObjectCallback graphRequest() {
        return (jsonObject, graphResponse) -> loginWithFacebook.call(jsonObject, graphResponse, facebookProfile);
    }
}
