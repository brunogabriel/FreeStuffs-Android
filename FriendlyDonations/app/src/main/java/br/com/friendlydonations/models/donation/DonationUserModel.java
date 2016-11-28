package br.com.friendlydonations.models.donation;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by brunogabriel on 28/11/16.
 */

public class DonationUserModel implements Serializable {
    @SerializedName("_id")
    private String id;

    @SerializedName("name")
    private String name;

    public DonationUserModel() {
        super();
    }

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
}
