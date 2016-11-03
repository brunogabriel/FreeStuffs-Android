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
import br.com.friendlydonations.models.CategoryModel;
import br.com.friendlydonations.models.DonationModel;
import br.com.friendlydonations.models.LoaderModel;
import br.com.friendlydonations.utils.ConstantsTypes;
import br.com.friendlydonations.views.activities.DonationDetailActivity;
import br.com.friendlydonations.views.widgets.LoaderViewHolder;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by brunogabriel on 9/11/16.
 */
public class HomeCardAdapter extends BaseRecyclerViewAdapter {

    public HomeCardAdapter(Activity activity) {
        super(activity);
    }

    public HomeCardAdapter(Activity activity, Object headerObject) {
        super(activity);
        add(headerObject);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder mViewHolder;

        switch (viewType) {
            case ConstantsTypes.VH_LOADER:
                mViewHolder = new LoaderViewHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.holder_list_loader, parent, false));
                break;
            case ConstantsTypes.VH_DONATION_CATEGORIES:
                mViewHolder = new HorizontalRecyclerViewHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.holder_category_in_home, parent, false));
                break;

            default:
            case ConstantsTypes.VH_DONATION_HOME:
                mViewHolder = new HomeCardViewHolder(LayoutInflater.from(parent.getContext()).
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
        } else if (holder instanceof HomeCardViewHolder) {
            ((HomeCardViewHolder) holder).populateUI();
        } else if (holder instanceof HorizontalRecyclerViewHolder) {
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            layoutParams.setFullSpan(true);
            HomeCategoryCardAdapter homeCategoryCardAdapter = new HomeCategoryCardAdapter(activity);
            HorizontalRecyclerViewHolder horizontalRecyclerViewHolder = (HorizontalRecyclerViewHolder) holder;
            horizontalRecyclerViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
            horizontalRecyclerViewHolder.recyclerView.setAdapter(homeCategoryCardAdapter);
            homeCategoryCardAdapter.addAll((List<Object>) items.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object mCurrentItem = items.get(position);

        if (mCurrentItem instanceof LoaderModel) {
            return ConstantsTypes.VH_LOADER;
        } else if (mCurrentItem instanceof DonationModel) {
            return ConstantsTypes.VH_DONATION_HOME;
        } else {
            return ConstantsTypes.VH_DONATION_CATEGORIES;
        }
    }

    @Override
    public int getItemCount() {
        return items == null ? 0: items.size();
    }

    // Holders
    public class HomeCardViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cardTitle)
        AppCompatTextView cardTitle;

        public HomeCardViewHolder(View itemView) {
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

    public class HorizontalRecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.horizontalRecyclerview)
        RecyclerView recyclerView;

        public HorizontalRecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public class HomeCategoryCardAdapter extends BaseRecyclerViewAdapter {

        public HomeCategoryCardAdapter(Activity activity) {
            super(activity);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            VHCategoryCheck checkHolder = new VHCategoryCheck(LayoutInflater.from(parent.getContext()).
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
            VHCategoryCheck checkHolder = (VHCategoryCheck) holder;
            CategoryModel categoryModel = (CategoryModel) this.items.get(position);

            boolean isModelCheck = categoryModel.isChecked();
            if (isModelCheck) {
                checkHolder.checkMarker.setVisibility(View.VISIBLE);
                checkHolder.itemView.setAlpha(1.0f);
            } else {
                checkHolder.checkMarker.setVisibility(View.GONE);
                checkHolder.itemView.setAlpha(0.5f);
            }

            checkHolder.tvCategoryName.setText("" + categoryModel.getCategoryName());
        }

        @Override
        public int getItemCount() {
            return this.items == null ? 0: this.items.size();
        }

        public class VHCategoryCheck extends RecyclerView.ViewHolder {
            @BindView(R.id.circleImageView) CircleImageView circleImageView;
            @BindView(R.id.tvCategoryName) TextView tvCategoryName;
            @BindView(R.id.checkMarker) RelativeLayout checkMarker;
            View itemView;

            public VHCategoryCheck(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
                this.itemView = itemView;
                this.itemView.setOnClickListener(v -> performCheckBehaviour(getAdapterPosition()));
            }
        }
    }

}
