package de.webshop.entities;

import de.webshop.entities.relations.OrderProducts;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "PRODUCTS")
public class Product {

    /*
     * ID
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID", unique = true, nullable = false, updatable = false)
    private long productId;

    /*
     * RELATIONS
     */

    @OneToMany(mappedBy = "product")
    private List<OrderProducts> orderProducts;

    /*
     * FIELDS
     */

    @Column(name = "PRODUCT_NAME", unique = true, nullable = false)
    private String productName;

    @Column(name = "PRICE_EUR", nullable = false)
    private double price; // in EUR

    @Column(name = "STOCK_COUNT", nullable = false)
    private int stock;

    @Column(name = "IMAGE_URLS", columnDefinition = "TEXT NOT NULL")
    private String imageUrls;

    @Column(name = "DESCRIPTION", columnDefinition = "TEXT NOT NULL")
    private String description;

    /**
     * Product constructor.
     *
     * @param productName not-null
     * @param price       >= 0
     * @param stock       >= 0
     * @param description not-null
     */
    public Product(@NotNull String productName, @PositiveOrZero double price, @PositiveOrZero int stock, @NotNull String description) {
        this.productName = productName;
        this.price = price;
        this.stock = stock;
        this.description = description;
    }

    /**
     * empty Constructor for JPA
     */
    public Product() {
    }

    public long getId() {
        return productId;
    }

    public void setId(long id) {
        productId = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(String imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return productId == product.productId &&
                Double.compare(product.price, price) == 0 &&
                stock == product.stock &&
                productName.equals(product.productName) &&
                Objects.equals(imageUrls, product.imageUrls) &&
                Objects.equals(description, product.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productName, price, stock, imageUrls, description);
    }
}
