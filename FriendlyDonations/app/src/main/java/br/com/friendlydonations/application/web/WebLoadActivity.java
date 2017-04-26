package br.com.friendlydonations.application.web;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.MalformedURLException;
import java.net.URL;

import br.com.friendlydonations.BuildConfig;
import br.com.friendlydonations.R;
import br.com.friendlydonations.shared.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by brunogabriel on 25/04/17.
 */

public class WebLoadActivity extends BaseActivity implements WebLoadView {

    public enum WebUrlPath {
        TERMS("terms_and_policy");

        private String value;

        WebUrlPath(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static final String URL_PATH_EXTRA = "url.path.extra";
    public static final String TITLE_EXTRA = "title.extra";

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.web_view)
    protected WebView webView;

    @BindView(R.id.swipe_refresh_layout)
    protected SwipeRefreshLayout swipeRefreshLayout;

    private WebLoadPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_load);
        unbinder = ButterKnife.bind(this);

        // Extras
        String titleExtra = getIntent().getExtras().getString(TITLE_EXTRA, null);
        String urlPathExtra = getIntent().getExtras().getString(URL_PATH_EXTRA, null);

        setupToolbar(toolbar, titleExtra, null, true, true);
        presenter = new WebLoadPresenter(this);
        presenter.refreshUrl(urlPathExtra);
        onSetSwipeToRefreshColor(swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> webView.reload());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLoad(String urlPathName) {
        swipeRefreshLayout.setRefreshing(true);
        try {
            URL url =  new URL(new URL(BuildConfig.base_url), urlPathName);
            webView.loadUrl(url.toString());
        } catch (MalformedURLException e) {
            webView.loadUrl(BuildConfig.base_url);
        }
    }
}
