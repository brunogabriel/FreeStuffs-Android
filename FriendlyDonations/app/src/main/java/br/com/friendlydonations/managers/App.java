package br.com.friendlydonations.managers;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.karumi.dexter.Dexter;

import br.com.friendlydonations.R;
import br.com.friendlydonations.dagger.components.DaggerNetComponent;
import br.com.friendlydonations.dagger.components.NetComponent;
import br.com.friendlydonations.dagger.modules.AppModule;
import br.com.friendlydonations.dagger.modules.NetModule;
import br.com.friendlydonations.models.login.LoginModel;
import br.com.friendlydonations.network.NetworkInterface;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by brunogabriel on 8/27/16.
 */
public class App extends Application {

    private NetComponent mNetcomponent;
    private LoginModel loginModel;
    
    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        Dexter.initialize(this);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        mNetcomponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(NetworkInterface.SERVER_URL))
                .build();
    }

    public NetComponent getmNetcomponent() {
        return mNetcomponent;
    }

    public void setLoginModel(LoginModel loginModel) {
        this.loginModel = loginModel;
    }

    public LoginModel getLoginModel() {
        return this.loginModel;
    }
}
