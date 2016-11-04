package br.com.friendlydonations.views.adapters;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.friendlydonations.R;
import br.com.friendlydonations.managers.BaseRecyclerViewAdapter;
import br.com.friendlydonations.models.LoaderModel;
import br.com.friendlydonations.models.ProfileDonationModel;
import br.com.friendlydonations.views.widgets.LoaderViewHolder;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by brunogabriel on 31/10/16.
 */

public class ProfileDonationAdapter extends BaseRecyclerViewAdapter {

    public static final int VIEW_TYPE_PROFILE = 1000;
    public static final int VIEW_TYPE_DONATIONS = 1001;

    public ProfileDonationAdapter(Activity activity, Object headerObject) {
        super(activity);
        add(headerObject);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder mViewHolder = null;

        switch (viewType) {
            case VIEW_TYPE_LOADER:
                mViewHolder = new LoaderViewHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.holder_list_loader, parent, false));
                break;

            case VIEW_TYPE_PROFILE:
                mViewHolder = new ProfileDescriptionViewHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.holder_profile_description, parent, false));
                break;

            case VIEW_TYPE_DONATIONS:
            default:
                mViewHolder = new HorizontalProfileDonationsViewHolder(LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.holder_profile_donations_view, parent, false));
                break;
        }


        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof LoaderViewHolder) {

        } else if (holder instanceof ProfileDescriptionViewHolder) {

        } else if (holder instanceof HorizontalProfileDonationsViewHolder) {
            OwnDonationsAdapter ownDonationAdapter = new OwnDonationsAdapter(activity);
            HorizontalProfileDonationsViewHolder horizontalRecyclerViewHolder = (HorizontalProfileDonationsViewHolder) holder;
            horizontalRecyclerViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
            horizontalRecyclerViewHolder.recyclerView.setAdapter(ownDonationAdapter);
            ownDonationAdapter.addAll((List<Object>) items.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object mCurrentItem = items.get(position);
        if (mCurrentItem instanceof LoaderModel) {
            return VIEW_TYPE_LOADER;
        } else if (mCurrentItem instanceof ProfileDonationModel) {
            return VIEW_TYPE_PROFILE;
        } else {
            return VIEW_TYPE_DONATIONS;
        }
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public class ProfileDescriptionViewHolder extends RecyclerView.ViewHolder {

        public ProfileDescriptionViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class HorizontalProfileDonationsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recyclerView)
        RecyclerView recyclerView;

        public HorizontalProfileDonationsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class OwnDonationsAdapter extends BaseRecyclerViewAdapter {

        public OwnDonationsAdapter(Activity activity) {
            super(activity);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            OwnDonationViewHolder ownDonationViewHolder = new OwnDonationViewHolder(LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.holder_card_donation_profile, parent, false));
            return ownDonationViewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return this.items == null ? 0: this.items.size();
        }

        public class OwnDonationViewHolder extends RecyclerView.ViewHolder {

            View itemView;

            public OwnDonationViewHolder(View itemView) {
                super(itemView);
                //ButterKnife.bind(this, itemView);
                this.itemView = itemView;
            }
        }
    }


}
