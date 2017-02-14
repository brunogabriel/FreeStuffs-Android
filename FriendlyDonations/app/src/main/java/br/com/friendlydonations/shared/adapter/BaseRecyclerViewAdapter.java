package br.com.friendlydonations.shared.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.friendlydonations.models.LoaderModel;

/**
 * Created by brunogabriel on 13/02/17.
 */

public abstract class BaseRecyclerViewAdapter <T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_LOADER = 0;

    protected List<? super Object> items = new ArrayList<>();

    public boolean isLoaderLastElement() {
        return items != null && items.size() > 0 ? items.get(items.size() - 1) instanceof LoaderModel : false;
    }

    public void addLoader() {
        if (!isLoaderLastElement()) {
            items.add(new LoaderModel());
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

    public void addAll(List<? super Object> mElements) {
        if (mElements != null && mElements.size() > 0) {
            removeLoader(); // first remove loader
            int beforeSize = items.size();
            int mElementsSize = mElements.size();
            items.addAll(mElements);
            notifyItemRangeInserted(beforeSize, mElementsSize);
        }
    }

    public void addAllWithLoading(List<? super Object> mElements) {
        if (mElements == null) {
            mElements = new ArrayList<>();
        }
        mElements.add(new LoaderModel());
        addAll(mElements);
    }

    public void add(T newItem) {
        if (newItem != null) {
            int beforeSize = items.size();
            items.add(newItem);
            notifyItemInserted(beforeSize);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}