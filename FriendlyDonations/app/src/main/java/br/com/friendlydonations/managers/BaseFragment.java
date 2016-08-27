package br.com.friendlydonations.managers;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by brunogabriel on 8/27/16.
 */
public abstract class BaseFragment extends Fragment {

    public BaseFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public abstract void initUI();}
