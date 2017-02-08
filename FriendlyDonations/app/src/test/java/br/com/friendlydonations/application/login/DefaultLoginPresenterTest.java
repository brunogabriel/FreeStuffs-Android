package br.com.friendlydonations.application.login;

import com.facebook.CallbackManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.friendlydonations.manager.BaseActivity;
import br.com.friendlydonations.network.NetworkLogin;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * Created by brunogabriel on 07/02/17.
 */

@RunWith(MockitoJUnitRunner.Silent.class)
public class DefaultLoginPresenterTest {

    private DefaultLoginPresenter loginPresenter;

    @Mock LoginView loginView;
    @Mock CallbackManager callbackManager;
    @Mock NetworkLogin networkLogin;
    @Mock BaseActivity activity;

    @Before
    public void setUp() {
        loginPresenter = new DefaultLoginPresenter(loginView, callbackManager, networkLogin);
    }

    @Test
    public void shouldShowNetworkNotAvailableWhenNetworkDisable() {
        // given
        when(activity.isNetworkEnable()).thenReturn(false);

        // when
        loginPresenter.loginWithFacebook(activity);

        // then
        verify(loginView, times(1)).showNetworkNotAvailable();
    }

    @Test
    public void shouldSetCallbackManagerAfterPresenterCreated() {
        assertThat(loginPresenter.getCallbackManager(), is(callbackManager));
    }
}
