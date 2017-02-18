package br.com.friendlydonations.application.main;

import android.widget.FrameLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.friendlydonations.shared.adapter.DynamicTabPageAdapter;
import br.com.friendlydonations.shared.widgets.BadgeImageView;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by brunogabriel on 16/02/17.
 */

@RunWith(MockitoJUnitRunner.Silent.class)
public class MainPresenterTest {

    @Mock
    MainView view;

    @Mock
    DynamicTabPageAdapter adapter;

    @Mock
    BadgeImageView badgeImageView;

    @Mock
    FrameLayout frameLayout;

    private MainPresenter presenter;

    @Before
    public void setUp() {
        presenter = new MainPresenter(view, adapter);
    }

    @Test
    public void shouldSetValidAdapterSettingsWhenPresenterInitialize() {
        // when
        presenter.initialize();
        when(adapter.getCount()).thenReturn(4);

        // then
        verify(view).setAdapterSettings(adapter);
    }

    @Test
    public void shouldCreateCustomTabsWhenSetupTabIsCalled() {
        // given
        int tabCount = 4;

        // when
        when(adapter.badgeImageView(anyInt())).thenReturn(badgeImageView);
        when(badgeImageView.getRootView()).thenReturn(frameLayout);
        presenter.setupTab(4);

        // then
        for (int i = 0; i < tabCount; i++) {
            verify(view).setCustomView(i, frameLayout);
        }
        verify(view).changeTabAlpha(MainPresenter.TAB_ACTIVATED);
    }

    @Test
    public void shouldEmitNotificationWhenOnReceiverNotificationIsCalled() {
        // given
        when(adapter.badgeImageView(anyInt())).thenReturn(badgeImageView);
        when(badgeImageView.getRootView()).thenReturn(frameLayout);
        presenter.setupTab(4);

        // when
        presenter.onNotificationReceiver(1);

        // then
        verify(badgeImageView).setOrChangeNotificationCount(1, true);
    }

}
