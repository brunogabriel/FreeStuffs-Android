package br.com.friendlydonations.manager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import br.com.friendlydonations.R;
import br.com.friendlydonations.dagger.component.NetworkComponent;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by brunogabriel on 05/02/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected NetworkComponent getNetworkComponent() {
       return ((CustomApplication) getApplication()).getNetworkComponent();
    }

    protected void defineBaseLocale() {
        // TODO: Need to be removed, because only to test pt-br
        try {
            Locale locale = CustomApplication.locale;
            Locale.setDefault(locale);
            Configuration config = getBaseContext().getResources().getConfiguration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());
        } catch (Exception exception) {
            Log.e(TAG, "Fail to define base locale: " + exception.getMessage());
        }
    }

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

    protected void applyTypefaceToolbar(Toolbar mToolbar, Typeface typeface) {
        if (mToolbar != null && typeface != null) {
            try {
                for(int i = 0; i < mToolbar.getChildCount(); i++){
                    View view = mToolbar.getChildAt(i);
                    if(view instanceof TextView){
                        TextView tv = (TextView) view;
                        if(tv.getText().equals(mToolbar.getTitle())){
                            tv.setTypeface(typeface);
                        }
                    }
                }
            } catch (Exception ex) {
                Log.e(TAG, "Fail to apply typeface on toolbar: " + ex.getMessage());
            }
        }
    }

    public void showSimpleSnackbar(View view, String message, int duration) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    public boolean isNetworkEnable() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        return mNetworkInfo != null && mNetworkInfo.isConnected();
    }

    public void showDialog(int progressDialogStyle, String title, String message, boolean isIndeterminate, boolean isCancelable) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this, R.style.FriendlyDonationsTheme_ProgressDialogStyle);
        }

        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        progressDialog.setProgressStyle(progressDialogStyle);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.setIndeterminate(isIndeterminate);
        progressDialog.setCancelable(isCancelable);

        progressDialog.show();
    }

    public ProgressDialog getProgressDialog() {
        return this.progressDialog;
    }

    public void showToast(String message, int duration) {
        if (!TextUtils.isEmpty(message)) {
            Toast.makeText(this, message, duration).show();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
//        defineBaseLocale();
    }
}