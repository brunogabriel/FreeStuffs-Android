package br.com.friendlydonations.application.main.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import br.com.friendlydonations.R;
import br.com.friendlydonations.shared.models.category.Category;
import br.com.friendlydonations.shared.ui.ViewHelper;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by brunogabriel on 02/05/17.
 */

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.CategoryViewHolder> {

    private Context context;
    private List<Category> items;

    public HomeCategoryAdapter(@NonNull Context context, @NonNull List<Category> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_category, parent, false));
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        Category category = items.get(position);
        holder.categoryName.setText(category.getName());
        ViewHelper.loadImageWithPlaceholder(context, holder.circleImageView, category.getImage().getLoader(), category.getImage().getLarge());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.circle_image_view)
        CircleImageView circleImageView;

        @BindView(R.id.check_marker)
        RelativeLayout checkMarker;

        @BindView(R.id.category_name)
        TextView categoryName;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}