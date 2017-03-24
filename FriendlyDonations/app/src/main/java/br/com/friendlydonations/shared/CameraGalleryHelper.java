package br.com.friendlydonations.shared;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.List;
import java.util.UUID;

import br.com.friendlydonations.R;
import rx.functions.Action0;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by brunogabriel on 18/03/17.
 */

public class CameraGalleryHelper {

    public static final int CAMERA_CODE = 1000;
    public static final int GALLERY_CODE = 1001;

    private BaseActivity activity;
    private PermissionsHelper permissionsHelper;
    private Action0 openCameraAction;
    private Action0 openGalleryAction;

    public CameraGalleryHelper(@NonNull BaseActivity activity,
                               @NonNull PermissionsHelper permissionsHelper,
                               @NonNull Action0 openCameraAction,
                               @NonNull Action0 openGalleryAction) {
        this.activity = activity;
        this.permissionsHelper = permissionsHelper;
        this.openCameraAction = openCameraAction;
        this.openGalleryAction = openGalleryAction;
    }

    public void showChooseCameraOrGallery() {
        View view = LayoutInflater.from(activity).inflate(R.layout.alert_choose_updater_image, null, false);
        AlertDialog.Builder customDialog = new AlertDialog.Builder(activity);
        customDialog.setView(view);
        AlertDialog dialog = customDialog.show();

        view.findViewById(R.id.camera_layout).setOnClickListener(v -> {
            dialog.dismiss();
            if (permissionsHelper.areAllPermissionsEnabled(new String[]{CAMERA, WRITE_EXTERNAL_STORAGE})) {
                openCameraAction.call();
            } else {
                Dexter.withActivity(activity)
                        .withPermissions(CAMERA, WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {
                                    openCameraAction.call();
                                } else if (report.isAnyPermissionPermanentlyDenied()) {
                                    activity.showPermissionsSettings();
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        view.findViewById(R.id.gallery_layout).setOnClickListener(v -> {
            dialog.dismiss();
            if (permissionsHelper.areAllPermissionsEnabled(new String[]{WRITE_EXTERNAL_STORAGE})) {
                openGalleryAction.call();
            } else {
                Dexter.withActivity(activity)
                        .withPermission(WRITE_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                openGalleryAction.call();
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });
    }

    public File createMediaFile() {
        return new File(activity.getExternalFilesDir("AppImages").getPath(),
                UUID.randomUUID().toString());
    }

    public Intent createCameraIntent() {
        return new Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                .putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(createMediaFile()));
    }

    public Intent createGalleryIntent() {
        return new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).setType("image/*");
    }
}
