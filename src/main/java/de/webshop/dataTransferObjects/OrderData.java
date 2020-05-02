package de.webshop.dataTransferObjects;


import de.webshop.entities.Product;
import de.webshop.entities.User;

import java.util.Objects;

public class OrderData implements DataTransferObject {

    private User user;

    private Product product;

    private int productCount;

    public OrderData(User user, Product product, int productCount) {
        this.user = user;
        this.product = product;
        this.productCount = productCount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderData orderData = (OrderData) o;
        return productCount == orderData.productCount &&
                Objects.equals(user, orderData.user) &&
                Objects.equals(product, orderData.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, product, productCount);
    }

    @Override
    public String toString() {
        return "OrderData{" +
                "user=" + user +
                ", product=" + product +
                ", productCount=" + productCount +
                '}';
    }

    @Override
    public boolean isValid() {
        return this.user != null && this.product != null && this.productCount != 0;
    }
}
