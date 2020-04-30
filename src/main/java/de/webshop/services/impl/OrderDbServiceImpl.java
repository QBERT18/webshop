package de.webshop.services.impl;

import de.webshop.constants.OrderStatus;
import de.webshop.dataTransferObjects.OrderData;
import de.webshop.db.dataAccessObjects.OrderRepository;
import de.webshop.db.dataAccessObjects.UserRepository;
import de.webshop.entities.Order;
import de.webshop.entities.Product;
import de.webshop.entities.relations.OrderProducts;
import de.webshop.services.OrderDbService;
import de.webshop.services.exceptions.OrderDbServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("orderDbService")
public class OrderDbServiceImpl implements OrderDbService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrderDbServiceImpl(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void addOrder(OrderData orderData) throws OrderDbServiceException {
        if (orderData == null) {
            throw new OrderDbServiceException("OrderData was null");
        } else {
            orderRepository.save(Order.from(orderRepository, orderData.getOrderStatus()));
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
    public List<Product> getProductsByOrderId(long orderId) throws OrderDbServiceException {
        if (orderId <= 0) {
            throw new OrderDbServiceException("Illegal orderId" + orderId);
        } else {
            final List<OrderProducts> orderProductsList = orderRepository.findById(orderId)
                    .map(Order::getOrderProducts).orElseGet(Collections::emptyList);
            return orderProductsList.stream().map(OrderProducts::getProduct).distinct().collect(Collectors.toList());
        }
    }
}
