package br.com.friendlydonations.views.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import br.com.friendlydonations.R;
import br.com.friendlydonations.managers.BaseRecyclerViewAdapter;
import br.com.friendlydonations.models.category.CategoryModel;
import br.com.friendlydonations.utils.ImageUtility;
import br.com.friendlydonations.views.actions.CategoryAction;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by brunogabriel on 03/01/17.
 */

public class ConcreteCategoryAdapter extends BaseRecyclerViewAdapter {

    private static final String TAG = "HomeCategAdapter";
    private CategoryAction categoryAction;
    private int clickedItem = -1;

    public ConcreteCategoryAdapter(Activity activity, CategoryAction categoryAction) {
        super(activity);
        this.categoryAction = categoryAction;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CategoryCheckViewHolder checkHolder =
                new ConcreteCategoryAdapter.CategoryCheckViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.holder_card_category, parent, false));

        return checkHolder;
    }

    private void performCheckBehaviour(int position) {
        int i = 0;
        for (Object mNextItem: items) {
            CategoryModel categoryModel = (CategoryModel) mNextItem;
            boolean isCallAction = false;
            if (i == position && !categoryModel.isChecked()) { // need to call action
                isCallAction = true;
            }

            if (i == position) {
                clickedItem = i;
                categoryModel.setChecked(true);
            } else {
                categoryModel.setChecked(false);
            }

            i++;

            if (isCallAction && categoryAction != null) {
                categoryAction.onClickAtPosition(position, categoryModel);
            }
        }

        notifyDataSetChanged();
    }

    public void uncheckAll() {
        for (Object mNextItem: items) {
            CategoryModel categoryModel = (CategoryModel) mNextItem;
            categoryModel.setChecked(false);
            clickedItem = -1;
        }
    }

    public int getClickedItem() {
        return this.clickedItem;
    }

    public void setClickedItem(int position) {
        this.clickedItem = position;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CategoryCheckViewHolder checkHolder = (CategoryCheckViewHolder) holder;
        CategoryModel categoryModel = (CategoryModel) this.items.get(position);

        if (categoryModel == null) {
            return;
        }

        boolean isModelCheck = categoryModel.isChecked();
        if (isModelCheck) {
            checkHolder.checkMarker.setVisibility(View.VISIBLE);
            checkHolder.itemView.setAlpha(1.0f);
        } else {
            checkHolder.checkMarker.setVisibility(View.GONE);
            checkHolder.itemView.setAlpha(0.5f);
        }

        // Apply information
        checkHolder.tvCategoryName.setText("" + categoryModel.getName());

        try {
            ImageUtility.loadImageWithPlaceholder(checkHolder.circleImageView, activity,
                    categoryModel.getImageModel().getLoader(),
                    categoryModel.getImageModel().getLarge());
        } catch (Exception exception) {
            Log.e(TAG, "Fail to apply image on category: " + categoryModel != null ? categoryModel.getName(): "NULL");
        }
    }

    @Override
    public int getItemCount() {
        return this.items == null ? 0: this.items.size();
    }

    public class CategoryCheckViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.circleImageView)
        CircleImageView circleImageView;
        @BindView(R.id.tvCategoryName)
        TextView tvCategoryName;
        @BindView(R.id.checkMarker)
        RelativeLayout checkMarker;
        View itemView;

        public CategoryCheckViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
            this.itemView.setOnClickListener(v -> performCheckBehaviour(getAdapterPosition()));
        }
    }
}