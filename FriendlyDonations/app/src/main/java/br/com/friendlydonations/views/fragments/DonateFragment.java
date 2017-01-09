package br.com.friendlydonations.views.fragments;

/**
 * Created by brunogabriel on 9/19/16.
 */
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.friendlydonations.R;
import br.com.friendlydonations.managers.App;
import br.com.friendlydonations.managers.BaseActivity;
import br.com.friendlydonations.managers.BaseFragment;
import br.com.friendlydonations.models.PictureUpload;
import br.com.friendlydonations.network.NetworkInterface;
import br.com.friendlydonations.utils.ConstantsTypes;
import br.com.friendlydonations.utils.ApplicationUtilities;
import br.com.friendlydonations.views.activities.MainActivity;
import br.com.friendlydonations.views.adapters.ConcreteCategoryAdapter;
import br.com.friendlydonations.views.adapters.PictureUploadAdapter;
import br.com.friendlydonations.views.widgets.SelectEditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import mehdi.sakout.dynamicbox.DynamicBox;
import retrofit2.Retrofit;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by brunogabriel on 8/27/16.
 */
public class DonateFragment extends BaseFragment implements View.OnFocusChangeListener {

    private static final String DYNAMICBOX_LOADING = "dynamicboxloading";
    private static final String DYNAMICBOX_LOADAGAIN = "dynamicboxloadingagain";

    @BindView(R.id.tvUploadPhotos)
    TextView tvUploadPhotos;

    @BindView(R.id.recyclerViewPictures)
    RecyclerView recyclerViewPictures;

    @BindView(R.id.tvSelectCategory)
    TextView tvSelectCategory;

    @BindView(R.id.recyclerViewCategories)
    RecyclerView recyclerViewCategories;

    @BindView(R.id.etProductItemTitle)
    AppCompatEditText etProductItemTitle;

    @BindView(R.id.etDescription)
    AppCompatEditText etDescription;

    @BindView(R.id.locationSelectET)
    SelectEditText locationSelectEditText;

    @BindView(R.id.deliverySelectET)
    SelectEditText deliverySelectET;

    PictureUploadAdapter pictureAdapter;

    protected View rootView;

    @Inject
    protected Retrofit retrofit;

    private boolean isCategoryLoaded;

    ConcreteCategoryAdapter concreteCategoryAdapter;

    // Views
    protected View dynamicBoxLoading;
    protected View dynamicBoxNoInternet;
    protected DynamicBox dynamicBox;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_donate, container, false);
        ButterKnife.bind(this, rootView);
        ((App) getActivity().getApplication()).getmNetcomponent().inject(this);
        initUI();
        initCategoriesAdapter(inflater, container);
        return rootView;
    }

    @Override
    public void initUI() {
        locationSelectEditText.setOnClickListener(v -> {
            try {
                Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                        .build(getActivity());
                startActivityForResult(intent, ConstantsTypes.ACTIVITY_LOCATION_ADDRESS);
            } catch (Exception exception) {
                Log.e(TAG, "Fail on location selection: " + exception.getMessage());
            }
        });

        deliverySelectET.setOnClickListener( v -> {
           PopupMenu popupMenu = new PopupMenu(getActivity(), deliverySelectET);
            popupMenu.getMenuInflater().inflate(R.menu.menu_delivery, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                deliverySelectET.setModel(item, false);
                deliverySelectET.setText(item.getTitle());
                return true;
            });

            popupMenu.show();
        });

        initImagesAdapter();
    }

    private void initImagesAdapter() {
        recyclerViewPictures.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        pictureAdapter = new PictureUploadAdapter((BaseActivity) getActivity(), this, recyclerViewPictures.getLayoutManager());
        recyclerViewPictures.setAdapter(pictureAdapter);

        pictureAdapter.add(new PictureUpload(0, null));
        pictureAdapter.add(new PictureUpload(1, null));
        pictureAdapter.add(new PictureUpload(2, null));
        pictureAdapter.add(new PictureUpload(3, null));
        pictureAdapter.add(new PictureUpload(4, null));
    }

    private void initCategoriesAdapter(LayoutInflater inflater, ViewGroup container) {
        dynamicBox = new DynamicBox(getActivity(), recyclerViewCategories);
        dynamicBoxLoading = inflater.inflate(R.layout.holder_list_loader, container, false);
        dynamicBoxNoInternet = inflater.inflate(R.layout.holder_category_error_on_donate, container, false);
        try {

            ProgressBar progressBar = (ProgressBar) dynamicBoxLoading.findViewById(R.id.progressBar);
            progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#6D65E6"),
                    android.graphics.PorterDuff.Mode.MULTIPLY);
        } catch (Exception exception) {
            Log.e(TAG, "Fail to apply loading color on progressbar: " + exception.getMessage());
        }

        // adding views to dynamic box
        dynamicBox.addCustomView(dynamicBoxLoading, DYNAMICBOX_LOADING);
        dynamicBox.addCustomView(dynamicBoxNoInternet, DYNAMICBOX_LOADAGAIN);

        // Setup adapter
        concreteCategoryAdapter = new ConcreteCategoryAdapter(getActivity(), (position, categoryModel) -> {
            // TODO: Apply actions
        });

        recyclerViewCategories.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCategories.setAdapter(concreteCategoryAdapter);

        // Starting loading
        if (!isNetworkEnabled()) {
            showLoadAgain();
        } else if (!isCategoryLoaded) {
            loadCategories();
        }

        dynamicBoxNoInternet.findViewById(R.id.btnCategoryLoadAgain).setOnClickListener(v -> loadCategories());
    }

    private Action1<Throwable> throwableErrorCategories = throwable -> {
        showLoadAgain();
    };

    private void showLoadAgain() {
        dynamicBox.hideAll();
        dynamicBox.showCustomView(DYNAMICBOX_LOADAGAIN);
        Toast.makeText(getActivity(), R.string.donate_fail_loading_categories, Toast.LENGTH_SHORT).show();
    }

    private void loadCategories() {
        dynamicBox.hideAll();
        if (!isCategoryLoaded) {
            dynamicBox.showCustomView(DYNAMICBOX_LOADING);
            retrofit.create(NetworkInterface.class)
                    .loadCategories(((MainActivity) getActivity()).getToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(result -> {
                        dynamicBox.hideAll();
                        if (result.isStatus() && result.getCategoryModelList().size() > 0) {
                            List<Object> mItens = new ArrayList<>();
                            mItens.addAll(result.getCategoryModelList());
                            concreteCategoryAdapter.addAll(mItens);
                            this.isCategoryLoaded = true;
                        } else {
                            showLoadAgain();
                        }
                    }, throwableErrorCategories);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
    }

    // Needs refactor
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        final Intent dataFinal = data;

        if (resultCode == Activity.RESULT_OK && requestCode == ConstantsTypes.ACTIVITY_RESULT_CAMERA) {
            Dexter.checkPermissions(new MultiplePermissionsListener() {
                @Override
                public void onPermissionsChecked(MultiplePermissionsReport report) {
                    if (report.isAnyPermissionPermanentlyDenied()) {
                        ApplicationUtilities.showPermissionRequest(getActivity());
                    } else {
                        File mPictureFile = ApplicationUtilities.getCameraOutputMediaFile(getActivity());
                        File mPictureFileCropped = ApplicationUtilities.getOutputMediaFile(getActivity(), true);
                        callCrop(Uri.fromFile(mPictureFile), Uri.fromFile(mPictureFileCropped));
                    }
                }
                @Override
                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                    token.continuePermissionRequest();
                }
            }, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
        } else if(resultCode == Activity.RESULT_OK && requestCode == ConstantsTypes.ACTIVITY_RESULT_SELECT_PICTURE_GALLERY) {
            Dexter.checkPermissions(new MultiplePermissionsListener() {
                @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
                    if (report.isAnyPermissionPermanentlyDenied()) {
                        ApplicationUtilities.showPermissionRequest(getActivity());
                    } else {
                        File mPictureFileCropped = ApplicationUtilities.getOutputMediaFile(getActivity(), true);
                        callCrop(dataFinal.getData(), Uri.fromFile(mPictureFileCropped));
                    }
                }
                @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                    token.continuePermissionRequest();
                }
            }, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
        } else if (resultCode == Activity.RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            Uri resultUri = UCrop.getOutput(data);
            pictureAdapter.updateImage(resultUri);
        } else if (requestCode == ConstantsTypes.ACTIVITY_LOCATION_ADDRESS) {
            onPlaceApiAnswer(resultCode, data);
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
            String mCause = cropError.getMessage();
            // TODO: Fail on crop result
        }
    }

    private void onPlaceApiAnswer(int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Place place = PlaceAutocomplete.getPlace(getContext(), data);
            locationSelectEditText.setModel(place, false);
            locationSelectEditText.setText(place.getAddress());
        } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
            // Status status = PlaceAutocomplete.getStatus(getContext(), data);
            // TODO:
        } else if (resultCode == Activity.RESULT_CANCELED) {
            // TODO:
        }
    }

    private void callCrop(Uri sourceUri, Uri destinyUri) {
        UCrop.Options options = new UCrop.Options();
        options.setToolbarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        options.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        UCrop.of(sourceUri, destinyUri)
                .withAspectRatio(4, 3)
                .withOptions(options)
                .start(getActivity(), DonateFragment.this);
    }
}
