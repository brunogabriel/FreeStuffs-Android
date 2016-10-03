package br.com.friendlydonations.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.friendlydonations.R;
import br.com.friendlydonations.managers.BaseActivity;
import br.com.friendlydonations.utils.TypefaceMaker;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by brunogabriel on 21/09/16.
 */

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> items = new ArrayList<>();
    BaseActivity activity;

    public CategoryAdapter (BaseActivity activity) {
        this.activity = activity;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mInflateredView = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_card_category, parent, false);
        RecyclerView.ViewHolder viewHolder = new CategoryViewHolder(mInflateredView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CategoryViewHolder categoryViewHolder = (CategoryViewHolder) holder;
        categoryViewHolder.populateUI();
    }

    @Override
    public int getItemCount() {
        return items == null ? 0: items.size();
    }

    public void addAll(List<Object> newItems) {
        if (newItems != null) {
            int beforeSize = items.size();
            int newItemsSize = newItems.size();
            items.addAll(newItems);
            notifyItemRangeInserted(beforeSize, newItemsSize);
        }
    }

    public void add(Object newItem) {
        if (newItem != null) {
            int beforeSize = items.size();
            items.add(newItem);
            notifyItemInserted(beforeSize);
        }
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.circleImageView) CircleImageView circleImageView;
        @BindView(R.id.tvCategoryName) TextView tvCategoryName;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void populateUI() {
            tvCategoryName.setTypeface(TypefaceMaker.createTypeFace(activity, TypefaceMaker.FontFamily.RobotoMedium));
        }
    }
}
