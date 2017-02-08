package br.com.friendlydonations.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by brunogabriel on 07/02/17.
 */

public abstract class BaseModel implements Serializable {

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