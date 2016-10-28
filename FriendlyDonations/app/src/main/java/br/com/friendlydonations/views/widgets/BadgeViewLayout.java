package br.com.friendlydonations.views.widgets;

import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;


/**
 * Created by brunogabriel on 9/16/16.
 */
public class BadgeViewLayout {

    private RelativeLayout rootBadge;
    private RelativeLayout rlBadge;
    private ImageView badgeImage;
    private AppCompatTextView badgeText;

    public BadgeViewLayout (RelativeLayout rootBadge) {
        this.rootBadge = rootBadge;
    }

    public void initUi(int idImage, int idBadge, int idText) {
        if (rootBadge != null) {
            rlBadge = (RelativeLayout) rootBadge.findViewById(idBadge);
            badgeText = (AppCompatTextView) rootBadge.findViewById(idText);
            badgeImage = (ImageView) rootBadge.findViewById(idImage);
        }
    }

    public void changeVisibility () {
        if (rlBadge != null) {
            rlBadge.setVisibility(rlBadge.getVisibility() == View.VISIBLE ? View.INVISIBLE: View.VISIBLE);
        }
    }


    public RelativeLayout getRlBadge() {
        return rlBadge;
    }

    public ImageView getBadgeImage() {
        return badgeImage;
    }

    public AppCompatTextView getBadgeText() {
        return badgeText;
    }
}
