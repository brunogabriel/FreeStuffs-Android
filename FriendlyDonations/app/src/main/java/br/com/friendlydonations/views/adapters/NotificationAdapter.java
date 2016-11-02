package br.com.friendlydonations.views.adapters;

import android.app.Activity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.friendlydonations.R;
import br.com.friendlydonations.managers.BaseRecyclerViewAdapter;
import br.com.friendlydonations.models.NotificationModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by brunogabriel on 02/11/16.
 */

public class NotificationAdapter extends BaseRecyclerViewAdapter {

    public NotificationAdapter(Activity activity) {
        super(activity);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        VHNotification vhNotification = new VHNotification(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_notification, parent, false));


        return vhNotification;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (items.get(position) instanceof NotificationModel) {

        }
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public class VHNotification extends RecyclerView.ViewHolder {


        View itemView;

        @BindView(R.id.notificationTitle)
        AppCompatTextView notificationTitle;

        @BindView(R.id.notificationResume)
        AppCompatTextView notificationResume;

        @BindView(R.id.notificationImage)
        CircleImageView notificationImage;

        public VHNotification(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
