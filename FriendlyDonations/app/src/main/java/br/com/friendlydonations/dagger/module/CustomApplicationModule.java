package br.com.friendlydonations.dagger.module;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by brunogabriel on 05/02/17.
 */

@Module
public class CustomApplicationModule {

    Application application;

    public CustomApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return application;
    }
}
