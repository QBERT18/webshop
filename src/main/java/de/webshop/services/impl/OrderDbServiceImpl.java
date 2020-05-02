package de.webshop.services.impl;

import de.webshop.constants.OrderStatus;
import de.webshop.dataTransferObjects.OrderData;
import de.webshop.db.dataAccessObjects.OrderRepository;
import de.webshop.db.dataAccessObjects.UserRepository;
import de.webshop.entities.Order;
import de.webshop.entities.Product;
import de.webshop.entities.User;
import de.webshop.entities.relations.OrderProducts;
import de.webshop.services.OrderDbService;
import de.webshop.services.exceptions.OrderDbServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
            orderRepository.save(Order.from(orderData.getUser(), OrderStatus.OPEN));
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
    public List<Order> getOrdersByOrderStatus(OrderStatus status) throws OrderDbServiceException {
        if (status == null) {
            throw new OrderDbServiceException("OrderStatus was null");
        } else {
            return orderRepository.getOrdersByStatus(status);
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

    @Override
    public List<Order> getOrderByUserId(long userId) throws OrderDbServiceException {
        if (userId <= 0) {
            throw new OrderDbServiceException("Illegal orderId" + userId);
        } else {
            Optional<User> user = userRepository.findById(userId);
            if (user.isPresent()) {
                return new ArrayList<>(user.get().getOrders());
            } else {
                throw new OrderDbServiceException("User not present" + userId);
            }
        }
    }

    @Override
    public void addProductToOrder(final Order order, final Product product, final int productCount) throws OrderDbServiceException {
        if (order == null || product == null || productCount <= 0) {
            throw new OrderDbServiceException("OrderId <= 0 or product was null");
        } else {
            final OrderProducts newOrderProducts = new OrderProducts(order, product, productCount);
            final List<OrderProducts> orderProducts = order.getOrderProducts();
            if (orderProducts != null) {
                orderProducts.add(newOrderProducts);
            } else {
                final List<OrderProducts> newOrderProductsList = new ArrayList<>();
                newOrderProductsList.add(newOrderProducts);
                order.setOrderProducts(newOrderProductsList);
            }
            orderRepository.save(order);
        }
    }
}
