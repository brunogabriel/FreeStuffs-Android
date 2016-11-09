package br.com.friendlydonations.models.login;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import br.com.friendlydonations.models.BaseModel;
import br.com.friendlydonations.models.ErrorModel;

/**
 * Created by brunogabriel on 05/10/16.
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

    /** Data Content Class **/
    public class LoginModelData implements Serializable{

        @SerializedName("_id")
        private String id;

        @SerializedName("name")
        private String name;

        @SerializedName("email")
        private String email;

        @SerializedName("gender")
        private String gender;

        @SerializedName("birthday")
        private String birthday;

        @SerializedName("picture")
        private String picture;

        @SerializedName("language")
        private String language;

        @SerializedName("active")
        private boolean active;

        @SerializedName("termsOfUse")
        private boolean termsOfUse;

        public LoginModelData() {
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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public boolean isTermsOfUse() {
            return termsOfUse;
        }

        public void setTermsOfUse(boolean termsOfUse) {
            this.termsOfUse = termsOfUse;
        }
    }
}


