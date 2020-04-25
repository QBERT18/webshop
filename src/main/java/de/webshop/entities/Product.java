package de.webshop.entities;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.net.URL;
import java.util.Objects;
import java.util.Set;

public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String productName;
    private double price; // in EUR
    private int stock;
    private Set<URL> imageUrls;
    private String description;

    public Product(@NotNull String productName, @NotNull double price) {
        this.productName = productName;
        this.price = price;
    }

    public Product() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Set<URL> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(Set<URL> imageUrls) {
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
        return id == product.id &&
                Double.compare(product.price, price) == 0 &&
                stock == product.stock &&
                productName.equals(product.productName) &&
                Objects.equals(imageUrls, product.imageUrls) &&
                Objects.equals(description, product.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName, price, stock, imageUrls, description);
    }
}
