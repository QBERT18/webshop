package de.webshop.services;

import de.webshop.constants.OrderStatus;
import de.webshop.entities.Order;
import de.webshop.entities.Product;
import de.webshop.services.exceptions.OrderDbServiceException;

import java.util.List;
import java.util.Optional;

public interface OrderDbService {

    void addOrder(Order order) throws OrderDbServiceException;

    Optional<Order> getOrderById(long orderId) throws OrderDbServiceException;

    List<Order> getOrdersByOrderStatus(OrderStatus orderStatus) throws OrderDbServiceException;

    List<Product> getProductsByOrderId(long orderId) throws OrderDbServiceException;
}
