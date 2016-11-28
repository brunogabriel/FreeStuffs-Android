package br.com.friendlydonations.models.location;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by brunogabriel on 28/11/16.
 */

public class LocationModel implements Serializable{

    @SerializedName("context")
    private String context;

    @SerializedName("latlng")
    private List<Float> latlng;

    public LocationModel() {
        super();
    }

    public List<Float> getLatlng() {
        return latlng;
    }

    public void setLatlng(List<Float> latlng) {
        this.latlng = latlng;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
