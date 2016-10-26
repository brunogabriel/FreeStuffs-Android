package br.com.friendlydonations.models;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by brunogabriel on 03/10/16.
 */
public class PictureUpload implements Serializable {

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
