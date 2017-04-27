package br.com.friendlydonations.shared.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by brunogabriel on 26/04/17.
 */

public class Image {

    private String large;
    private String thumb;
    private String loader;
    private String filename;
    private String directory;
    @SerializedName("_id")
    private String id;

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getLoader() {
        return loader;
    }

    public void setLoader(String loader) {
        this.loader = loader;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
