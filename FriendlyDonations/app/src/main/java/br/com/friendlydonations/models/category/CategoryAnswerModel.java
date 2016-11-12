package br.com.friendlydonations.models.category;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import br.com.friendlydonations.models.BaseModel;

/**
 * Created by brunogabriel on 09/11/16.
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
}
