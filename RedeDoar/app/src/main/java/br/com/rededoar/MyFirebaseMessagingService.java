package br.com.rededoar;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by brunogabriel on 8/26/16.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "RDFirebaseMessaging";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "onMessageReceived");
        sendNotification();
    }

    private void sendNotification() {
        // TODO: Make a Push generator
    }

}
