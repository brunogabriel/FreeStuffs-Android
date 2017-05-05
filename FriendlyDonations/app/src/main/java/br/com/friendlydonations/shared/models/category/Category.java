package br.com.friendlydonations.shared.models.category;

import com.google.gson.annotations.SerializedName;

import br.com.friendlydonations.shared.models.Image;

/**
 * Created by brunogabriel on 26/04/17.
 */

public class Category {

    @SerializedName("_id")
    private String id;

    private String name;

    private Image image;

    private transient boolean select;

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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}
