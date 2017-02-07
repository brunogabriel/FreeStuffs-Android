package br.com.friendlydonations.dagger.component;

import javax.inject.Singleton;

import br.com.friendlydonations.application.login.LoginActivity;
import br.com.friendlydonations.dagger.module.CustomApplicationModule;
import br.com.friendlydonations.dagger.module.NetworkModule;
import dagger.Component;

/**
 * Created by brunogabriel on 05/02/17.
 */

@Singleton
@Component(modules = {CustomApplicationModule.class, NetworkModule.class})
public interface NetworkComponent {
    void inject(LoginActivity loginActivity);
}
