package br.com.friendlydonations.shared.form;

import android.support.annotation.NonNull;

/**
 * Created by brunogabriel on 25/03/17.
 */

public class PasswordValidator implements BaseValidator<String> {

    @Override
    public boolean isValid(@NonNull String password) {
        return password.length() >= 6;
    }
}
