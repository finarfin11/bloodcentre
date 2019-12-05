package com.softuni.bloodcentre.data.models;

public enum BloodProcessState {
    PROCESSED("Processed"),
    UNPROCESSED("Unprocessed");

    private final String displayValue;

    private BloodProcessState(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
