package br.com.rededoar.managers;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

/**
 * Created by brunogabriel on 8/23/16.
 */
public class ApplicationManager extends Application {

    public static final String TAG = "APPLICATION_MANAGER";
    public static boolean DEVELOPER_MODE = true;

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }
}
