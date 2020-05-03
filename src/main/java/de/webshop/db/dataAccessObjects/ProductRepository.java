package de.webshop.db.dataAccessObjects;

import de.webshop.constants.ProductCategory;
import de.webshop.entities.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> getProductByCategory(ProductCategory category);

}
