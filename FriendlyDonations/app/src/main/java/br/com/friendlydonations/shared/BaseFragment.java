package br.com.friendlydonations.shared;

import android.support.v4.app.Fragment;

import br.com.friendlydonations.dagger.component.NetworkComponent;
import butterknife.Unbinder;

/**
 * Created by brunogabriel on 10/02/17.
 */

public class BaseFragment extends Fragment {
    protected Unbinder unbinder;

    protected NetworkComponent getNetworkComponent() {
        return ((CustomApplication) getActivity().getApplication()).getNetworkComponent();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
