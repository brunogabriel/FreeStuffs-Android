package br.com.friendlydonations.shared.views;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;

import com.jakewharton.rxbinding.view.RxView;

import uk.co.chrisjenx.calligraphy.CalligraphyUtils;

/**
 * Created by brunogabriel on 17/02/17.
 */

public class RxTextViewUtils {
    private static String MONTSERRAT_REGULAR = "fonts/Montserrat-Regular.ttf";
    private static String ROBOTO_REGULAR = "fonts/Roboto-Regular.ttf";

    public static void applyFormTypefaceRule(Context context, EditText editText) {
        if (context != null && editText != null) {
            RxView.focusChanges(editText)
                    .subscribe(result -> CalligraphyUtils.applyFontToTextView(context, editText,
                            result ? MONTSERRAT_REGULAR: ROBOTO_REGULAR));
        }
    }
}
