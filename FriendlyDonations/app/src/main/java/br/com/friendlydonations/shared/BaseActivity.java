package br.com.friendlydonations.shared;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import br.com.friendlydonations.R;
import br.com.friendlydonations.shared.network.RetrofitHelper;
import butterknife.Unbinder;
import retrofit2.Retrofit;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by brunogabriel on 24/04/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected String TAG = BaseActivity.class.getSimpleName();
    protected Unbinder unbinder;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    protected void setupToolbar(Toolbar mToolbar,
                                String mTitle,
                                String mSubtitle,
                                boolean isDisplayHomeAsUpEnabled,
                                boolean isDisplayShowHomeEnabled) {
        if (mToolbar != null) {

            setSupportActionBar(mToolbar);

            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(mTitle);
                getSupportActionBar().setSubtitle(mSubtitle);
                getSupportActionBar().setDisplayHomeAsUpEnabled(isDisplayHomeAsUpEnabled);
                getSupportActionBar().setDisplayShowHomeEnabled(isDisplayShowHomeEnabled);
            }
        }
    }

    protected void setToolbarTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    protected Retrofit getRetrofit() {
        return RetrofitHelper.getInstance().getRetrofit();
    }

    protected void onSetSwipeToRefreshColor(@NonNull SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorPrimary));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Try to unbind Butterknife
        if (unbinder != null) {
            try {
                unbinder.unbind();
            } catch (Exception exception) {
                Log.e(TAG, "Fail to unbind Butterknife");
            }
        }
    }
}
