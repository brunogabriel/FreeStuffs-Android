package br.com.friendlydonations.shared.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;
import java.util.List;

import br.com.friendlydonations.R;
import br.com.friendlydonations.shared.ImageHelper;
import br.com.friendlydonations.shared.models.ImageModel;
import br.com.friendlydonations.shared.models.category.CategoryModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by brunogabriel on 20/02/17.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {

    private int clickedIndex = -1;
    private Context context;
    private List<CategoryModel> categoryModels;

    public CategoryAdapter(Context context) {
        categoryModels = new ArrayList<>();
        this.context = context;
    }

    @Override
    public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoryHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_category, parent, false));
    }

    @Override
    public void onBindViewHolder(CategoryHolder holder, int position) {
        CategoryModel categoryModel = categoryModels.get(position);
        ImageModel imageModel = categoryModel.getImageModel();
        ImageHelper.loadImageWithPlaceholder(holder.categoryImage, context, imageModel.getLoader(), imageModel.getLarge());
        holder.categoryText.setText(categoryModel.getName());
        holder.checkView.setVisibility(clickedIndex == position ? View.VISIBLE: View.GONE);
    }

    @Override
    public int getItemCount() {
        return categoryModels.size();
    }

    public void addAll(List<CategoryModel> categories) {
        categoryModels.addAll(categories);
        notifyItemRangeInserted(0, categories.size());
    }

    public class CategoryHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.category_image)
        CircleImageView categoryImage;

        @BindView(R.id.category_text)
        TextView categoryText;

        @BindView(R.id.check_marker)
        View checkView;

        public CategoryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            RxView.clicks(itemView).subscribe(aVoid -> {
                if (clickedIndex == -1) {
                    clickedIndex = getAdapterPosition();
                    notifyItemChanged(clickedIndex);
                } else if (clickedIndex != -1 && clickedIndex != getAdapterPosition()) {
                    int oldPosition = clickedIndex;
                    notifyItemChanged(oldPosition);
                    clickedIndex = getAdapterPosition();
                    notifyItemChanged(clickedIndex);
                }
            });
        }
    }
}
