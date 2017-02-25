package br.com.friendlydonations.shared.models.category;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import br.com.friendlydonations.shared.models.BaseModel;

/**
 * Created by brunogabriel on 20/02/17.
 */

public class CategoryAnswerModel extends BaseModel {

    public CategoryAnswerModel() {
    }

    @SerializedName("data")
    private List<CategoryModel> categoryModelList;

    public List<CategoryModel> getCategoryModelList() {
        return categoryModelList;
    }

    public void setCategoryModelList(List<CategoryModel> categoryModelList) {
        this.categoryModelList = categoryModelList;
    }
}
