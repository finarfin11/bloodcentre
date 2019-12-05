package com.softuni.bloodcentre.data.models;

public enum BloodSampleStatus {
    UNTESTED("Untested"),
    CLEAN("Clean"),
    INFECTED("Infected"),
    ANTIERYTHROCYTEPOSITIVE("Antierythrocytepositive");

    private final String displayValue;

    private BloodSampleStatus(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
