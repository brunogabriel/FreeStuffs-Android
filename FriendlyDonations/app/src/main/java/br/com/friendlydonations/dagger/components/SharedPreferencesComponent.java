package br.com.friendlydonations.dagger.components;

import android.content.SharedPreferences;

import javax.inject.Singleton;

import br.com.friendlydonations.dagger.modules.SharedPreferencesModule;
import dagger.Component;

/**
 * Created by brunogabriel on 26/10/16.
 */

@Singleton
@Component(modules = {SharedPreferencesModule.class})
public interface SharedPreferencesComponent {
    SharedPreferences getSharedPreferences();
}
