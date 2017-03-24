package br.com.friendlydonations.shared.views.alert;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding.view.RxView;

import br.com.friendlydonations.R;
import butterknife.ButterKnife;

/**
 * Created by brunogabriel on 23/03/17.
 */

public class AlertPermissionsSettingsDialog extends DialogFragment {
    public AlertPermissionsSettingsDialog() {
        // Empty Constructor required
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.alert_permissions_setting, null);
        onApplyClickActions(view);
        builder.setView(view);
        return builder.create();
    }

    private void onApplyClickActions(View view) {
        RxView.clicks(view.findViewById(R.id.text_cancel)).subscribe(aVoid -> dismiss());
        RxView.clicks(view.findViewById(R.id.text_settings)).subscribe(aVoid -> {
            dismiss();
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.fromParts("package", getActivity().getPackageName(), null));
            startActivity(intent);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
