package com.heapixLearn.discovery.Entity;

public enum TypeOfServerError {
    SERVER_ERROR("Server error"),
    WRONG_CREDENTIALS("Wrong credentials"),
    INFO_IS_ABSENT("Info is absent"),
    INTERNET_DOES_NOT_WORK("Internet does not work");

    private String description;

    TypeOfServerError(String description) {
        this.description = description;
    }

    public String getTypeOfServerError() {return description;}
}
