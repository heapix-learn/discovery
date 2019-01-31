package com.heapixLearn.discovery.entity.authorization;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Email implements EmailWith {
    @SerializedName("email")
    @Expose
    private String email;

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

}
