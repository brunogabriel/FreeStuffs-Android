package br.com.friendlydonations.shared;

import android.app.Application;

import br.com.friendlydonations.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by brunogabriel on 24/04/17.
 */

public class CustomApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initializeCalligraphy();
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
