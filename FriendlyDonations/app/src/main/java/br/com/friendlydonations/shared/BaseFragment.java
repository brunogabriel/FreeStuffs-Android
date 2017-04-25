package br.com.friendlydonations.shared;

import android.support.v4.app.Fragment;

import butterknife.Unbinder;

/**
 * Created by brunogabriel on 24/04/17.
 */

public class BaseFragment extends Fragment {
    protected Unbinder unbinder;

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Try to unbind Butterknife
        if (unbinder != null) {
            try {
                unbinder.unbind();
            } catch (Exception exception) {}
        }
    }
}
