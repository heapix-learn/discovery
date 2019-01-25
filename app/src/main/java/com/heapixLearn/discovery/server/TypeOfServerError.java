package com.heapixLearn.discovery.server;

public enum TypeOfServerError {
    SERVER_ERROR("Server error"),
    INTERNET_DOES_NOT_WORK("Internet does not work");

    private String description;

    TypeOfServerError(String description) {
        this.description = description;
    }

    public String getTypeOfServerError() {return description;}
}
