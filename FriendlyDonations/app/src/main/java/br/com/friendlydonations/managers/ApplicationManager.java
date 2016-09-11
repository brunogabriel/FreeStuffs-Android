package br.com.friendlydonations.managers;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import br.com.friendlydonations.utils.TypefaceMaker;

/**
 * Created by brunogabriel on 8/27/16.
 */
public class ApplicationManager extends Application {


    public static final String TAG = "APPLICATION_MANAGER";

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        TypefaceMaker.getInstance();
        AppEventsLogger.activateApp(this);
    }

}
