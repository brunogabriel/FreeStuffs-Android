package br.com.friendlydonations.shared.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.List;

import br.com.friendlydonations.R;
import br.com.friendlydonations.shared.models.PictureDiskModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by brunogabriel on 18/02/17.
 */

public class PictureUpdaterAdapter extends RecyclerView.Adapter<PictureUpdaterAdapter.PictureUpdaterAdapterHolder> {

    private List<PictureDiskModel> pictureDiskModels;
    private Action1<PictureDiskModel> actionClickPictureModel;

    public PictureUpdaterAdapter() {
        this.pictureDiskModels = new ArrayList<>();
        Stream.range(0, 5).forEach(index -> this.pictureDiskModels.add(new PictureDiskModel(index)));
    }

    @Override
    public PictureUpdaterAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_image_selector, parent, false);
        return new PictureUpdaterAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(PictureUpdaterAdapterHolder holder, int position) {
        PictureDiskModel pictureDiskModel = pictureDiskModels.get(position);
        holder.populate(pictureDiskModel);
    }

    @Override
    public int getItemCount() {
        return pictureDiskModels.size();
    }

    public void setActionClickPictureModel(Action1<PictureDiskModel> actionClickPictureModel) {
        this.actionClickPictureModel = actionClickPictureModel;
    }


    public class PictureUpdaterAdapterHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.camera_image)
        ImageView camera_image;

        @BindView(R.id.content_image)
        ImageView contentImage;

        @OnClick(R.id.frame_picture)
        protected void onClickFramePicture() {
            actionClickPictureModel.call(pictureDiskModels.get(getAdapterPosition()));
        }

        public PictureUpdaterAdapterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void populate(PictureDiskModel pictureDiskModel) {

        }
    }
}