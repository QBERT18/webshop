package de.webshop.entities;

import de.webshop.constants.OrderStatus;
import de.webshop.constants.converter.OrderStatusConverter;
import de.webshop.db.dataAccessObjects.OrderRepository;
import de.webshop.entities.relations.OrderProducts;
import de.webshop.util.DeepCopyUtility;

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
public class Order extends AbstractDbEntity<Order> {

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
    private OrderStatus status;

    /**
     * Order constructor.
     *
     * @param user      non-null
     * @param orderTime non-null
     * @param status    non-null
     */
    public Order(@NotNull User user, @NotNull @PastOrPresent LocalDateTime orderTime, @NotNull OrderStatus status) {
        this.user = user;
        this.orderTime = orderTime;
        this.status = status;
    }

    /**
     * Deep Copy Constructor.
     *
     * @param order the order to copy from
     */
    public Order(final Order order) {
        orderId = order.orderId;
        user = order.user;
        orderProducts = order.orderProducts;
        deliverTime = order.deliverTime;
        status = order.status;
    }

    /**
     * empty Constructor for JPA
     */
    public Order() {
    }

    @Override
    public Order deepCopy() {
        final Order copy = new Order();
        copy.orderId = orderId;
        copy.orderProducts = DeepCopyUtility.bulkDeepCopy(orderProducts);
        copy.orderTime = orderTime != null ? LocalDateTime.from(orderTime) : null;
        copy.deliverTime = deliverTime != null ? LocalDateTime.from(deliverTime) : null;
        copy.status = status;
        return copy;
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

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    // TODO move this into some kind of factory and declare a checked custom Exception
    public static Order from(final User user, final OrderStatus orderStatus) throws IllegalArgumentException {
        return new Order(user, LocalDateTime.now(), orderStatus);
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
                status == order.status &&
                user.equals(order.user) &&
                Objects.equals(orderProducts, order.orderProducts) &&
                orderTime.equals(order.orderTime) &&
                Objects.equals(deliverTime, order.deliverTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, user, orderProducts, orderTime, deliverTime, status);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", user=" + user +
                ", orderProducts=" + orderProducts +
                ", orderTime=" + orderTime +
                ", deliverTime=" + deliverTime +
                ", orderStatus=" + status +
                '}';
    }
}
