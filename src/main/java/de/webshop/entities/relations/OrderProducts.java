package de.webshop.entities.relations;

import de.webshop.entities.Order;
import de.webshop.entities.Product;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Objects;

@Entity
@Table(name = "ORDER_PRODUCTS", indexes = {
        @Index(name = "index_order_id", columnList = "ORDER_ID"),
        @Index(name = "index_product_id", columnList = "PRODUCT_ID")})
public class OrderProducts {

    /*
     * ID
     */

    // a relation between product and order is identified by two primary keys, thus the composite key
    // because of the productCount attribute, we cannot use @ManyToMany for this relation
    @EmbeddedId
    private OrderProductsCompositeKey key;

    /*
     * RELATIONS
     */

    @ManyToOne
    @MapsId("ORDER_ID")
    @JoinColumn(name = "ORDER_ID", nullable = false)
    private Order order;

    @ManyToOne
    @MapsId("PRODUCT_ID")
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    private Product product;

    /*
     * FIELDS
     */

    @Column(name = "PRODUCT_COUNT", nullable = false)
    private int productCount;

    /**
     * OrderProducts constructor.
     *
     * @param order        non-null
     * @param product      non-null
     * @param productCount > 0
     */
    public OrderProducts(@NotNull Order order, @NotNull Product product, @Positive int productCount) {
        this.order = order;
        this.product = product;
        this.productCount = productCount;
    }

    /**
     * empty Constructor for JPA
     */
    public OrderProducts() {
    }

    public OrderProductsCompositeKey getKey() {
        return key;
    }

    public void setKey(OrderProductsCompositeKey key) {
        this.key = key;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderProducts that = (OrderProducts) o;
        return productCount == that.productCount &&
                key.equals(that.key) &&
                order.equals(that.order) &&
                product.equals(that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, order, product, productCount);
    }
}
