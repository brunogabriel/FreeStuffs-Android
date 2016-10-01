package br.com.friendlydonations.views.adapters;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.friendlydonations.R;
import br.com.friendlydonations.managers.BaseActivity;
import br.com.friendlydonations.managers.BaseFragment;
import br.com.friendlydonations.utils.ConstantsTypes;
import br.com.friendlydonations.utils.TypefaceMaker;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by brunogabriel on 21/09/16.
 */

public class PictureUploadAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> items = new ArrayList<>();
    BaseActivity activity;
    BaseFragment baseFragment;

    protected Typeface mRobotoMedium;
    protected Typeface mMonserratRegular;

    public PictureUploadAdapter (BaseActivity activity, BaseFragment baseFragment) {
        this.activity = activity;
        this.baseFragment = baseFragment;
        setupTypefaces();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // TODO
        View mInflateredView = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_card_picture, parent, false);
        RecyclerView.ViewHolder viewHolder = new PictureCardViewHolder(mInflateredView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return items == null ? 0: items.size();
    }

    public void setupTypefaces() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                mMonserratRegular = TypefaceMaker.createTypeFace(activity, TypefaceMaker.FontFamily.MontserratRegular);
                mRobotoMedium = TypefaceMaker.createTypeFace(activity, TypefaceMaker.FontFamily.RobotoMedium);
                return null;
            }
        }.execute();
    }

    public void addAll(List<Object> newItems) {
        if (newItems != null) {
            int beforeSize = items.size();
            int newItemsSize = newItems.size();
            items.addAll(newItems);
            notifyItemRangeInserted(beforeSize, newItemsSize);
        }
    }

    public void add(Object newItem) {
        if (newItem != null) {
            int beforeSize = items.size();
            items.add(newItem);
            notifyItemInserted(beforeSize);
        }
    }

    public class PictureCardViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.flPicture)
        FrameLayout flPicture;

        @OnClick(R.id.flPicture)
        protected void onClickCardView() {
            int position = getAdapterPosition();
            selectImage();
        }

        public PictureCardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void populateUI() {

        }

    }

    private void selectImage() {
        View mRootView = LayoutInflater.from(activity).inflate(R.layout.alert_donation_picture, null, false);
        AlertDialog.Builder mDialog = new AlertDialog.Builder(activity);

        // applying typefaces
        TextView alertTitle = (TextView) mRootView.findViewById(R.id.alertTitle);
        alertTitle.setTypeface(mMonserratRegular);

        TextView tvPicture = (TextView) mRootView.findViewById(R.id.tvPicture);
        tvPicture.setTypeface(mMonserratRegular);

        TextView tvCamera = (TextView) mRootView.findViewById(R.id.tvCamera);
        tvCamera.setTypeface(mRobotoMedium);

        TextView tvGallery = (TextView) mRootView.findViewById(R.id.tvGallery);
        tvGallery.setTypeface(mRobotoMedium);

        ImageView ivCamera = (ImageView) mRootView.findViewById(R.id.ivCamera);
        ImageView ivGallery = (ImageView) mRootView.findViewById(R.id.ivGallery);
        ivCamera.setColorFilter(ContextCompat.getColor(activity, R.color.colorPrimary));
        ivGallery.setColorFilter(ContextCompat.getColor(activity, R.color.colorPrimary));

        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                baseFragment.startActivityForResult(intent, ConstantsTypes.ACTIVITY_RESULT_CAMERA);
                //activity.startActivityForResult(intent, ConstantsTypes.ACTIVITY_RESULT_CAMERA);//REQUEST_CAMERA
            }
        });

        ivGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                //activity.startActivityForResult(Intent.createChooser(intent, activity.getString(R.string.donate_include_image_textual)), ConstantsTypes.ACTIVITY_RESULT_SELECT_PICTURE_GALLERY);//SELECT_FILE
            }
        });

        mDialog.setView(mRootView);
        mDialog.show();
    }
}
