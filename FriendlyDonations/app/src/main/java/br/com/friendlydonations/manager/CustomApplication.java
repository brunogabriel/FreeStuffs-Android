package br.com.friendlydonations.manager;

import android.app.Application;
import com.facebook.appevents.AppEventsLogger;
import java.util.Locale;
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

    public static final String PLATFORM = "android";

    private NetworkComponent networkComponent;
    public static Locale locale;

    @Override
    public void onCreate() {
        super.onCreate();
        initLocale();
        initFacebookSDK();
        initializeComponents();
        initializeDaggerComponents();
    }

    private void initLocale() {
        // TODO: Get locale when app start and will be used during all the time
        locale = new Locale("pt", "BR");
    }

    private void initFacebookSDK() {
//        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }

    private void initializeDaggerComponents() {
        networkComponent = DaggerNetworkComponent.builder()
                .customApplicationModule(new CustomApplicationModule(this))
                .networkModule(new NetworkModule(NetworkModule.BASE_URL))
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
}
