package de.webshop.model;

import java.net.URL;
import java.util.Set;

/**
 * Implementation of {@link Product}.
 */
public class ProductImpl implements Product {

    private final String productName;
    private final double price; // in EUR
    private final int stock;
    private final Set<URL> imageUrls;
    private final String description;

    /**
     * @param productName must be non-null
     * @param price       >= 0
     * @param stock       >= 0
     * @param imageUrls   must be non-null
     * @param description may be null
     * @throws IllegalArgumentException if price or stock < 0 or if productName or imageUrls is null
     */
    public ProductImpl(final String productName, final double price, final int stock, final Set<URL> imageUrls, final String description) {
        if (productName == null || price < 0 || stock < 0 || imageUrls == null) {
            throw new IllegalArgumentException("productName was null or price < 0 or stock < 0 or imageUrls was null");
        } else {
            this.productName = productName;
            this.price = price;
            this.stock = stock;
            this.imageUrls = imageUrls;
            this.description = description == null ? "" : description;
        }
    }

    @Override
    public String getProductName() {
        return productName;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public int getStock() {
        return stock;
    }

    @Override
    public Set<URL> getImageUrls() {
        return imageUrls;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
