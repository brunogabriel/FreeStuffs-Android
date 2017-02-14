package br.com.friendlydonations.application.main.donate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.friendlydonations.R;
import br.com.friendlydonations.shared.BaseFragment;
import butterknife.ButterKnife;

/**
 * Created by brunogabriel on 12/02/17.
 */

public class ProfileFragment extends BaseFragment {

    public ProfileFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, layoutView);
        return layoutView;
    }
}
