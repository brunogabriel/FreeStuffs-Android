package br.com.friendlydonations.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import br.com.friendlydonations.R;
import br.com.friendlydonations.managers.BaseActivity;
import br.com.friendlydonations.managers.BaseRecyclerViewAdapter;
import br.com.friendlydonations.models.CategoryModel;
import br.com.friendlydonations.utils.ConstantsTypes;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by brunogabriel on 21/09/16.
 */

public class CategoryAdapter extends BaseRecyclerViewAdapter {

    // By default, vhType is simple selection
    private int vhType = ConstantsTypes.VH_CATEGORY_SIMPLE;

    /** public CategoryAdapter (BaseActivity activity) {
        super(activity);
     } **/

    public CategoryAdapter(BaseActivity activity, int vhType) {
        super(activity);
        this.vhType = vhType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mInflateredView = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_card_category, parent, false);
        RecyclerView.ViewHolder viewHolder;

        switch (vhType) {
            case ConstantsTypes.VH_CATEGORY_CHECK:
                viewHolder = new VHCategoryCheck(mInflateredView);
            break;

            case ConstantsTypes.VH_CATEGORY_SIMPLE:
            default:
                viewHolder = new VHCategorySimple(mInflateredView);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        VHCategorySimple vhCategorySimple = (VHCategorySimple) holder;
        CategoryModel model = (CategoryModel) items.get(position);
        switch (vhType) {
            case ConstantsTypes.VH_CATEGORY_CHECK:
                boolean isModelCheck = model.isChecked();
                if (isModelCheck) {
                    ((VHCategoryCheck) vhCategorySimple).checkMarker.setVisibility(View.VISIBLE);
                    vhCategorySimple.itemView.setAlpha(1.0f);
                } else {
                    ((VHCategoryCheck) vhCategorySimple).checkMarker.setVisibility(View.GONE);
                    vhCategorySimple.itemView.setAlpha(0.5f);
                }
                // break; // not necessary
            case ConstantsTypes.VH_CATEGORY_SIMPLE:
            default:
                vhCategorySimple.tvCategoryName.setText(model.getCategoryName());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return items == null ? 0: items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return vhType;
    }

    private void performCheckBehaviour(int position) {
        int i = 0;
        for (Object mNextItem: items) {
            ((CategoryModel) mNextItem).setChecked(i == position ? true: false);
            i++;
        }

        notifyDataSetChanged();
    }

    /**
     * View Holders
     */

    public class VHCategorySimple extends RecyclerView.ViewHolder {

        @BindView(R.id.circleImageView) CircleImageView circleImageView;
        @BindView(R.id.tvCategoryName) TextView tvCategoryName;

        View itemView;

        public VHCategorySimple(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
        }
    }

    public class VHCategoryCheck extends VHCategorySimple {

        @BindView(R.id.checkMarker) RelativeLayout checkMarker;

        public VHCategoryCheck(View itemView) {
            super(itemView);
            itemView.setOnClickListener(v -> performCheckBehaviour(getAdapterPosition()));
        }

    }
}
