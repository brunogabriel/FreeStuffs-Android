package br.com.friendlydonations.views.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import br.com.friendlydonations.R;
import br.com.friendlydonations.managers.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by brunogabriel on 13/11/16.
 */

public class AccountSettingsActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);
        ButterKnife.bind(this);
        initUI();
    }

    @Override
    public void initUI() {
        setupToolbar(toolbar, "", null, true, true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_close_x);
    }
}
