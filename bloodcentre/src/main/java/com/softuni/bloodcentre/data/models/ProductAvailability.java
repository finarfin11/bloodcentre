package com.softuni.bloodcentre.data.models;

public enum ProductAvailability {
    AVAILABLE("Available"),
    UNAVAILABLE("Unavailable");

    private final String displayValue;

    private ProductAvailability(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
