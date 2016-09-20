package br.com.friendlydonations.managers;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.TextView;

/**
 * Created by brunogabriel on 8/27/16.
 */
public abstract class BaseFragment extends Fragment {

    public BaseFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public abstract void initUI();

    protected void setTypeface (Typeface mTypeface, TextView mTextView) {
        if (mTypeface != null && mTextView != null) {
            mTextView.setTypeface(mTypeface);
        }
    }

}
