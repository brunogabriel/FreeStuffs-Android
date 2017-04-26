package br.com.friendlydonations.shared;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import br.com.friendlydonations.R;
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
            } catch (Exception exception) {}
        }
    }
}
