package br.com.friendlydonations.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by brunogabriel on 05/10/16.
 */

public class LoginModel extends BaseModel {

    @SerializedName("error")
    private ErrorModel error;

    public LoginModel() {
        super();
    }
}
