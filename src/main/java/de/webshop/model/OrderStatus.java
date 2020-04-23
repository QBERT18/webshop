package de.webshop.model;

public enum OrderStatus {
    OPEN(1), // initially received, but not yet issued
    WAITING_FOR_PAYMENT(2), // issued by user, but not yet paid
    WAITING_FOR_DELIVERY(3), // paid, but not in delivery
    IN_DELIVERY(4), // paid and in delivery
    DELIVERED(5); // finished

    private final int id;

    OrderStatus(final int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
