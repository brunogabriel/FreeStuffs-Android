package br.com.friendlydonations.widgets;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import br.com.friendlydonations.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by brunogabriel on 10/02/17.
 */

public class BadgeView {

    protected RelativeLayout rootBadge;

    @BindView(R.id.badge_layout)
    protected RelativeLayout badgeLayout;

    @BindView(R.id.badge_image)
    protected ImageView badgeImage;

    @BindView(R.id.notification_text)
    protected TextView badgeText;

    public BadgeView (RelativeLayout rootBadge) {
        this.rootBadge = rootBadge;
        ButterKnife.bind(this, this.rootBadge);
    }

    public void changeVisibility () {
        if (badgeLayout != null) {
            badgeLayout.setVisibility(badgeLayout.getVisibility() == View.VISIBLE ? View.INVISIBLE: View.VISIBLE);
        }
    }

    public void updateNotifications(Integer notifications) {
        if (notifications != null && notifications > -1 && badgeText != null) {
            badgeText.setText(notifications > 99 ? "99": String.valueOf(notifications));
        }
    }

    public ImageView getBadgeImage() {
        return this.badgeImage;
    }
}
