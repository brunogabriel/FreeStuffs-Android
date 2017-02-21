package br.com.friendlydonations.shared.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by brunogabriel on 20/02/17.
 */

public abstract class BaseModel {

    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private String message;

    public BaseModel() {}

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
