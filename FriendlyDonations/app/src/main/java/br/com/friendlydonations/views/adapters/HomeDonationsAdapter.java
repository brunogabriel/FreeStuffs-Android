package br.com.friendlydonations.views.adapters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import br.com.friendlydonations.R;
import br.com.friendlydonations.managers.BaseRecyclerViewAdapter;
import br.com.friendlydonations.models.ImageModel;
import br.com.friendlydonations.models.donation.DonationModel;
import br.com.friendlydonations.models.LoaderModel;
import br.com.friendlydonations.utils.ImageUtility;
import br.com.friendlydonations.views.actions.CategoryAction;
import br.com.friendlydonations.views.activities.DonationDetailActivity;
import br.com.friendlydonations.views.widgets.LoaderViewHolder;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by brunogabriel on 9/11/16.
 */
public class HomeDonationsAdapter extends BaseRecyclerViewAdapter {

    public static final int VIEW_TYPE_DONATION_CATEGORIES = 1000;
    public static final int VIEW_TYPE_DONATIONS = 1001;

    private CategoryAction categoryAction;

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

    public void removeAllExceptCategories(boolean isAddLoader) {
        if (items != null && items.size() > 0 ) {
            Object firstItem = items.get(0);

            if (firstItem instanceof LoaderModel || firstItem instanceof DonationModel) {
                return;
            } else {
                items.clear();
                items.add(firstItem);
                items.add(new LoaderModel());
                notifyDataSetChanged();
            }
        }
    }

    public void setCategoryAction(CategoryAction categoryAction) {
        this.categoryAction = categoryAction;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LoaderViewHolder) {
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams)
                    holder.itemView.getLayoutParams();
            layoutParams.setFullSpan(true);
            return;
        } else if (holder instanceof DonationHomeViewHolder) {
            ((DonationHomeViewHolder) holder).populateUI(position);
        } else if (holder instanceof HorizontalCategoriesViewHolder) {
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            layoutParams.setFullSpan(true);
            ConcreteCategoryAdapter homeCategoryCardAdapter = new ConcreteCategoryAdapter(activity, this.categoryAction);
            HorizontalCategoriesViewHolder horizontalRecyclerViewHolder = (HorizontalCategoriesViewHolder) holder;
            horizontalRecyclerViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
            horizontalRecyclerViewHolder.recyclerView.setAdapter(homeCategoryCardAdapter);
            homeCategoryCardAdapter.addAll((List<Object>) items.get(position));
            homeCategoryCardAdapter.setClickedItem(0);
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

        @BindView(R.id.cardLocation)
        AppCompatTextView cardLocation;

        @BindView(R.id.cardImage)
        ImageView cardImage;

        DonationModel donationModel;


        public DonationHomeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(view -> {
                Bundle mBundle = new Bundle();
                mBundle.putSerializable(DonationDetailActivity.DONATION_DETAIL_SERIALIZATION, donationModel);
                Intent mIntent = new Intent(activity, DonationDetailActivity.class);
                mIntent.putExtras(mBundle);
                activity.startActivity(mIntent);
            });
        }

        public void populateUI(int position) {
            donationModel = (DonationModel) items.get(position);
            ImageModel imageModel = donationModel.getImages().get(0);

            try {
                ImageUtility.loadImageWithPlaceholder(cardImage, activity,
                        imageModel.getLoader(), imageModel.getLarge());
            } catch (Exception exception) {

            }

            cardTitle.setText(donationModel.getTitle());
            cardLocation.setText(donationModel.getLocationModel().getContext());
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

}
