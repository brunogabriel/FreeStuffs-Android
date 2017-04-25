package br.com.friendlydonations.application.main;

import android.os.Bundle;
import android.support.annotation.Nullable;

import br.com.friendlydonations.R;
import br.com.friendlydonations.shared.BaseActivity;

/**
 * Created by brunogabriel on 24/04/17.
 */

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
