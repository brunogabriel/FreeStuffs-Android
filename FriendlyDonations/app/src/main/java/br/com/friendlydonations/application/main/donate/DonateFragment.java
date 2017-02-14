package br.com.friendlydonations.application.main.donate;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import br.com.friendlydonations.R;
import br.com.friendlydonations.models.PictureUpload;
import br.com.friendlydonations.network.NetworkCategory;
import br.com.friendlydonations.shared.ApplicationHelper;
import br.com.friendlydonations.shared.BaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;

/**
 * Created by brunogabriel on 12/02/17.
 */

public class DonateFragment extends BaseFragment implements DonateView {

    public static final int GALLERY_OPTION = 1;
    public static final int CAMERA_OPTION = 2;

    public static final int GALLERY_REQUEST_CODE = 100;
    public static final int CAMERA_REQUEST_CODE = 101;

    @Inject
    protected Retrofit retrofit;
    private DonatePresenter presenter;
    private PictureRecyclerViewAdapter pictureRecyclerViewAdapter;
    @BindView(R.id.upload_photos_text)
    protected TextView uploadPhotosText;
    @BindView(R.id.pictures_recycler_view)
    protected RecyclerView picturesRecyclerView;
    private LinearLayoutManager pictureLinearLayout;

    public DonateFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.fragment_donate, container, false);
        unbinder = ButterKnife.bind(this, layoutView);
        getNetworkComponent().inject(this);
        presenter = new DonatePresenter(this,
                retrofit.create(NetworkCategory.class));
        initComponents();
        presenter.init();
        return layoutView;
    }

    private void initComponents() {
        pictureRecyclerViewAdapter = new PictureRecyclerViewAdapter(presenter);
        pictureLinearLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        picturesRecyclerView.setLayoutManager(pictureLinearLayout);
        picturesRecyclerView.setAdapter(pictureRecyclerViewAdapter);
    }

    @Override
    public void addPictures(List<PictureUpload> pictureUploadList) {
        pictureRecyclerViewAdapter.addAll(pictureUploadList);
    }

    @Override
    public void changeImage(ImageView contentImage, PictureUpload upload) {
        Picasso.with(getActivity())
                .load(upload.getImage())
                .into(contentImage);
    }

    @Override
    public void choosePictureUploadMethod() {
        PopupMenu popupMenu = new PopupMenu(getActivity(), picturesRecyclerView, Gravity.CENTER);
        popupMenu.getMenu().add(0, GALLERY_OPTION, 0, getString(R.string.gallery));
        popupMenu.getMenu().add(0, CAMERA_OPTION,  1, getString(R.string.camera));
        popupMenu.setOnMenuItemClickListener(item -> {
            presenter.uploadByType(item.getItemId());
           return true;
        });
        popupMenu.show();
    }

    @Override
    public void uploadByGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    @Override
    public void uploadByCamera() {
        // TODO:
    }

    @Override
    public void scrollToPictureAtIndex(int index) {
        pictureLinearLayout.scrollToPosition(index);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GALLERY_REQUEST_CODE) {
                Dexter.withActivity(getActivity())
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(readExternalGalleryPermissionListener(data))
                        .check();
            } else if (requestCode == UCrop.REQUEST_CROP) {
                Uri resultUri = UCrop.getOutput(data);
                pictureRecyclerViewAdapter.updateImage(resultUri);
            }
        }
    }

    private PermissionListener readExternalGalleryPermissionListener(Intent intent) {
        return new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                File mPictureFileCropped = ApplicationHelper.getOutputMediaFile(getActivity(), true);
                callCrop(intent.getData(), Uri.fromFile(mPictureFileCropped));
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
            }
        };
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
