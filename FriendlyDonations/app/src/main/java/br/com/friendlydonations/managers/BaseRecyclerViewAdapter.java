package br.com.friendlydonations.managers;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.friendlydonations.models.LoaderModel;
import br.com.friendlydonations.models.category.CategoryModel;

/**
 * Created by brunogabriel on 31/10/16.
 */

public abstract class BaseRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_LOADER = 0; // default loader item

    protected List<Object> items = new ArrayList<>();
    protected Activity activity;

    public BaseRecyclerViewAdapter(Activity activity) {
        this.activity = activity;
    }

    public boolean isLoaderLastElement() {
        return items != null && items.size() > 0 ? items.get(items.size() - 1) instanceof LoaderModel: false;
    }

    public void addLoader() {
        if (!isLoaderLastElement()) {
            items.add(new LoaderModel());
            notifyItemInserted(items.size() - 1);
        }
    }

    public void removeLoader() {
        if (isLoaderLastElement()) {
            int mItemsSize = items.size();
            items.remove(items.size()-1);
            notifyItemRemoved(mItemsSize);
        }
    }

    public void addAll(List<Object> mElements) {
        if (mElements != null && mElements.size() > 0) {
            removeLoader(); // first remove loader
            int beforeSize = items.size();
            int mElementsSize = mElements.size();
            items.addAll(mElements);
            notifyItemRangeInserted(beforeSize, mElementsSize);
        }
    }

    public void addAllWithLoading(List<Object> mElements) {

        if (mElements == null) {
            mElements = new ArrayList<>();
        }

        mElements.add(new LoaderModel());
        addAll(mElements);
    }

    public void add(Object newItem) {
        if (newItem != null) {
            int beforeSize = items.size();
            items.add(newItem);
            notifyItemInserted(beforeSize);
        }
    }


}
