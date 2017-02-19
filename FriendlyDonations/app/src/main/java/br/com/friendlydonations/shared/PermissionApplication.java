package br.com.friendlydonations.shared;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 * Created by brunogabriel on 18/02/17.
 */

public class PermissionApplication {

    public static boolean areAllPermissionsEnabled(Context context, String[] permissions) {
        if (permissions != null) {
            for (String permission: permissions) {
                if (ContextCompat.checkSelfPermission(context, permission) != PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isPermissionEnabled(Context context, String permission) {
        return permission != null ? ContextCompat.checkSelfPermission(context, permission) == PERMISSION_GRANTED :  true;
    }
}
