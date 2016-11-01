package br.com.friendlydonations.managers;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brunogabriel on 31/10/16.
 */

public abstract class BaseRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<Object> items = new ArrayList<>();
    protected Activity activity;

    public BaseRecyclerViewAdapter(Activity activity) {
        this.activity = activity;
    }

    public void addAll(List<Object> mElements) {
        if (mElements != null && mElements.size() > 0) {
            int beforeSize = items.size();
            int mElementsSize = mElements.size();
            items.addAll(mElements);
            notifyItemRangeInserted(beforeSize, mElementsSize);
        }
    }

    public void add(Object newItem) {
        if (newItem != null) {
            int beforeSize = items.size();
            items.add(newItem);
            notifyItemInserted(beforeSize);
        }
    }

}
