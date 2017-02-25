package br.com.friendlydonations.helpers;

import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.plugins.RxJavaHooks;
import rx.schedulers.Schedulers;

/**
 * Created by brunogabriel on 25/02/17.
 */

public class RxHelperTest {

    public void setUp() {
        // Override RxJava schedulers
        RxJavaHooks.setOnIOScheduler(scheduler -> Schedulers.immediate());

        RxJavaHooks.setOnComputationScheduler(scheduler -> Schedulers.immediate());

        RxJavaHooks.setOnNewThreadScheduler(scheduler -> Schedulers.immediate());

        // Override RxAndroid schedulers
        final RxAndroidPlugins rxAndroidPlugins = RxAndroidPlugins.getInstance();
        rxAndroidPlugins.registerSchedulersHook(new RxAndroidSchedulersHook() {
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        });
    }

    public void tearDown() {
        RxJavaHooks.reset();
        RxAndroidPlugins.getInstance().reset();
    }
}
