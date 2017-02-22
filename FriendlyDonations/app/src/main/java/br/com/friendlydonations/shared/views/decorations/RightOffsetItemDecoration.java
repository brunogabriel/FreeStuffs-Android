package br.com.friendlydonations.shared.views.decorations;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by brunogabriel on 21/02/17.
 */

public class RightOffsetItemDecoration extends RecyclerView.ItemDecoration {

    private int mItemOffset;

    public RightOffsetItemDecoration(int itemOffset) {
        mItemOffset = itemOffset;
    }

    public RightOffsetItemDecoration(@NonNull Context context, @DimenRes int itemOffsetId) {
        this(context.getResources().getDimensionPixelSize(itemOffsetId));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0, 0, mItemOffset, 0);
    }
}
