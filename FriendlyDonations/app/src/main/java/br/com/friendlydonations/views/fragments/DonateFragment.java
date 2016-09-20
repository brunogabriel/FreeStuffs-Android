package br.com.friendlydonations.views.fragments;

/**
 * Created by brunogabriel on 9/19/16.
 */
import android.graphics.Typeface;
import android.os.Bundle;
import android.renderscript.Type;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import br.com.friendlydonations.R;
import br.com.friendlydonations.managers.BaseFragment;
import br.com.friendlydonations.utils.TypefaceMaker;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by brunogabriel on 8/27/16.
 */
public class DonateFragment extends BaseFragment implements View.OnFocusChangeListener{

    // Views
    protected View rootView;

    // Typefaces
    Typeface mRobotoLight;
    Typeface mMonserratRegular;

    @BindView(R.id.etProductItemTitle) AppCompatEditText etProductItemTitle;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // mainActivity = (MainActivity) getActivity();
        rootView = inflater.inflate(R.layout.fragment_donate, container, false);
        initTypefaces();
        ButterKnife.bind(this, rootView);
        //setHasOptionsMenu(true);
        initUI();
        return rootView;
    }

    @Override
    public void initUI() {
        etProductItemTitle.setOnFocusChangeListener(this);
    }

    public void initTypefaces() {
        mMonserratRegular = TypefaceMaker.createTypeFace(getActivity(), TypefaceMaker.FontFamily.MontserratRegular);
        mRobotoLight = TypefaceMaker.createTypeFace(getActivity(), TypefaceMaker.FontFamily.RobotoLight);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflater.inflate(R.menu.menu_homefragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            /* case R.id.action_exit:
                mainActivity.doExit();
                return true; */
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {

        if (view instanceof AppCompatEditText) {
            if (hasFocus) {
                setTypeface(mMonserratRegular, (TextView)view);
            } else {
                setTypeface(mRobotoLight, (TextView)view);
            }
        }
    }
}
