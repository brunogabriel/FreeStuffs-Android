package br.com.friendlydonations.application.donate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.friendlydonations.R;
import br.com.friendlydonations.shared.BaseFragment;
import butterknife.ButterKnife;

/**
 * Created by brunogabriel on 16/02/17.
 */

public class DonateFragment extends BaseFragment implements DonateView {

    protected View rootView;


    public DonateFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_donate, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }


}
