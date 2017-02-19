package br.com.friendlydonations.shared.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import br.com.friendlydonations.shared.models.PictureDiskModel;

/**
 * Created by brunogabriel on 18/02/17.
 */

public class PictureUpdaterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PictureDiskModel> pictureDiskModels;

    public PictureUpdaterAdapter(List<PictureDiskModel> pictureDiskModels) {
        this.pictureDiskModels = pictureDiskModels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}