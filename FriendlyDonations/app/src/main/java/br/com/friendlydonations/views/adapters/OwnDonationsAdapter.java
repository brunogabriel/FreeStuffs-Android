package br.com.friendlydonations.views.adapters;

import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.com.friendlydonations.R;
import br.com.friendlydonations.managers.BaseActivity;
import br.com.friendlydonations.views.activities.DonationDetailActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by brunogabriel on 31/10/16.
 */

public class OwnDonationsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // TODO: USAR HOLDERTYPES INTERFACE
    private BaseActivity activity;
    private List<Object> items = new ArrayList<>();

    public OwnDonationsAdapter(BaseActivity activity) {
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // TODO
        View mInflateredView = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_card_donation, parent, false);
        RecyclerView.ViewHolder viewHolder = new OwnDonationsAdapter.OwnDonationCardViewHolder(mInflateredView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // TODO
        if (holder instanceof HomeCardAdapter.HomeCardViewHolder) {
            ((HomeCardAdapter.HomeCardViewHolder) holder).populateUI();
        }
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

    public class OwnDonationCardViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cardTitle)
        AppCompatTextView cardTitle;

        public OwnDonationCardViewHolder(View itemView) {
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
                cardTitle.setText("Brand new ladies bike 29 wheels...Brand new ladies bike 29 wheels Brand new ladies bike 29 wheels");
            } else {
                cardTitle.setText("Brand new ladies bike 29 wheels Brand new ladies bike 29 wheels...Brand new ladies bike 29 wheels Brand new ladies bike 29 wheels");
            }
        }
    }
}
