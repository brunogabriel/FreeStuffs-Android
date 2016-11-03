package br.com.friendlydonations.views.widgets;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import br.com.friendlydonations.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by brunogabriel on 02/11/16.
 */

public class LoaderViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private Context context;

    public LoaderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#6D65E6"),
                android.graphics.PorterDuff.Mode.MULTIPLY);
    }
}
