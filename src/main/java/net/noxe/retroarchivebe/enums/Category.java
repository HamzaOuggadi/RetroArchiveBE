package net.noxe.retroarchivebe.enums;

public enum Category {

    GAME("Game"),
    SOFTWARE("Software"),
    EDUCATION("Education"),
    EDUTAINMENT("Edutainment");

    private final String value;

    Category(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
