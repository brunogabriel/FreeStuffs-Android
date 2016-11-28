package br.com.friendlydonations.models.donation;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import br.com.friendlydonations.models.ImageModel;
import br.com.friendlydonations.models.location.LocationModel;

/**
 * Created by brunogabriel on 01/11/16.
 */

public class DonationModel implements Serializable {

    // TODO: add comments object

    public DonationModel() {
        super();
    }

    @SerializedName("_id")
    private String id;

    @SerializedName("updatedAt")
    private String updatedAt;

    @SerializedName("createdAt")
    private String createAt;

    @SerializedName("user")
    private DonationUserModel userModel;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("delivery")
    private String delivery;

    @SerializedName("condition")
    private String condition;

    @SerializedName("contact_type")
    private String contactType;

    @SerializedName("contact_value")
    private String contactValue;

    @SerializedName("images")
    private List<ImageModel> images;

    @SerializedName("location")
    private LocationModel locationModel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public DonationUserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(DonationUserModel userModel) {
        this.userModel = userModel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public String getContactValue() {
        return contactValue;
    }

    public void setContactValue(String contactValue) {
        this.contactValue = contactValue;
    }

    public List<ImageModel> getImages() {
        return images;
    }

    public void setImages(List<ImageModel> images) {
        this.images = images;
    }

    public LocationModel getLocationModel() {
        return locationModel;
    }

    public void setLocationModel(LocationModel locationModel) {
        this.locationModel = locationModel;
    }
}
