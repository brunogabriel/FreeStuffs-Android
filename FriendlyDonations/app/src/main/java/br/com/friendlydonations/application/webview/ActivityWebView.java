package br.com.friendlydonations.application.webview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import java.net.MalformedURLException;
import java.net.URL;

import br.com.friendlydonations.BuildConfig;
import br.com.friendlydonations.R;
import br.com.friendlydonations.shared.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by brunogabriel on 16/03/17.
 */

public class ActivityWebView extends BaseActivity implements AppWebView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.webview)
    WebView webview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        unbinder = ButterKnife.bind(this);
        // Title by intent
        // URL By Intent
        setupToolbar(toolbar, "Teste", null, true, true);
        try {
            URL url = new URL(new URL(BuildConfig.base_url), "/links/terms_and_policy");
            webview.loadUrl(url.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
