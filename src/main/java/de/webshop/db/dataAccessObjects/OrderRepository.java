package de.webshop.db.dataAccessObjects;

import de.webshop.constants.OrderStatus;
import de.webshop.entities.Order;
import de.webshop.entities.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {

    List<Order> getOrdersByStatus(OrderStatus status);

    List<Product> getProductsByOrderId(long orderId);
}
