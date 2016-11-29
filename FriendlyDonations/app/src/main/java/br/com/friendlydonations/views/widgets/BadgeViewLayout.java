package br.com.friendlydonations.views.widgets;

import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import br.com.friendlydonations.R;
import br.com.friendlydonations.views.actions.BadgeAction;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by brunogabriel on 9/16/16.
 */
public class BadgeViewLayout implements BadgeAction {

    protected RelativeLayout rootBadge;

    @BindView(R.id.rlBadge)
    protected RelativeLayout rlBadge;

    @BindView(R.id.badgeImage)
    protected ImageView badgeImage;

    @BindView(R.id.textBadge)
    protected AppCompatTextView badgeText;

    public BadgeViewLayout (RelativeLayout rootBadge) {
        this.rootBadge = rootBadge;
        ButterKnife.bind(this, this.rootBadge);
    }

    public void changeVisibility () {
        if (rlBadge != null) {
            rlBadge.setVisibility(rlBadge.getVisibility() == View.VISIBLE ? View.INVISIBLE: View.VISIBLE);
        }
    }

    @Override
    public void updateNotifications(Integer notifications) {
        if (notifications != null && notifications > -1 && badgeText != null) {
            badgeText.setText(notifications > 99 ? "99": String.valueOf(notifications));
        }
    }

    public ImageView getBadgeImage() {
        return this.badgeImage;
    }
}
