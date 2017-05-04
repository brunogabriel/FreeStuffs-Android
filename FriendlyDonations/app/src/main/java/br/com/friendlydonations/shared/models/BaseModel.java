package br.com.friendlydonations.shared.models;

import java.io.Serializable;

/**
 * Created by brunogabriel on 26/04/17.
 */

public abstract class BaseModel implements Serializable {

    private boolean status;
    private String message;

    public boolean getStatus() {
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
