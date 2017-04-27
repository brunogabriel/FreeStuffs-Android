package br.com.friendlydonations.shared.models.category;

import java.util.List;

import br.com.friendlydonations.shared.models.BaseModel;

/**
 * Created by brunogabriel on 26/04/17.
 */

public class CategoryData extends BaseModel {

    private List<Category> data;

    public List<Category> getData() {
        return data;
    }

    public void setData(List<Category> data) {
        this.data = data;
    }
}
