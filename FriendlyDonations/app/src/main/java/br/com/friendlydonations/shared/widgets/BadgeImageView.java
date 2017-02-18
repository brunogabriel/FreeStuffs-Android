package br.com.friendlydonations.shared.widgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.friendlydonations.R;

/**
 * Created by brunogabriel on 16/02/17.
 */

public class BadgeImageView {

    private final Context context;

    FrameLayout rootView;

    ImageView iconImage;
    TextView notificationCount;

    public BadgeImageView(Context context) {
        rootView = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.badge_imageview, null, false);
        this.context = context;
        init();
    }

    private void init() {
        iconImage = (ImageView) rootView.findViewById(R.id.icon_image);
        notificationCount = (TextView) rootView.findViewById(R.id.notification_count);
    }

    public void setupBadge(int imageId) {
        Picasso.with(context)
                .load(imageId)
                .into(iconImage);
    }

    public void setOrChangeNotificationCount(int count, boolean isVisible) {
        notificationCount.setVisibility(isVisible ? View.VISIBLE: View.INVISIBLE);
        notificationCount.setText(String.valueOf(count > 99 ? 99: count));
    }

    public FrameLayout getRootView() {
        return rootView;
    }
}
