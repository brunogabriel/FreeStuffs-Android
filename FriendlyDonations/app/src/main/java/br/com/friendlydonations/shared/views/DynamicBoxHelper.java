package br.com.friendlydonations.shared.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

import br.com.friendlydonations.R;
import mehdi.sakout.dynamicbox.DynamicBox;

/**
 * Created by brunogabriel on 20/02/17.
 */

public class DynamicBoxHelper {

    public static final String CUSTOM_LOADER = "customloader";
    Map<String, View> map;
    private final Context context;
    private final View view;
    private DynamicBox dynamicBox;

    public DynamicBoxHelper(Context context, View view) {
        dynamicBox = new DynamicBox(context, view);
        this.context = context;
        this.view = view;
        this.map = new HashMap<>();
        initialize();
    }

    public void hideAll() {
        dynamicBox.hideAll();
    }
    
    public void initialize() {
        createCustomLoader();
    }

    public void showLoader() {
        dynamicBox.showCustomView(CUSTOM_LOADER);
    }

    private void createCustomLoader() {
        View customView = LayoutInflater.from(context).inflate(R.layout.box_custom_loader, null, false);
        dynamicBox.addCustomView(customView, CUSTOM_LOADER);
        map.put(CUSTOM_LOADER, customView);
    }
}
