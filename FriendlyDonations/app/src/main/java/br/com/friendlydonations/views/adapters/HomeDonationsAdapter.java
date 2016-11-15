package br.com.friendlydonations.views.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.List;

import br.com.friendlydonations.R;
import br.com.friendlydonations.managers.BaseRecyclerViewAdapter;
import br.com.friendlydonations.models.category.CategoryModel;
import br.com.friendlydonations.models.DonationModel;
import br.com.friendlydonations.models.LoaderModel;
import br.com.friendlydonations.utils.ApplicationUtilities;
import br.com.friendlydonations.utils.ImageUtility;
import br.com.friendlydonations.views.activities.DonationDetailActivity;
import br.com.friendlydonations.views.widgets.LoaderViewHolder;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by brunogabriel on 9/11/16.
 */
public class HomeDonationsAdapter extends BaseRecyclerViewAdapter {

    public static final int VIEW_TYPE_DONATION_CATEGORIES = 1000;
    public static final int VIEW_TYPE_DONATIONS = 1001;

    public HomeDonationsAdapter(Activity activity) {
        super(activity);
    }

    public HomeDonationsAdapter(Activity activity, Object headerObject) {
        super(activity);
        add(headerObject);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder mViewHolder;

        switch (viewType) {
            case VIEW_TYPE_LOADER:
                mViewHolder = new LoaderViewHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.holder_list_loader, parent, false));
                break;
            case VIEW_TYPE_DONATION_CATEGORIES:
                mViewHolder = new HorizontalCategoriesViewHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.holder_category_in_home, parent, false));
                break;

            default:
            case VIEW_TYPE_DONATIONS:
                mViewHolder = new DonationHomeViewHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.holder_card_donation, parent, false));
                break;
        }


        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LoaderViewHolder) {
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            layoutParams.setFullSpan(true);
            return;
        } else if (holder instanceof DonationHomeViewHolder) {
            ((DonationHomeViewHolder) holder).populateUI();
        } else if (holder instanceof HorizontalCategoriesViewHolder) {
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            layoutParams.setFullSpan(true);
            HomeCategoryCheckAdapter homeCategoryCardAdapter = new HomeCategoryCheckAdapter(activity);
            HorizontalCategoriesViewHolder horizontalRecyclerViewHolder = (HorizontalCategoriesViewHolder) holder;
            horizontalRecyclerViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
            horizontalRecyclerViewHolder.recyclerView.setAdapter(homeCategoryCardAdapter);
            homeCategoryCardAdapter.addAll((List<Object>) items.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object mCurrentItem = items.get(position);
        if (mCurrentItem instanceof LoaderModel) {
            return VIEW_TYPE_LOADER;
        } else if (mCurrentItem instanceof DonationModel) {
            return VIEW_TYPE_DONATIONS;
        } else {
            return VIEW_TYPE_DONATION_CATEGORIES;
        }
    }

    @Override
    public int getItemCount() {
        return items == null ? 0: items.size();
    }

    // Holders
    public class DonationHomeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cardTitle)
        AppCompatTextView cardTitle;

        public DonationHomeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(view -> {
                Intent mIntent = new Intent(activity, DonationDetailActivity.class);
                activity.startActivity(mIntent);
            });
        }

        public void populateUI() {
            int randomItem = (int) (Math.random() * 100);

            if (randomItem < 30) {
                cardTitle.setText("Lorem ipsum dolor");
            } else if (randomItem < 60) {
                cardTitle.setText("Brand new ladies bike 29 wheels...Brand new ladies " +
                        "bike 29 wheels Brand new ladies bike 29 wheels");
            } else {
                cardTitle.setText("Brand new ladies bike 29 wheels Brand new ladies bike 29 " +
                        "wheels...Brand new ladies bike 29 wheels Brand new ladies bike 29 wheels");
            }
        }
    }

    public class HorizontalCategoriesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.horizontalRecyclerview)
        RecyclerView recyclerView;

        public HorizontalCategoriesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public class HomeCategoryCheckAdapter extends BaseRecyclerViewAdapter {

        public HomeCategoryCheckAdapter(Activity activity) {
            super(activity);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            CategoryCheckViewHolder checkHolder = new CategoryCheckViewHolder(LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.holder_card_category, parent, false));


            return checkHolder;
        }

        private void performCheckBehaviour(int position) {
            int i = 0;
            for (Object mNextItem: items) {
                ((CategoryModel) mNextItem).setChecked(i == position ? true: false);
                i++;
            }

            notifyDataSetChanged();
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            CategoryCheckViewHolder checkHolder = (CategoryCheckViewHolder) holder;
            CategoryModel categoryModel = (CategoryModel) this.items.get(position);

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
            ImageUtility.loadImageWithPlaceholder(checkHolder.circleImageView, activity,
                    categoryModel.getImageModel().getLoader(),
                    categoryModel.getImageModel().getLarge());
        }

        @Override
        public int getItemCount() {
            return this.items == null ? 0: this.items.size();
        }

        public class CategoryCheckViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.circleImageView) CircleImageView circleImageView;
            @BindView(R.id.tvCategoryName) TextView tvCategoryName;
            @BindView(R.id.checkMarker) RelativeLayout checkMarker;
            View itemView;

            public CategoryCheckViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
                this.itemView = itemView;
                this.itemView.setOnClickListener(v -> performCheckBehaviour(getAdapterPosition()));
            }
        }
    }

}
