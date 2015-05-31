package com.mu.mothersunited.model;

/**
 * Created by dhilipb on 31/05/2015.
 */
public enum UserType {
    DOCTOR("Doctor"),
    EXPECTING("Expecting"),
    MOTHER("Mother");

    String type;
    UserType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
