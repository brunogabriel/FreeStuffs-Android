package br.com.friendlydonations.models.login;

import android.content.SharedPreferences;
import android.nfc.Tag;
import android.util.Log;

import com.google.gson.Gson;
import java.io.Serializable;

import br.com.friendlydonations.dagger.components.SharedPreferencesComponent;

/**
 * Created by brunogabriel on 07/11/16.
 */

public class LoginPreferenceModel implements Serializable {

    public static final String TAG = "LOGIN_PREF_MODEL";

    private String name;
    private String profileId;
    private String email;
    private String gender;
    private String birthday;
    private String accessToken;

    public LoginPreferenceModel(String name, String profileId, String email, String gender, String birthday, String accessToken) {
        this.name = name;
        this.profileId = profileId;
        this.email = email;
        this.gender = gender;
        this.birthday = birthday;
        this.accessToken = accessToken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public static String toJSonStr(LoginPreferenceModel loginPreferenceModel) {

        try {
            Gson gson = new Gson();
            return gson.toJson(loginPreferenceModel);
        } catch (Exception ex) {
            Log.e(TAG, "Fail to convert object into json: " + ex.getMessage());
            return null;
        }
    }

    public static LoginPreferenceModel jsonToObject(String json) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(json, LoginPreferenceModel.class);
        } catch (Exception ex) {
            Log.e(TAG, "Fail to convert json into object: " + ex.getMessage());
            return null;
        }
    }

    public void saveOnPreference(SharedPreferences sharedPreferences) {
        if (sharedPreferences != null) {
            try {
                String strJson = toJSonStr(this);
                if (strJson != null && strJson.length() > 0) {
                    sharedPreferences.edit().putString(SharedPreferencesComponent.LOGIN_PREFERENCES, strJson).commit();
                }
            } catch (Exception ex) {
                Log.e(TAG, "Fail to save this object on preference: " + ex.getMessage());
            }
        }
    }
}
