package models;

/**
 * Created by lamd on 12/9/2016.
 */
public enum Modifier {
    PUBLIC("+"), DEFAULT(""), PROTECTED("#"), PRIVATE("-");

    private final String modifierValue;

    Modifier(String modifierValue) {
        this.modifierValue = modifierValue;
    }

    public String getModifierValue() {
        return this.modifierValue;
    }
}
