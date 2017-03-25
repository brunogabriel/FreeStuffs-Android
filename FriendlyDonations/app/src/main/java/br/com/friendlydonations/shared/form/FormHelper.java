package br.com.friendlydonations.shared.form;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.widget.CheckBox;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxCompoundButton;
import com.jakewharton.rxbinding.widget.RxTextView;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

import static com.jakewharton.rxbinding.widget.RxTextView.textChanges;

/**
 * Created by brunogabriel on 25/03/17.
 */

public class FormHelper {

    public static Observable<Boolean> fieldObservable (@NonNull TextInputLayout layout,
                                                       @NonNull TextInputEditText editText,
                                                       @NonNull String erroMessage,
                                                       @NonNull BaseValidator baseValidator) {


        Observable<Boolean> elementObservable = RxTextView.textChanges(editText)
                .map(input -> baseValidator.isValid(input.toString())).observeOn(AndroidSchedulers.mainThread());

        elementObservable.subscribe(isValid -> layout.setError(isValid ? "": erroMessage));

        return elementObservable;
    }

    public static Observable<Boolean> checkBoxObservable(@NonNull CheckBox checkBox) {
        return RxCompoundButton.checkedChanges(checkBox).map(input -> input).observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Boolean> compositeObservable (@NonNull TextInputLayout layout,
                                                           @NonNull TextInputEditText editText,
                                                           @NonNull TextInputEditText compostEditText,
                                                           @NonNull String erroMessage,
                                                           @NonNull BaseValidator baseValidator) {


        Observable<Boolean> generalObservable = textChanges(editText)
                .map(input -> baseValidator.isValid(input.toString())).observeOn(AndroidSchedulers.mainThread());

        Observable<String> compareObservable = textChanges(editText).map(input -> input.toString()).observeOn(AndroidSchedulers.mainThread());
        Observable<String> equalsObservable = textChanges(compostEditText).map(input -> input.toString()).observeOn(AndroidSchedulers.mainThread());

        Observable<Boolean> composite = Observable.combineLatest(generalObservable,
                compareObservable, equalsObservable, (a, b, c) -> a && b.equals(c))
                .distinctUntilChanged().observeOn(AndroidSchedulers.mainThread());
        composite.subscribe(isValid -> layout.setError(isValid ? "":  erroMessage));

        return composite;
    }
}
