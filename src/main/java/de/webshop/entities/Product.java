package de.webshop.entities;

import de.webshop.constants.ProductCategory;
import de.webshop.constants.converter.ProductCategoryConverter;
import de.webshop.entities.relations.OrderProducts;

import javax.persistence.Column;
import javax.persistence.Convert;
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
import java.util.stream.Collectors;

@Entity
@Table(name = "PRODUCTS")
public class Product extends AbstractDbEntity<Product> {

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

    @Column(name = "CATEGORY", nullable = false)
    @Convert(converter = ProductCategoryConverter.class)
    private ProductCategory category;

    /**
     * Product constructor.
     *
     * @param productName not-null
     * @param price       >= 0
     * @param stock       >= 0
     * @param description not-null
     * @param category    not-null
     */
    public Product(@NotNull String productName, @PositiveOrZero double price, @PositiveOrZero int stock, @NotNull String description, @NotNull ProductCategory category) {
        this.productName = productName;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.category = category;
    }

    /**
     * empty Constructor for JPA
     */
    public Product() {
    }

    @Override
    public Product deepCopy() {
        final Product product = new Product();
        product.productId = productId;
        product.orderProducts = orderProducts != null ? orderProducts.stream().map(OrderProducts::deepCopy).collect(Collectors.toList()) : null;
        product.productName = productName;
        product.price = price;
        product.stock = stock;
        product.imageUrls = imageUrls;
        product.description = description;
        product.category = category;
        return product;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long id) {
        productId = id;
    }

    public List<OrderProducts> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProducts> orderProducts) {
        this.orderProducts = orderProducts;
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

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
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
                category == product.category &&
                productName.equals(product.productName) &&
                Objects.equals(imageUrls, product.imageUrls) &&
                Objects.equals(description, product.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productName, price, stock, imageUrls, description, category);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", orderProducts=" + orderProducts +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", imageUrls='" + imageUrls + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
