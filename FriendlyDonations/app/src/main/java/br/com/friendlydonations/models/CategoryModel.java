package br.com.friendlydonations.models;

/**
 * Created by brunogabriel on 31/10/16.
 */

public class CategoryModel {

    private String categoryName;
    private boolean isChecked;

    public CategoryModel(String categoryName, boolean isChecked) {
        this.categoryName = categoryName;
        this.isChecked = isChecked;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
