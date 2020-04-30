package de.webshop.dataTransferObjects;


import de.webshop.constants.OrderStatus;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

public class OrderData implements DataTransferObject {

    @NotNull
    @NotEmpty
    private LocalDateTime orderTime;

    @NotNull
    @NotEmpty
    private LocalDateTime deliverTime;

    @NotNull
    @NotEmpty
    private OrderStatus orderStatus;


    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public LocalDateTime getDeliverTime() {
        return deliverTime;
    }

    public void setDeliverTime(LocalDateTime deliverTime) {
        this.deliverTime = deliverTime;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderData orderData = (OrderData) o;
        return Objects.equals(orderTime, orderData.orderTime) &&
                Objects.equals(deliverTime, orderData.deliverTime) &&
                orderStatus == orderData.orderStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderTime, deliverTime, orderStatus);
    }

    @Override
    public String toString() {
        return "OrderData{" +
                "orderTime=" + orderTime +
                ", deliverTime=" + deliverTime +
                ", orderStatus=" + orderStatus +
                '}';
    }

    @Override
    public boolean isValid() {
        return orderTime != null && deliverTime != null && orderStatus != null;
    }
}
