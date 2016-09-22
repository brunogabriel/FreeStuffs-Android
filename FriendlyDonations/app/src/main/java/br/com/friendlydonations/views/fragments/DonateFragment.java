package br.com.friendlydonations.views.fragments;

/**
 * Created by brunogabriel on 9/19/16.
 */
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import br.com.friendlydonations.R;
import br.com.friendlydonations.managers.BaseActivity;
import br.com.friendlydonations.managers.BaseFragment;
import br.com.friendlydonations.utils.TypefaceMaker;
import br.com.friendlydonations.views.adapters.CategoryAdapter;
import br.com.friendlydonations.views.adapters.PictureUploadAdapter;
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

    @BindView(R.id.tvUploadPhotos) TextView tvUploadPhotos;
    @BindView(R.id.recyclerViewPictures) RecyclerView recyclerViewPictures;
    PictureUploadAdapter pictureAdapter;

    @BindView(R.id.tvSelectCategory) TextView tvSelectCategory;
    @BindView(R.id.recyclerViewCategories) RecyclerView recyclerViewCategories;
    CategoryAdapter categoryAdapter;

    @BindView(R.id.etProductItemTitle) AppCompatEditText etProductItemTitle;
    @BindView(R.id.etDescription) AppCompatEditText etDescription;





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
        tvUploadPhotos.setTypeface(mMonserratRegular);
        tvSelectCategory.setTypeface(mMonserratRegular);

        recyclerViewPictures.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        pictureAdapter = new PictureUploadAdapter((BaseActivity) getActivity());
        recyclerViewPictures.setAdapter(pictureAdapter);

        recyclerViewCategories.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        categoryAdapter = new CategoryAdapter((BaseActivity) getActivity());
        recyclerViewCategories.setAdapter(categoryAdapter);

        // TODO: Remove, only to test
        // Adding pictures
        pictureAdapter.add(new String()); pictureAdapter.add(new String());
        pictureAdapter.add(new String()); pictureAdapter.add(new String());
        pictureAdapter.add(new String());

        // Adding categories
        categoryAdapter.add(new String()); categoryAdapter.add(new String());
        categoryAdapter.add(new String()); categoryAdapter.add(new String());
        categoryAdapter.add(new String()); categoryAdapter.add(new String());

        etProductItemTitle.setOnFocusChangeListener(this);
        etDescription.setOnFocusChangeListener(this);
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
