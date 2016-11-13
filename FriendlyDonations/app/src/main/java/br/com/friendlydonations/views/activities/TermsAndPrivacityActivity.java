package br.com.friendlydonations.views.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import br.com.friendlydonations.R;
import br.com.friendlydonations.managers.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by brunogabriel on 13/11/16.
 */

public class TermsAndPrivacityActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_privacity);
        ButterKnife.bind(this);
        initUI();
    }

    @Override
    public void initUI() {
        setupToolbar(toolbar, getString(R.string.terms_priv_title), null, true, true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
