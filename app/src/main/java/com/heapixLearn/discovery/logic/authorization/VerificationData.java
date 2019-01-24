package com.heapixLearn.discovery.logic.authorization;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerificationData {
    @SerializedName("access_token")
    @Expose
    private String token;
    @SerializedName("code")
    @Expose
    private String code;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}

