package br.com.friendlydonations.shared.form;

import android.support.annotation.NonNull;

/**
 * Created by brunogabriel on 25/03/17.
 */

public class NotEmptyValidator implements BaseValidator<String> {

    @Override
    public boolean isValid(@NonNull String s) {
        return !s.isEmpty();
    }
}
