package br.com.friendlydonations.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

import br.com.friendlydonations.managers.BaseActivity;

/**
 * Created by brunogabriel on 29/09/16.
 */

public class ViewUtility {

    public static void showSnackBar(View rootView, String message, int snackSize, String actionUndo, View.OnClickListener onClickListener) {
        Snackbar snackbar = null;
        if (actionUndo != null && onClickListener != null) {
            snackbar = Snackbar.make(rootView, message, snackSize).setAction(actionUndo, onClickListener);
        } else {
            snackbar = Snackbar.make(rootView, message, snackSize);
        }

        snackbar.show();
    }
}
