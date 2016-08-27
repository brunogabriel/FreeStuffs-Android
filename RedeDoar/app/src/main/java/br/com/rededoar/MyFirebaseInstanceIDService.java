package br.com.rededoar;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by brunogabriel on 8/26/16.
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "RDFirebaseService";

    @Override
    public void onTokenRefresh() {
        // registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Token:" + refreshedToken);
    }


}
