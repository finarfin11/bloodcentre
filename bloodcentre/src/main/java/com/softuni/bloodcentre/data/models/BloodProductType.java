package com.softuni.bloodcentre.data.models;

public enum BloodProductType {
    ERYTHROCYTECONCENTRATE("Erythrocyte Concentrate"),
    THROMBOCYTECONCENTRATE("Thrombocyte Concentrate"),
    FRESHFROZENPLASMA("Fresh Frozen Plasma"),
    PRODUCTIONPLASMA("Production Plasma");

    private final String displayValue;

    private BloodProductType(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
