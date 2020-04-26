package de.webshop.services.impl;

import de.webshop.constants.OrderStatus;
import de.webshop.db.dataAccessObjects.OrderRepository;
import de.webshop.entities.Order;
import de.webshop.entities.relations.OrderProducts;
import de.webshop.services.OrderDbService;
import de.webshop.services.exceptions.OrderDbServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service("orderDbService")
public class OrderDbServiceImpl implements OrderDbService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderDbServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void addOrder(Order order) throws OrderDbServiceException {
        if (order == null) {
            throw new OrderDbServiceException("Order was null");
        } else {
            orderRepository.save(order);
        }
    }

    @Override
    public Optional<Order> getOrderById(long orderId) throws OrderDbServiceException {
        if (orderId <= 0) {
            throw new OrderDbServiceException("Illegal orderId" + orderId);
        } else {
            return orderRepository.findById(orderId);
        }
    }

    @Override
    public List<Order> getOrdersByOrderStatus(OrderStatus orderStatus) throws OrderDbServiceException {
        if (orderStatus == null) {
            throw new OrderDbServiceException("OrderStatus was null");
        } else {
            return orderRepository.getOrdersByOrderStatus(orderStatus);
        }
    }

    @Override
    public List<OrderProducts> getProductsByOrderId(long orderId) throws OrderDbServiceException {
        if (orderId <= 0) {
            throw new OrderDbServiceException("Illegal orderId" + orderId);
        } else {
            return orderRepository.findById(orderId).map(Order::getOrderProducts).orElseGet(Collections::emptyList);
        }
    }
}
