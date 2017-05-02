package br.com.friendlydonations.application.main.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.friendlydonations.R;
import br.com.friendlydonations.shared.models.category.Category;

/**
 * Created by brunogabriel on 02/05/17.
 */

public class HomeCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Category> items;

    public HomeCategoryAdapter(@NonNull List<Category> items) {
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_category, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private class CategoryViewHolder extends RecyclerView.ViewHolder {

        public CategoryViewHolder(View itemView) {
            super(itemView);
        }
    }
}
