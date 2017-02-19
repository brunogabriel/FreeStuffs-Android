package br.com.friendlydonations.shared.views;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.friendlydonations.R;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.functions.Action0;

/**
 * Created by brunogabriel on 18/02/17.
 */

public class PictureUpdaterDialogFragment extends DialogFragment {

    private final Action0 actionCamera;
    private final Action0 actionGallery;
    private Unbinder unbider;

    @OnClick(R.id.camera_layout)
    protected void onClickCameraLayout() {
        dismiss();
        actionCamera.call();
    }

    @OnClick(R.id.gallery_layout)
    protected void onClickGalleryLayout() {
        dismiss();
        actionGallery.call();
    }

    public PictureUpdaterDialogFragment(Action0 actionCamera, Action0 actionGallery) {
        super();
        this.actionCamera = actionCamera;
        this.actionGallery = actionGallery;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.alert_choose_updater_image, null);
        unbider = ButterKnife.bind(this, view);
        builder.setView(view);
        return builder.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbider.unbind();
    }
}
