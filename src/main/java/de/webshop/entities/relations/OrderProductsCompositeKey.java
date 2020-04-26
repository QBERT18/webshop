package de.webshop.entities.relations;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OrderProductsCompositeKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "ORDER_ID", nullable = false)
    long orderId;

    @Column(name = "PRODUCT_ID", nullable = false)
    long productId;

    /**
     * OrderProductsCompositeKey constuctor.
     *
     * @param orderId   non-null
     * @param productId non-null
     */
    public OrderProductsCompositeKey(@NotNull long orderId, @NotNull long productId) {
        this.orderId = orderId;
        this.productId = productId;
    }

    /**
     * empty Constructor for JPA
     */
    public OrderProductsCompositeKey() {
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderProductsCompositeKey that = (OrderProductsCompositeKey) o;
        return orderId == that.orderId &&
                productId == that.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId);
    }
}
