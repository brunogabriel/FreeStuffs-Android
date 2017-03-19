package br.com.friendlydonations.shared;

import android.app.Application;
import android.support.annotation.NonNull;

import com.facebook.appevents.AppEventsLogger;

import br.com.friendlydonations.BuildConfig;
import br.com.friendlydonations.R;
import br.com.friendlydonations.dagger.component.DaggerNetworkComponent;
import br.com.friendlydonations.dagger.component.NetworkComponent;
import br.com.friendlydonations.dagger.module.CustomApplicationModule;
import br.com.friendlydonations.dagger.module.NetworkModule;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by brunogabriel on 05/02/17.
 */

public class CustomApplication extends Application {

    private NetworkComponent networkComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initFacebookSDK();
        initializeComponents();
        initializeDaggerComponents();
    }

    private void initFacebookSDK() {
        AppEventsLogger.activateApp(this);
    }

    private void initializeDaggerComponents() {
        networkComponent = DaggerNetworkComponent.builder()
                .customApplicationModule(new CustomApplicationModule(this))
                .networkModule(new NetworkModule(BuildConfig.base_url))
                .build();
    }

    private void initializeComponents() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    public NetworkComponent getNetworkComponent() {
        return networkComponent;
    }

    /**
     * Helper to permit mock tests instrumental context
     * @param baseUrl A non null url that represents custom environment
     */
    public void useCustomBaseURL (@NonNull String baseUrl) {
        networkComponent = DaggerNetworkComponent.builder()
                .customApplicationModule(new CustomApplicationModule(this))
                .networkModule(new NetworkModule(baseUrl))
                .build();
    }
}
