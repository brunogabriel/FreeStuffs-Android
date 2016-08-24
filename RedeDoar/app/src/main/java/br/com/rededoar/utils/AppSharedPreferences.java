package br.com.rededoar.utils;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by brunogabriel on 8/23/16.
 */
public class AppSharedPreferences {

    public static final String TAG = "SHARED_PREF";

    public static void saveObject(Context context, String key, Object value) {

        if (context != null && key != null && value != null) {
            try {
                if (value instanceof String) {
                    PreferenceManager.getDefaultSharedPreferences(context).edit().putString(key, (String) value);
                } else if (value instanceof Integer) {
                    PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(key, (Integer) value);
                } else if (value instanceof Boolean) {
                    PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(key, (Boolean) value);
                }
            } catch (Exception e) {
                Log.e(TAG, "Fail to save object preference, key: " + key);
            }

        }
    }

    public static Object loadObject(Context context, String key, Object defaultValue) {
        try {
            if (context != null && key != null) {
                if (defaultValue instanceof String) {
                    return PreferenceManager.getDefaultSharedPreferences(context).getString(key, (String) defaultValue);
                } else if (defaultValue instanceof Integer) {
                    return PreferenceManager.getDefaultSharedPreferences(context).getInt(key, (Integer) defaultValue);
                } else if (defaultValue instanceof Boolean) {
                    return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, (Boolean) defaultValue);
                } else {
                    throw new Exception("Invalid type on default");
                }
            } else {
                throw new Exception("Invalid parameters");
            }
        } catch (Exception e) {
            Log.e(TAG, "Fail on load object, key: " +key + ", cause: " + e.getMessage());
            return null;
        }
    }

    public static boolean clearPreferences(Context context) {
        try {
            PreferenceManager.getDefaultSharedPreferences(context).edit().clear().commit();
            return true;
        } catch (Exception e) {
            Log.d(TAG, "error: " + e.getMessage());
            return false;
        }
    }
}
