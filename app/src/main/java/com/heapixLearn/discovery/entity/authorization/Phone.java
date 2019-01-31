package com.heapixLearn.discovery.entity.authorization;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Phone implements PhoneWith {
    @SerializedName("phone")
    @Expose
    private String phone;

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public void setPhone(String phone) {
        this.phone = phone;
    }

}
