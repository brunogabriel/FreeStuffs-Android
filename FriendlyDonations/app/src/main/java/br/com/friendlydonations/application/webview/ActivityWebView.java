package br.com.friendlydonations.application.webview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebView;

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

    public static final String TITLE_EXTRA = "TITLE_EXTRA";
    public static final String PATH_EXTRA = "PATH_EXTRA";
    private static final String TAG = ActivityWebView.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.webview)
    WebView webview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        unbinder = ButterKnife.bind(this);

        try {
            String title = getIntent().getStringExtra(TITLE_EXTRA);
            String path = getIntent().getStringExtra(PATH_EXTRA);
            setupToolbar(toolbar, title, null, true, true);
            URL url = new URL(new URL(BuildConfig.base_url), path);
            webview.loadUrl(url.toString());
        } catch (Exception exception) {
            Log.e(TAG, "Fail to setup webview with intent: " +  exception.getMessage());
        }
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
}
