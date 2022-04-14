package ru.mozevil.bank.entities;

public enum RoleName {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private final String name;

    RoleName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
