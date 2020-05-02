package de.webshop.db.dataAccessObjects;

import de.webshop.entities.relations.OrderProducts;
import de.webshop.entities.relations.OrderProductsCompositeKey;
import org.springframework.data.repository.CrudRepository;

public interface OrderProductsRepository extends CrudRepository<OrderProducts, OrderProductsCompositeKey> {

}
