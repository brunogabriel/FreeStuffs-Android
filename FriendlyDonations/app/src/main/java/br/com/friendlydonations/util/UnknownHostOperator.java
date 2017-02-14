package br.com.friendlydonations.util;

import java.net.UnknownHostException;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by brunogabriel on 11/02/17.
 */

public class UnknownHostOperator<T> implements Observable.Operator<T, T> {

    private Action0 unknownAction;

    public UnknownHostOperator(Action0 unknownAction) {
        this.unknownAction = unknownAction;
    }

    public static <T> Observable.Operator<T,T> getUnknownHostOperator(Action0 unknownAction){
        if (unknownAction == null) {
            return null;
        }

        return new UnknownHostOperator<>(unknownAction);
    }

    @Override
    public Subscriber<? super T> call(Subscriber<? super T> subscriber) {
        return new Subscriber<T>() {
            @Override
            public void onCompleted() {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                if (!subscriber.isUnsubscribed()) {
                    if (throwable instanceof UnknownHostException) {
                        unknownAction.call();
                        subscriber.onCompleted();
                    } else {
                        subscriber.onError(throwable);
                    }
                }
            }

            @Override
            public void onNext(T t) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(t);
                }
            }
        };
    }
}
