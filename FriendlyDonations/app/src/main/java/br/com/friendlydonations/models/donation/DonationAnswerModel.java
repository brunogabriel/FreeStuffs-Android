package br.com.friendlydonations.models.donation;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import br.com.friendlydonations.models.BaseModel;

/**
 * Created by brunogabriel on 28/11/16.
 */

public class DonationAnswerModel extends BaseModel {

    public DonationAnswerModel() {
        super();
    }

    @SerializedName("data")
    private List<DonationModel> donationModelList;

    public List<DonationModel> getDonationModelList() {
        return donationModelList;
    }

    public void setCategoryModelList(List<DonationModel> donationModelList) {
        this.donationModelList = donationModelList;
    }

}
