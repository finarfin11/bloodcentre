package com.softuni.bloodcentre.data.models;

public enum RequestState {
    WAITING("Waiting"),
    FULFILLED("Fulfilled"),
    REJECTED("Rejected");

    private final String displayValue;

    private RequestState (String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
