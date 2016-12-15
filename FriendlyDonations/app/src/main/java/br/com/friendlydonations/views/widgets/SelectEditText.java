package br.com.friendlydonations.views.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

/**
 * Created by brunogabriel on 11/12/16.
 */

public class SelectEditText<T> extends AppCompatEditText {
    Object mModel;
    CharSequence mHint;

    public SelectEditText(Context context) {
        super(context);
        mHint = getHint();
    }

    public SelectEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHint = getHint();
    }

    public SelectEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHint = getHint();
    }

    public void setModel(Object mModel, boolean isSetText) {
        this.mModel = mModel;

        if (!isSetText) {
            return;
        } else {
            if (this.mModel == null) {
                setText("");
            } else {
                setText(mModel.toString());
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setFocusable(false);
        setClickable(true);
    }

}
