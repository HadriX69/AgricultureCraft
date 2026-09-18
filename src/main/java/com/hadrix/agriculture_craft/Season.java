package com.hadrix.agriculture_craft;

public enum Season
{
    SPRING(0.0f),
    SUMMER(0.5f),
    AUTUMN(0.0f),
    WINTER(-0.8f);

    private final float tempModifier;

    Season(float tempModifier) {
        this.tempModifier = tempModifier;
    }

    public float getTempModifier() {
        return tempModifier;
    }
}
