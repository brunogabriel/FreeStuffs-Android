package br.com.friendlydonations.application.main;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.friendlydonations.adapters.BadgeTabViewAdapter;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Created by brunogabriel on 11/02/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    @Mock
    MainView mainView;

    @Mock
    BadgeTabViewAdapter badgeTabViewAdapter;

    private MainPresenter mainPresenter;

    @Before
    public void setUp() {
        mainPresenter = new MainPresenter(mainView);
    }

    @Test
    public void shouldUpdateFragmentListAfterInit() {
        assertThat(mainPresenter.getFragmentListSize(), is(4));
    }

    @Test
    public void shouldCallSetupTabsWhenInitIsCalled() {
        // when
        mainPresenter.init(badgeTabViewAdapter);

        // then
        verify(mainView).setupTabStructure(anyInt(), any());
    }

    @Test
    public void shouldNotCallSetupTabsWhenInitIsCalledWithNullReference() {
        // given
        BadgeTabViewAdapter anyBadge = null;

        // when
        mainPresenter.init(anyBadge);

        // then
        verify(mainView, never()).setupTabStructure(anyInt(), any());
    }


}
