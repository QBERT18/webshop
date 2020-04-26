package de.webshop.constants;


public enum OrderStatus {
    OPEN("OPEN"), // initially received, but not yet issued
    WAITING_FOR_PAYMENT("WAITING_FOR_PAYMENT"), // issued by user, but not yet paid
    WAITING_FOR_DELIVERY("WAITING_FOR_DELIVERY"), // paid, but not in delivery
    IN_DELIVERY("IN_DELIVERY"), // paid and in delivery
    DELIVERED("DELIVERED"); // finished

    private final String code;

    OrderStatus(final String code) {
        this.code = code;
    }

    public String getDbCode() {
        return code;
    }
}
