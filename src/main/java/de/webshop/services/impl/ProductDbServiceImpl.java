package de.webshop.services.impl;

import de.webshop.constants.ProductCategory;
import de.webshop.db.dataAccessObjects.ProductRepository;
import de.webshop.entities.Product;
import de.webshop.services.ProductDbService;
import de.webshop.services.exceptions.ProductDbServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("productDbService")
public class ProductDbServiceImpl implements ProductDbService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductDbServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductByCategory(ProductCategory productCategory) throws ProductDbServiceException {
        if (productCategory == null) {
            throw new ProductDbServiceException("OrderStatus was null");
        } else {
            return productRepository.getProductByCategory(productCategory);
        }
    }
}
