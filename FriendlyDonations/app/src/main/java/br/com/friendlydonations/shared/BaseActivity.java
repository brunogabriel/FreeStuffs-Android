package br.com.friendlydonations.shared;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import br.com.friendlydonations.R;
import br.com.friendlydonations.dagger.component.NetworkComponent;
import br.com.friendlydonations.shared.views.alert.AlertPermissionsSettingsDialog;
import butterknife.Unbinder;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.R.id.message;

/**
 * Created by brunogabriel on 05/02/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();
    private ProgressDialog progressDialog;
    protected Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected NetworkComponent getNetworkComponent() {
       return ((CustomApplication) getApplication()).getNetworkComponent();
    }

    protected void setupToolbar(Toolbar mToolbar, String mTitle, String mSubtitle, boolean isDisplayHomeAsUpEnabled, boolean isDisplayShowHomeEnabled) {
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(mTitle == null? "": mTitle);
                getSupportActionBar().setSubtitle(mSubtitle);
                getSupportActionBar().setDisplayHomeAsUpEnabled(isDisplayHomeAsUpEnabled);
                getSupportActionBar().setDisplayShowHomeEnabled(isDisplayShowHomeEnabled);
            }
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

    public void showSnackbarNetworkUnavailable(View view) {
        Snackbar.make(view, getString(R.string.network_not_detected), Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.settings),
                        settingsView -> startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0))
                .setActionTextColor(ContextCompat.getColor(this, R.color.colorError))
                .show();
    }

    public void showSimpleSnackbar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
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

    public void showMessageSnackBar(@NonNull View view, @NonNull String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void showPermissionsSettings() {
        AlertPermissionsSettingsDialog alertPermissionsSettingsDialog = new AlertPermissionsSettingsDialog();
        alertPermissionsSettingsDialog.show(getFragmentManager(), "");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}