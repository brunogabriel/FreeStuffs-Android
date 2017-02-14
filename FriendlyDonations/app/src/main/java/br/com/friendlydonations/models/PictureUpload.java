package br.com.friendlydonations.models;

import android.net.Uri;

/**
 * Created by brunogabriel on 13/02/17.
 */

public class PictureUpload {
    private int position;
    private Uri image;

    public PictureUpload(int position, Uri image) {
        this.position = position;
        this.image = image;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }
}
