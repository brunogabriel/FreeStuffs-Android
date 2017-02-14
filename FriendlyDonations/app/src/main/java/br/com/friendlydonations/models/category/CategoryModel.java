package br.com.friendlydonations.models.category;

/**
 * Created by brunogabriel on 13/02/17.
 */

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import br.com.friendlydonations.models.ImageModel;

public class CategoryModel implements Serializable {

    @SerializedName("_id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("updatedAt")
    private String updatedAt;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("image")
    private ImageModel imageModel;

    private boolean isChecked; // Only to marker

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public ImageModel getImageModel() {
        return imageModel;
    }

    public void setImageModel(ImageModel imageModel) {
        this.imageModel = imageModel;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}