package br.com.friendlydonations.shared;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import br.com.friendlydonations.R;
import br.com.friendlydonations.shared.network.RetrofitHelper;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by brunogabriel on 24/04/17.
 */

public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initializeCalligraphy();
        RetrofitHelper.injectContext(getApplicationContext());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private void initializeCalligraphy() {
        // Apply default typeface
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
