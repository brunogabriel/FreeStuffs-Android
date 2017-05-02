package br.com.friendlydonations.shared;

import android.support.v4.app.Fragment;
import android.util.Log;

import butterknife.Unbinder;

/**
 * Created by brunogabriel on 24/04/17.
 */

public class BaseFragment extends Fragment {
    protected String TAG = BaseActivity.class.getSimpleName();    protected Unbinder unbinder;
    protected String fragmentTitle;

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Try to unbind Butterknife
        if (unbinder != null) {
            try {
                unbinder.unbind();
            } catch (Exception exception) {
                Log.e(TAG, "Fail to unbind Butterknife");
            }
        }
    }

    public void setFragmentTitle(String fragmentTitle) {
        this.fragmentTitle = fragmentTitle;
    }

    public String getFragmentTitle() {
        return fragmentTitle;
    }
}
