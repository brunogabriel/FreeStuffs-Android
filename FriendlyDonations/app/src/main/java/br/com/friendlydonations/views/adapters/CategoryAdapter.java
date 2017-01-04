//package br.com.friendlydonations.views.adapters;
//
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import br.com.friendlydonations.R;
//import br.com.friendlydonations.managers.BaseActivity;
//import br.com.friendlydonations.managers.BaseRecyclerViewAdapter;
//import br.com.friendlydonations.models.category.CategoryModel;
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import de.hdodenhof.circleimageview.CircleImageView;
//
///**
// * Created by brunogabriel on 21/09/16.
// */
//
//public class CategoryAdapter extends BaseRecyclerViewAdapter {
//
//    public static final int VIEW_TYPE_SIMPLE = 1000;
//    public static final int VIEW_TYPE_CHECK = 1001;
//
//    // By default, vhType is simple selection
//    private int vhType = VIEW_TYPE_SIMPLE;
//
//    public CategoryAdapter(BaseActivity activity, int vhType) {
//        super(activity);
//        this.vhType = vhType;
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View mInflateredView = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_card_category, parent, false);
//        RecyclerView.ViewHolder viewHolder;
//
//        switch (vhType) {
//            case VIEW_TYPE_CHECK:
//                viewHolder = new CategoryCheckViewHolder(mInflateredView);
//            break;
//
//            case VIEW_TYPE_SIMPLE:
//            default:
//                viewHolder = new CategorySimpleViewHolder(mInflateredView);
//                break;
//        }
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        CategorySimpleViewHolder vhCategorySimple = (CategorySimpleViewHolder) holder;
//        CategoryModel model = (CategoryModel) items.get(position);
//        switch (vhType) {
//            case VIEW_TYPE_CHECK:
//                boolean isModelCheck = model.isChecked();
//                if (isModelCheck) {
//                    ((CategoryCheckViewHolder) vhCategorySimple).checkMarker.setVisibility(View.VISIBLE);
//                    vhCategorySimple.itemView.setAlpha(1.0f);
//                } else {
//                    ((CategoryCheckViewHolder) vhCategorySimple).checkMarker.setVisibility(View.GONE);
//                    vhCategorySimple.itemView.setAlpha(0.5f);
//                }
//                // break; // not necessary
//            case VIEW_TYPE_SIMPLE:
//            default:
//                vhCategorySimple.tvCategoryName.setText(model.getName());
//                break;
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return items == null ? 0: items.size();
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return vhType;
//    }
//
//    private void performCheckBehaviour(int position) {
//        int i = 0;
//        for (Object mNextItem: items) {
//            ((CategoryModel) mNextItem).setChecked(i == position ? true: false);
//            i++;
//        }
//
//        notifyDataSetChanged();
//    }
//
//    /**
//     * View Holders
//     */
//
//    public class CategorySimpleViewHolder extends RecyclerView.ViewHolder {
//
//        @BindView(R.id.circleImageView) CircleImageView circleImageView;
//        @BindView(R.id.tvCategoryName) TextView tvCategoryName;
//
//        View itemView;
//
//        public CategorySimpleViewHolder(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this, itemView);
//            this.itemView = itemView;
//        }
//    }
//
//    public class CategoryCheckViewHolder extends CategorySimpleViewHolder {
//        @BindView(R.id.checkMarker) RelativeLayout checkMarker;
//
//        public CategoryCheckViewHolder(View itemView) {
//            super(itemView);
//            itemView.setOnClickListener(v -> performCheckBehaviour(getAdapterPosition()));
//        }
//    }
//}
