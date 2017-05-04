package br.com.friendlydonations.shared.ui;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

import br.com.friendlydonations.R;
import mehdi.sakout.dynamicbox.DynamicBox;

/**
 * Created by brunogabriel on 03/05/17.
 */

public class DynamicBoxHelper {

    public enum DynamicBoxView {
        LOADING_HORIZONTAL("loading.horizontal", R.layout.holder_loading_horizontal);

        private String name;
        private int layoutId;

        DynamicBoxView(@NonNull String name, int layoutId) {
            this.name = name;
            this.layoutId = layoutId;
        }

        public int getLayoutId() {
            return layoutId;
        }

        public String getName() {
            return name;
        }
    }

    private final Activity activity;
    private final ViewGroup viewGroup;
    private DynamicBox dynamicBox;
    private HashMap<String, View> cachedViews;

    public DynamicBoxHelper(@NonNull Activity activity, @NonNull ViewGroup viewGroup) {
        this.activity = activity;
        this.viewGroup = viewGroup;
        dynamicBox = new DynamicBox(viewGroup.getContext(), viewGroup);
        cachedViews = new HashMap<>();
    }

    public void hideAll() {
        dynamicBox.hideAll();
    }

    private View createCustomView (@NonNull DynamicBoxView customView) {
        View view = activity.getLayoutInflater().inflate(customView.getLayoutId(), viewGroup, false);
        dynamicBox.addCustomView(view, customView.getName());
        return view;
    }

    private void showView(@NonNull String tag) {
        hideAll(); // hide previously views
        dynamicBox.showCustomView(tag);
    }

    public View showCachedView(@NonNull DynamicBoxView customView) {
        View view;

        if (cachedViews.containsKey(customView.getName())) {
            view = cachedViews.get(customView.getName());
        } else {
            view = createCustomView(customView);
            cachedViews.put(customView.getName(), view);
        }

        showView(customView.getName());

        return view;
    }
}
