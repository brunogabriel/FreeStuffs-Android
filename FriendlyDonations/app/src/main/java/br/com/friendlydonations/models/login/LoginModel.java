package br.com.friendlydonations.models.login;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import br.com.friendlydonations.models.BaseModel;
import br.com.friendlydonations.models.ErrorModel;

/**
 * Created by brunogabriel on 07/02/17.
 */
public class LoginModel extends BaseModel implements Serializable {

    @SerializedName("error")
    private ErrorModel error;

    @SerializedName("data")
    private LoginModelData data;

    @SerializedName("notifications")
    private Integer notifications;

    public LoginModel() {
        super();
    }

    public ErrorModel getError() {
        return error;
    }

    public void setError(ErrorModel error) {
        this.error = error;
    }

    public LoginModelData getData() {
        return data;
    }

    public void setData(LoginModelData data) {
        this.data = data;
    }

    public Integer getNotifications() {
        return notifications;
    }

    public void setNotifications(Integer notifications) {
        this.notifications = notifications;
    }
}