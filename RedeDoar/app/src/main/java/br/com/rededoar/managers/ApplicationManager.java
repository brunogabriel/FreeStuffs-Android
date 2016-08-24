package br.com.rededoar.managers;

import android.app.Application;

/**
 * Created by brunogabriel on 8/23/16.
 */
public class ApplicationManager extends Application {

    public static final String TAG = "APPLICATION_MANAGER";
    public static boolean DEVELOPER_MODE = true;

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
