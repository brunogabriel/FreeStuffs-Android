package br.com.friendlydonations.managers;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.karumi.dexter.Dexter;
import br.com.friendlydonations.utils.TypefaceMaker;

/**
 * Created by brunogabriel on 8/27/16.
 */
public class ApplicationManager extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        TypefaceMaker.getInstance();
        AppEventsLogger.activateApp(this);
        Dexter.initialize(this);
    }
}
