package br.com.friendlydonations.shared.form;

import android.support.annotation.NonNull;
import android.text.TextUtils;

/**
 * Created by brunogabriel on 25/03/17.
 */

public class EmailValidator implements BaseValidator<String> {
    @Override
    public boolean isValid(@NonNull String email) {
        return !TextUtils.isEmpty(email)
                && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
