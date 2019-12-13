package com.softuni.bloodcentre.data.models;

public enum Sex {
    MALE("Male"),
    FEMALE("Female");

    private final String displayValue;

    private Sex(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
