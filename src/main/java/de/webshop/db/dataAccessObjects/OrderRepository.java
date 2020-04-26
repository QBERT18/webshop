package de.webshop.db.dataAccessObjects;

import de.webshop.constants.OrderStatus;
import de.webshop.entities.Order;
import de.webshop.entities.relations.OrderProducts;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {

    List<Order> getOrdersByOrderStatus(OrderStatus orderStatus);

    List<OrderProducts> getProductsByOrderId(long orderId);
}
