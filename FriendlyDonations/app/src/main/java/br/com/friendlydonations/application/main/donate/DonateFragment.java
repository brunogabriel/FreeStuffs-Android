package br.com.friendlydonations.application.main.donate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.friendlydonations.R;
import br.com.friendlydonations.shared.BaseFragment;

/**
 * Created by brunogabriel on 24/04/17.
 */

public class DonateFragment extends BaseFragment implements DonateView {

    public DonateFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_donate, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: bind view
    }
}
