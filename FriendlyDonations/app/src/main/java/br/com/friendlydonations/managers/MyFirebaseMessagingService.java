package br.com.friendlydonations.managers;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by brunogabriel on 8/27/16.
 */
public class MyFirebaseMessagingService  extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        sendNotification();
    }

    private void sendNotification() {
        // TODO: Make a Push generator to a views.notifications (create interpreter json)
    }


}
