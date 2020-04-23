package de.webshop.model;

import java.time.LocalDateTime;
import java.util.Optional;

public interface Order {

    /**
     * @return the order id
     */
    long getOrderId();

    /**
     * @return the user id this order belongs to
     */
    long getUserId();

    /**
     * @return when the order was received
     */
    LocalDateTime getOrderTime();

    /**
     * @return when the order was delivered or empty if it was not delivered yet
     */
    Optional<LocalDateTime> getDeliverTime();

    /**
     * @param deliverTime the time the order was delivered
     */
    void setDeliverTime(LocalDateTime deliverTime);

    /**
     * @return the current order status
     */
    OrderStatus getOrderStatus();

    /**
     * @param orderStatus the new status of this order
     */
    void setOrderStatus(OrderStatus orderStatus);
}
