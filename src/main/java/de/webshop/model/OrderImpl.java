package de.webshop.model;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Implementation of {@link Order}.
 */
public class OrderImpl implements Order {

    private final long orderId;
    private final long userId;
    private final LocalDateTime orderTime;
    private LocalDateTime deliverTime;
    private OrderStatus orderStatus;

    /**
     * @param orderId     > 0
     * @param userId      > 0
     * @param orderTime   must be non-null
     * @param deliverTime may be null if orderStatus != OrderStatus.DELIVERED
     * @param orderStatus must be non-null
     * @throws IllegalArgumentException if any id is < 0 or orderTime is null or orderStatus is null
     * @throws IllegalStateException    if orderStatus is DELIVERED but deliverTime is null
     */
    public OrderImpl(final long orderId, final long userId, final LocalDateTime orderTime, final LocalDateTime deliverTime, final OrderStatus orderStatus) {
        if (orderId <= 0 || userId <= 0 || orderTime == null || orderStatus == null) {
            throw new IllegalArgumentException("ID < 0 or orderTime null or orderStatus null");
        } else if (orderStatus == OrderStatus.DELIVERED && deliverTime == null) {
            throw new IllegalStateException("OrderStatus was DELIVERED but deliverTime was null");
        } else {
            this.orderId = orderId;
            this.userId = userId;
            this.orderTime = orderTime;
            this.deliverTime = deliverTime;
            this.orderStatus = orderStatus;
        }
    }

    @Override
    public long getOrderId() {
        return orderId;
    }

    @Override
    public long getUserId() {
        return userId;
    }

    @Override
    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    @Override
    public Optional<LocalDateTime> getDeliverTime() {
        return Optional.ofNullable(deliverTime);
    }

    @Override
    public void setDeliverTime(final LocalDateTime deliverTime) {
        this.deliverTime = deliverTime;
    }

    @Override
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    @Override
    public void setOrderStatus(final OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
