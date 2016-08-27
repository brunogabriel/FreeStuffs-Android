package br.com.friendlydonations.managers;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by brunogabriel on 8/27/16.
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        // registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
    }

}
