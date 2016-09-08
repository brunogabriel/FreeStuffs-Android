package br.com.friendlydonations.managers;

import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.ButterKnife;

/**
 * Created by brunogabriel on 8/23/16.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public static String TAG = "BASE_ACTIVITY";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** if (ApplicationManager.DEVELOPER_MODE) {
            Log.d(TAG, "Starting development mode: StrictMode");
         StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().
                    detectAll().penaltyLog().build());
            ButterKnife.setDebug(true);
        } **/
    }

    public abstract void initUI();
    public abstract void setupTypefaces();

    protected void setupToolbar(Toolbar mToolbar, String mTitle, String mSubtitle, boolean
            isDisplayHomeAsUpEnabled, boolean isDisplayShowHomeEnabled) {

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setTitle(mTitle == null? "": mTitle);
            getSupportActionBar().setSubtitle(mSubtitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(isDisplayHomeAsUpEnabled);
            getSupportActionBar().setDisplayShowHomeEnabled(isDisplayShowHomeEnabled);

        }
    }

    protected void hideKeyboard() {
        View mView = getCurrentFocus();
        if (mView != null) {
            InputMethodManager mInputMethodManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);

            mInputMethodManager.hideSoftInputFromInputMethod(mView.getWindowToken(), 0);
        }
    }

    protected boolean isNetworkEnabled() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        return mNetworkInfo != null && mNetworkInfo.isConnected();
    }

    protected void setTypeface (Typeface mTypeface, TextView mTextView) {
        if (mTypeface != null && mTextView != null) {
            mTextView.setTypeface(mTypeface);
        }
    }

    protected boolean isLollipopSupport() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }
}