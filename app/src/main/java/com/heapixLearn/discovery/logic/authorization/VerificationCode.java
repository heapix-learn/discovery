package com.heapixLearn.discovery.logic.authorization;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerificationCode {
    @SerializedName("code")
    @Expose
    private String code;

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}

