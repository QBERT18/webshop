package de.webshop.entities;

import de.webshop.constants.OrderStatus;
import de.webshop.constants.converter.OrderStatusConverter;
import de.webshop.entities.relations.OrderProducts;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "ORDERS")
public class Order {

    /*
     * ID
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID", nullable = false, updatable = false, unique = true)
    private long orderId;

    /*
     * RELATIONS
     */

    @ManyToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_USER_ID", nullable = false, updatable = false)
    private User user;

    @OneToMany(mappedBy = "order")
    private List<OrderProducts> orderProducts;

    /*
     * FIELDS
     */

    @Column(name = "ORDER_TIMESTAMP", nullable = false, updatable = false)
    private LocalDateTime orderTime;

    @Column(name = "DELIVER_TIMESTAMP")
    private LocalDateTime deliverTime;

    @Column(name = "ORDER_STATUS", nullable = false)
    @Convert(converter = OrderStatusConverter.class)
    private OrderStatus orderStatus;

    /**
     * Order constructor.
     *
     * @param user        non-null
     * @param orderTime   non-null
     * @param orderStatus non-null
     */
    public Order(@NotNull User user, @NotNull @PastOrPresent LocalDateTime orderTime, @NotNull OrderStatus orderStatus) {
        this.user = user;
        this.orderTime = orderTime;
        this.orderStatus = orderStatus;
    }

    /**
     * empty Constructor for JPA
     */
    public Order() {
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderProducts> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProducts> orderProducts) {
        this.orderProducts = orderProducts;
    }

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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return orderId == order.orderId &&
                orderStatus == order.orderStatus &&
                user.equals(order.user) &&
                Objects.equals(orderProducts, order.orderProducts) &&
                orderTime.equals(order.orderTime) &&
                Objects.equals(deliverTime, order.deliverTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, user, orderProducts, orderTime, deliverTime, orderStatus);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", user=" + user +
                ", orderProducts=" + orderProducts +
                ", orderTime=" + orderTime +
                ", deliverTime=" + deliverTime +
                ", orderStatus=" + orderStatus +
                '}';
    }
}
