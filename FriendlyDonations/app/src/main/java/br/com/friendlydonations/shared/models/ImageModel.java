package br.com.friendlydonations.shared.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by brunogabriel on 20/02/17.
 */

public class ImageModel {
    @SerializedName("_id")
    private String id;

    @SerializedName("large")
    private String large;

    @SerializedName("thumb")
    private String thumb;

    @SerializedName("loader")
    private String loader;

    @SerializedName("filename")
    private String fileName;

    @SerializedName("directory")
    private String directory;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }
}
