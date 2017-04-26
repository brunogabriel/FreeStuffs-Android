package br.com.friendlydonations.application.web;

import android.content.Intent;
import android.support.test.espresso.web.webdriver.Locator;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.TextView;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Locale;

import br.com.friendlydonations.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.web.assertion.WebViewAssertions.webMatches;
import static android.support.test.espresso.web.sugar.Web.onWebView;
import static android.support.test.espresso.web.webdriver.DriverAtoms.findElement;
import static android.support.test.espresso.web.webdriver.DriverAtoms.getText;
import static br.com.friendlydonations.application.web.WebLoadActivity.URL_PATH_EXTRA;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;

/**
 * Created by brunogabriel on 25/04/17.
 */

@RunWith(AndroidJUnit4.class)
public class WebLoadActivityInstrumentalTest {

    private static final String LOREM_CONTENT = "Lorem Ipsum";
    private static final String MOCK_URL_PATH = "/anypath";

    @Rule
    public ActivityTestRule<WebLoadActivity> rule = new ActivityTestRule(WebLoadActivity.class, true, false) {
        @Override
        protected void afterActivityLaunched() {
            onWebView(withId(R.id.web_view)).forceJavascriptEnabled();
            super.afterActivityLaunched();
        }
    };

    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    @Before
    public void setUp() {
        prepareMock();
    }

    @After
    public void tearDown() {

    }

    @Test
    public void shouldStartWithTitleAndHtmlBody() {
        // given
        String toolbarTitle = "anyTitle";

        // when
        rule.launchActivity(new Intent().putExtra(WebLoadActivity.TITLE_EXTRA, toolbarTitle).putExtra(URL_PATH_EXTRA, MOCK_URL_PATH));

        // then
        onWebView(withId(R.id.web_view))
                .withElement(findElement(Locator.ID, "content"))
                .check(webMatches(getText(), containsString(LOREM_CONTENT)));

        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
                .check(matches(withText(toolbarTitle)));
    }

    private void prepareMock() {
        String htmlContent = String.format(Locale.getDefault(), "<h1 id=\"content\">%s</h1>", LOREM_CONTENT);


        wireMockRule.stubFor(get(urlPathEqualTo(MOCK_URL_PATH))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/html")
                        .withBody(htmlContent)));

        wireMockRule.stubFor(get(urlPathEqualTo("/favicon.ico"))
                .willReturn(aResponse()
                        .withStatus(200)));
    }
}
