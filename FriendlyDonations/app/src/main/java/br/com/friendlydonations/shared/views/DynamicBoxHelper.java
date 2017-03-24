package br.com.friendlydonations.shared.views;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.jakewharton.rxbinding.view.RxView;

import java.util.HashMap;
import java.util.Map;

import br.com.friendlydonations.R;
import mehdi.sakout.dynamicbox.DynamicBox;
import rx.functions.Action0;
import rx.functions.Action1;

import static br.com.friendlydonations.shared.views.DynamicBoxHelper.DynamicTag.LOADER_BOX;
import static br.com.friendlydonations.shared.views.DynamicBoxHelper.DynamicTag.TAP_TO_LOAD_BOX;

/**
 * Created by brunogabriel on 20/02/17.
 */

public class DynamicBoxHelper {

    public enum DynamicTag {
        LOADER_BOX(R.layout.box_custom_loader),
        TAP_TO_LOAD_BOX(R.layout.box_tap_to_load);

        private int layoutResource;

        DynamicTag (int layoutResource) {
            this.layoutResource = layoutResource;
        }

        public int getLayoutResource() {
            return layoutResource;
        }
    }

    private DynamicBox dynamicBox;
    private Context context;
    private View view;
    private LayoutInflater layoutInflater;

    public DynamicBoxHelper(Context context, View view) {
        dynamicBox = new DynamicBox(context, view);
        this.context = context;
        this.view = view;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void hideAll() {
        dynamicBox.hideAll();
    }

    public void showView(DynamicTag dynamicTag) {
        dynamicBox.addCustomView(layoutInflater.inflate(dynamicTag.getLayoutResource(), view instanceof ViewGroup ? (ViewGroup) view: null, false), dynamicTag.name());
        dynamicBox.showCustomView(dynamicTag.name());
    }

    public void showViewTapToload(Action0 action) {
        View customView = layoutInflater.inflate(TAP_TO_LOAD_BOX.getLayoutResource(), view instanceof ViewGroup ? (ViewGroup) view: null, false);

        RxView.clicks(customView.findViewById(R.id.button_load)).subscribe(result -> action.call());
        dynamicBox.addCustomView(customView, TAP_TO_LOAD_BOX.name());
        dynamicBox.showCustomView(TAP_TO_LOAD_BOX.name());
    }

    public void showLoader() {
        View loaderView = layoutInflater.inflate(LOADER_BOX.getLayoutResource(), view instanceof ViewGroup ? (ViewGroup) view: null, false);
        ProgressBar progressBar = (ProgressBar) loaderView.findViewById(R.id.progress_bar);
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
        dynamicBox.addCustomView(loaderView, LOADER_BOX.name());
        dynamicBox.showCustomView(LOADER_BOX.name());
    }
}
