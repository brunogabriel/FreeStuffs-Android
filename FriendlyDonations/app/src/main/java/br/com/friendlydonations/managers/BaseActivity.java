package br.com.friendlydonations.managers;

import android.content.Context;
import android.graphics.Typeface;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;

import android.Manifest;
import android.app.Activity;
import android.content.IntentSender;
import android.content.pm.PackageManager;

/**
 * Created by brunogabriel on 8/23/16.
 */
public abstract class BaseActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

protected static final int PERMISSIONS = 0;
    public static String TAG = "BASE_ACTIVITY";

    // Constants GPS
    public static final int GPS_UPDATE_INTERVAL = 5000;
    public static final int GPS_FAST_UPDATE_INTERVAL = 2500;

    // GPS Vars
    protected GoogleApiClient mGoogleApiClient;
    protected LocationRequest mLocationRequest;
    protected Location mCurrentLocation;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    protected void applyTypefaceToolbar(Toolbar mToolbar, Typeface typeface) {
        if (mToolbar != null && typeface != null) {
            try {
                for(int i = 0; i < mToolbar.getChildCount(); i++){
                    View view = mToolbar.getChildAt(i);
                    if(view instanceof TextView){
                        TextView tv = (TextView) view;
                        if(tv.getText().equals(mToolbar.getTitle())){
                            tv.setTypeface(typeface);
                            break;
                        }
                    }
                }
            } catch (Exception ex) {
                Log.e(TAG, "Fail to apply typeface on toolbar: " + ex.getMessage());
            }
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

    // GPS
    // # GPS
    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("BASE_ACTIVITY", "onConnectionSuspended: " + i);
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location != null) {
            this.mCurrentLocation = location;
        }
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    public synchronized void callConnection() {
        mGoogleApiClient = new GoogleApiClient.Builder(this).
                addConnectionCallbacks(this).addOnConnectionFailedListener(this).
                addApi(LocationServices.API).build();

        mGoogleApiClient.connect();
    }

    public void initLocation(Activity mActivity) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(GPS_UPDATE_INTERVAL); // Intervalo de Atualizacoes
        mLocationRequest.setFastestInterval(GPS_FAST_UPDATE_INTERVAL); // Intervalo Rapido
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); // Prior alta do app

        final Activity mFinalActivity = mActivity;
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(mFinalActivity, 1000);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });

    }

    private void startLocationUpdate() {
        initLocation(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION},
                        PERMISSIONS);
            }
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    private void stopLocationUpdate() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (permissions == null || permissions.length <= 0) return;
        if (grantResults == null || grantResults.length <= 0) return;

        switch (requestCode) {
            case PERMISSIONS: {
                int totalPermissions = permissions.length;
                for (int i = 0; i < totalPermissions; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        if (permissions[i].equalsIgnoreCase(Manifest.permission.ACCESS_FINE_LOCATION) ||
                                permissions[i].equalsIgnoreCase(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                            startLocationUpdate();
                        }
                    }
                }
                break;
            }
        }
    }



}