package br.com.friendlydonations.shared;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.annimon.stream.Stream;
import com.annimon.stream.function.Consumer;

/**
 * Created by brunogabriel on 18/03/17.
 */

public class PermissionsHelper {


    private static final int PERMISSION_GRANTED = 1;
    private Context context;

    public PermissionsHelper(Context context) {
        this.context = context;
    }
    public boolean areAllPermissionsEnabled(@NonNull String[] permissions) {

        for (String permission: permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PERMISSION_GRANTED) {
                return false;
            }
        }

        return true;
    }

    public boolean isPermissionEnabled(@NonNull String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PERMISSION_GRANTED;
    }

}
