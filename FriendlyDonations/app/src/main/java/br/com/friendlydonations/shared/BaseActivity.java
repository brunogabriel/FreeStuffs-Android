package br.com.friendlydonations.shared;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import butterknife.Unbinder;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by brunogabriel on 24/04/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected Unbinder unbinder;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Try to unbind Butterknife
        if (unbinder != null) {
            try {
                unbinder.unbind();
            } catch (Exception exception) {}
        }
    }
}
