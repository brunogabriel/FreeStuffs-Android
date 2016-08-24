package br.com.rededoar.views.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import br.com.rededoar.R;
import br.com.rededoar.managers.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by brunogabriel on 8/23/16.
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.toolbar) protected Toolbar mToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initUI();
    }

    @Override
    public void initUI() {
        setupToolbar(mToolbar,  "Teste", null, false, false);
    }
}
