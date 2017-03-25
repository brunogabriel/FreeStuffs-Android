package br.com.friendlydonations.shared.form;

import android.support.annotation.NonNull;

/**
 * Created by brunogabriel on 25/03/17.
 */

public interface BaseValidator<T>{
    boolean isValid(@NonNull T t);
}
