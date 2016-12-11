package br.com.friendlydonations.views.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import com.google.android.gms.location.places.Place;

/**
 * Created by brunogabriel on 11/12/16.
 */

public class LocationSelectEditText<T> extends AppCompatEditText {
    Place place;
    CharSequence mHint;

    public LocationSelectEditText(Context context) {
        super(context);
        mHint = getHint();
    }

    public LocationSelectEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHint = getHint();
    }

    public LocationSelectEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHint = getHint();
    }

    public void setPlace(Place place) {
        this.place = place;
        if (this.place == null) {
            setText("");
        } else {
            setText(place.getAddress());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setFocusable(false);
        setClickable(true);
    }

}
