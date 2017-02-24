package br.com.friendlydonations.shared;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;

import br.com.friendlydonations.R;
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

    public void showSnackbarNetworkUnavailable(View view) {
        Snackbar.make(view, getString(R.string.network_not_detected), Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.settings),
                        settingsView -> startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0))
                .setActionTextColor(ContextCompat.getColor(getContext(), R.color.colorError))
                .show();
    }

    public void showSimpleSnackbar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
