package br.com.friendlydonations.views.adapters;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.com.friendlydonations.R;
import br.com.friendlydonations.managers.BaseActivity;
import br.com.friendlydonations.managers.BaseFragment;
import br.com.friendlydonations.models.PictureUpload;
import br.com.friendlydonations.utils.ApplicationUtilities;
import br.com.friendlydonations.utils.ConstantsTypes;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by brunogabriel on 21/09/16.
 */

public class PictureUploadAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PictureUpload> items = new ArrayList<>();
    BaseActivity activity;
    BaseFragment baseFragment;
    public int clickedItem = -1;

    public PictureUploadAdapter (BaseActivity activity, BaseFragment baseFragment) {
        this.activity = activity;
        this.baseFragment = baseFragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mInflateredView = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_card_picture, parent, false);
        RecyclerView.ViewHolder viewHolder = new PictureCardViewHolder(mInflateredView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PictureCardViewHolder pictureCardHolder = (PictureCardViewHolder) holder;
        PictureUpload pictureUpload = items.get(position);

        if (pictureUpload.getImage() != null) {
            pictureCardHolder.cameraImage.setVisibility(View.GONE);
            pictureCardHolder.contentImage.setVisibility(View.VISIBLE);
            Picasso.with(activity).load(pictureUpload.getImage()).into(pictureCardHolder.contentImage);
        } else {
            pictureCardHolder.cameraImage.setVisibility(View.VISIBLE);
            pictureCardHolder.contentImage.setVisibility(View.GONE);
        }
    }

    public void updateImage(Uri mBitmapCandidate) {
        int mChanged = -1;
        PictureUpload mPictureUploadCandidate = null;

        if (clickedItem >= 0 && clickedItem < items.size()) {
            mPictureUploadCandidate = items.get(clickedItem);
            mChanged = clickedItem;
        }

        if (mPictureUploadCandidate == null) {
            int mEmptyPosition = -1;
            for (int i = 0; i < items.size(); ++i) {
                if (items.get(i).getImage() == null) {
                    mEmptyPosition = i;
                    break;
                }
            }

            if (mEmptyPosition == -1) {
                mPictureUploadCandidate = items.get(clickedItem);
                mChanged = clickedItem;
            } else {
                mPictureUploadCandidate = items.get(mEmptyPosition);
                mChanged = mEmptyPosition;
            }
        }

        mPictureUploadCandidate.setImage(mBitmapCandidate);

        notifyItemChanged(mChanged);
    }

    @Override
    public int getItemCount() {
        return items == null ? 0: items.size();
    }

    public void addAll(List<PictureUpload> newItems) {
        if (newItems != null) {
            int beforeSize = items.size();
            int newItemsSize = newItems.size();
            items.addAll(newItems);
            notifyItemRangeInserted(beforeSize, newItemsSize);
        }
    }

    public void add(PictureUpload newItem) {
        if (newItem != null) {
            int beforeSize = items.size();
            items.add(newItem);
            notifyItemInserted(beforeSize);
        }
    }

    public class PictureCardViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.flPicture)
        FrameLayout flPicture;

        @BindView(R.id.cameraImage)
        ImageView cameraImage;

        @BindView(R.id.contentImage)
        ImageView contentImage;

        @OnClick(R.id.flPicture)
        protected void onClickCardView() {
            clickedItem = getAdapterPosition();
            selectImage();
        }

        public PictureCardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    protected void selectImage() {
        View mRootView = LayoutInflater.from(activity).inflate(R.layout.alert_donation_picture, null, false);
        final AlertDialog mDialog = new AlertDialog.Builder(activity).create();

        // applying typefaces
//        TextView alertTitle = (TextView) mRootView.findViewById(R.id.alertTitle);
//        TextView tvPicture = (TextView) mRootView.findViewById(R.id.tvPicture);
//        TextView tvCamera = (TextView) mRootView.findViewById(R.id.tvCamera);
//        TextView tvGallery = (TextView) mRootView.findViewById(R.id.tvGallery);

        ImageView ivCamera = (ImageView) mRootView.findViewById(R.id.ivCamera);
        ImageView ivGallery = (ImageView) mRootView.findViewById(R.id.ivGallery);

        ivCamera.setColorFilter(ContextCompat.getColor(activity, R.color.colorPrimary));
        ivGallery.setColorFilter(ContextCompat.getColor(activity, R.color.colorPrimary));

        ivCamera.setOnClickListener(v -> {

            Dexter.checkPermission(new PermissionListener() {
                @Override
                public void onPermissionGranted(PermissionGrantedResponse response) {
                    mDialog.cancel();
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File mPictureFile = ApplicationUtilities.getCameraOutputMediaFile(activity);
                    Uri fileUri = Uri.fromFile(mPictureFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                    baseFragment.startActivityForResult(intent, ConstantsTypes.ACTIVITY_RESULT_CAMERA);
                }

                @Override
                public void onPermissionDenied(PermissionDeniedResponse response) {
                    mDialog.cancel();
                    // TODO: Implement open settings
                }

                @Override
                public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                    mDialog.cancel();
                    token.continuePermissionRequest();
                }
            }, Manifest.permission.CAMERA);
        });

        ivGallery.setOnClickListener( v -> {
            mDialog.cancel();
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            baseFragment.startActivityForResult(intent, ConstantsTypes.ACTIVITY_RESULT_SELECT_PICTURE_GALLERY);
        });

        mDialog.setView(mRootView);
        mDialog.show();
    }
}
