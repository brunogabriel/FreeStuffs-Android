package br.com.friendlydonations.shared.form;

import android.support.annotation.NonNull;

/**
 * Created by brunogabriel on 25/03/17.
 */

public class NameValidator implements BaseValidator<String> {

    @Override
    public boolean isValid(@NonNull String name) {
        return name.trim().split(" ").length > 1;
    }
}
