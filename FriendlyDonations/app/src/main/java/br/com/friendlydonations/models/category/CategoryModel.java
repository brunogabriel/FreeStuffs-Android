package br.com.friendlydonations.models.category;

import com.google.gson.annotations.SerializedName;

import br.com.friendlydonations.models.ImageModel;

/**
 * Created by brunogabriel on 31/10/16.
 */

public class CategoryModel {


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
