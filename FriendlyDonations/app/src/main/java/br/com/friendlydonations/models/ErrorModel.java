package br.com.friendlydonations.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by brunogabriel on 10/10/16.
 */
public class ErrorModel {

    @SerializedName("cause")
    private String cause;

    @SerializedName("code")
    private Integer code;

    @SerializedName("type")
    private String type;

    public ErrorModel() {
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
