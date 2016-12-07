package br.com.friendlydonations.dagger.components;

import javax.inject.Singleton;

import br.com.friendlydonations.dagger.modules.AppModule;
import br.com.friendlydonations.dagger.modules.NetModule;
import br.com.friendlydonations.views.activities.LoginActivity;
import br.com.friendlydonations.views.activities.MainActivity;
import br.com.friendlydonations.views.fragments.DonateFragment;
import br.com.friendlydonations.views.fragments.HomeFragment;
import dagger.Component;

/**
 * Created by brunogabriel on 26/10/16.
 */

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {
    void inject(LoginActivity loginActivity);
    void inject(HomeFragment homeFragment);
    void inject(DonateFragment donateFragment);
}
