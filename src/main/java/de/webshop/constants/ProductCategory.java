package de.webshop.constants;

public enum ProductCategory {

    Vanille("Vanille"),
    Schokolade("Schokolade"),
    Hafer("Hafer"),
    Andere("Andere");

    private final String dbCode;

    ProductCategory(final String dbCode) {
        this.dbCode = dbCode;
    }

    public String getDbCode() {
        return dbCode;
    }
}
