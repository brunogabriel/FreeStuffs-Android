package br.com.friendlydonations.dagger.modules;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by brunogabriel on 26/10/16.
 */

@Module
public class SharedPreferencesModule {
    Application application;

    public SharedPreferencesModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application getApplication() {
        return application;
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }
}
