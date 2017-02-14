package br.com.friendlydonations.application.main.donate;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.annimon.stream.Stream;


import br.com.friendlydonations.R;
import br.com.friendlydonations.models.PictureUpload;
import br.com.friendlydonations.shared.adapter.BaseRecyclerViewAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by brunogabriel on 13/02/17.
 */

public class PictureRecyclerViewAdapter extends BaseRecyclerViewAdapter {

    private int clickedItem;
    private final DonatePresenter presenter;
    public PictureRecyclerViewAdapter(DonatePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holder_picture, parent, false);
        return new PictureCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PictureCardViewHolder pictureHolder = (PictureCardViewHolder) holder;
        PictureUpload picture = (PictureUpload) items.get(position);
        pictureHolder.populate(picture);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateImage(Uri resultUri) {
        int index;
        PictureUpload candidate = (PictureUpload) items.get(clickedItem);
        if (candidate != null && candidate.getImage() != null) {
            index = clickedItem;
        } else {
            index = Stream.range(0, items.size())
                    .filter(value -> ((PictureUpload) items.get(value)).getImage() == null)
                    .findFirst()
                    .orElse(-1);

            if (index == -1) {
                index = clickedItem;
            }
        }
        PictureUpload pictureUploadCandidate = (PictureUpload) items.get(index);
        pictureUploadCandidate.setImage(resultUri);
        notifyItemChanged(index);
        presenter.scrollToPicture(index);
    }

    protected class PictureCardViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.camera_image)
        ImageView cameraImage;

        @BindView(R.id.content_image)
        ImageView contentImage;

        @BindView(R.id.frame_picture)
        FrameLayout framePicture;

        public PictureCardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.frame_picture)
        protected void onClickPictureHolder() {
            clickedItem = getAdapterPosition();
            presenter.showChooser();
        }

        public void populate(PictureUpload pictureUpload) {
            if (pictureUpload.getImage() != null) {
                cameraImage.setVisibility(View.GONE);
                contentImage.setVisibility(View.VISIBLE);
                presenter.populateImage(contentImage, pictureUpload);
            } else {
                cameraImage.setVisibility(View.VISIBLE);
                contentImage.setVisibility(View.GONE);
            }
        }
    }
}
