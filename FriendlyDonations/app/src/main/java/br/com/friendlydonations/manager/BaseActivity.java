package br.com.friendlydonations.manager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;

import br.com.friendlydonations.dagger.component.NetworkComponent;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by brunogabriel on 05/02/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected NetworkComponent getNetworkComponent() {
       return ((CustomApplication) getApplication()).getNetworkComponent();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    protected boolean isNetworkEnabled() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        return mNetworkInfo != null && mNetworkInfo.isConnected();
    }
}