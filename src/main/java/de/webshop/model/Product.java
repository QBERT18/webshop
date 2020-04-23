package de.webshop.model;

import java.net.URL;
import java.util.Set;

public interface Product {

    /**
     * @return the name of the product
     */
    String getProductName();

    /**
     * @return the price of the product
     */
    double getPrice();

    /**
     * @return how many of this product are in stock
     */
    int getStock();

    /**
     * @return where the product image can be found
     */
    Set<URL> getImageUrls();

    /**
     * @return the product description
     */
    String getDescription();
}
