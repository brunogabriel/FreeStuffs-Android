package br.com.friendlydonations.application.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

/**
 * Created by brunogabriel on 25/04/17.
 */

@RunWith(MockitoJUnitRunner.Silent.class)
public class WebLoadPresenterTest {

    WebLoadPresenter webLoadPresenter;

    @Mock
    WebLoadView webLoadView;

    @Before
    public void setUp() {
        webLoadPresenter = new WebLoadPresenter(webLoadView);
    }

    @Test
    public void shouldRefreshUrlOnWebLoadView() {
        // given
        String path = "anyPath";

        // when
        webLoadPresenter.refreshUrl(path);

        // then
        verify(webLoadView).onLoad(path);
    }
}
