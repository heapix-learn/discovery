package com.heapixLearn.discovery.Server;

public enum TypeOfServerError {
    SERVER_ERROR("Server error");

    private String description;

    TypeOfServerError(String description) {
        this.description = description;
    }

    public String getTypeOfServerError() {return description;}
}
