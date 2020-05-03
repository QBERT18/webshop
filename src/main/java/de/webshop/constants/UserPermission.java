package de.webshop.constants;

public enum UserPermission {

    FULL("FULL"), // admin
    STANDARD("STANDARD"), // normal user
    RESTRICTED("RESTRICTED"); // unverified user

    private final String dbCode;

    UserPermission(final String dbCode) {
        this.dbCode = dbCode;
    }

    public String getDbCode() {
        return dbCode;
    }
}
