package br.com.friendlydonations.application.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.friendlydonations.R;
import br.com.friendlydonations.shared.BaseFragment;
import butterknife.ButterKnife;

/**
 * Created by brunogabriel on 20/02/17.
 */

public class ProfileFragment extends BaseFragment implements ProfileView {

    private View rootView;

    public ProfileFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initialize();
        return rootView;
    }

    private void initialize() {
    }
}
