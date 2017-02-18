package br.com.friendlydonations.shared.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by brunogabriel on 11/02/17.
 */

public class FirebaseServiceID extends FirebaseInstanceIdService {

    private static final String TAG = FirebaseServiceID.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
//        String refreshToken = FirebaseInstanceId.getInstance().getDeviceToken();
    }

    public static String getDeviceToken() {
        try {
            return FirebaseInstanceId.getInstance().getToken();
        } catch (Exception firebaseTokenException) {
            Log.e(TAG, "Fail during geting token, cause: " + firebaseTokenException.getMessage());
            return "";
        }
    }
}
