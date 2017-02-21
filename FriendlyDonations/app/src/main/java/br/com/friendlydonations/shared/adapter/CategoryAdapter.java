package br.com.friendlydonations.shared.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.com.friendlydonations.R;
import br.com.friendlydonations.application.donate.DonateFragment;
import br.com.friendlydonations.shared.models.ImageModel;
import br.com.friendlydonations.shared.models.category.CategoryAnswerModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by brunogabriel on 20/02/17.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {

    private Context context;
    private List<CategoryAnswerModel.CategoryModel> categoryModels;

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
        CategoryAnswerModel.CategoryModel categoryModel = categoryModels.get(position);
        ImageModel imageModel = categoryModel.getImageModel();
        Picasso.with(context).load(imageModel.getLarge()).into(holder.categoryImage);
    }

    @Override
    public int getItemCount() {
        return categoryModels.size();
    }

    public void addAll(List<CategoryAnswerModel.CategoryModel> categories) {
        categoryModels.addAll(categories);
        notifyItemRangeInserted(0, categories.size());
    }

    public class CategoryHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.category_image)
        CircleImageView categoryImage;

        public CategoryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
