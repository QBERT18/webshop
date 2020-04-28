package de.webshop.services;

import de.webshop.constants.ProductCategory;
import de.webshop.entities.Product;
import de.webshop.services.exceptions.ProductDbServiceException;

import java.util.List;

public interface ProductDbService {

    Iterable<Product> getAllProducts();
    List<Product> getProductByCategory(ProductCategory productCategory) throws ProductDbServiceException;
}
