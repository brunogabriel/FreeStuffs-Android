package br.com.friendlydonations.shared.models.category;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import br.com.friendlydonations.shared.models.BaseModel;
import br.com.friendlydonations.shared.models.ImageModel;

/**
 * Created by brunogabriel on 20/02/17.
 */

public class CategoryAnswerModel extends BaseModel {

    @SerializedName("data")
    private List<CategoryModel> categoryModelList;

    public List<CategoryModel> getCategoryModelList() {
        return categoryModelList;
    }

    public void setCategoryModelList(List<CategoryModel> categoryModelList) {
        this.categoryModelList = categoryModelList;
    }

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
}
