package de.webshop.services.impl;

import de.webshop.constants.OrderStatus;
import de.webshop.dataTransferObjects.OrderData;
import de.webshop.db.dataAccessObjects.OrderProductsRepository;
import de.webshop.db.dataAccessObjects.OrderRepository;
import de.webshop.entities.Order;
import de.webshop.entities.Product;
import de.webshop.entities.User;
import de.webshop.entities.relations.OrderProducts;
import de.webshop.entities.relations.OrderProductsCompositeKey;
import de.webshop.services.OrderDbService;
import de.webshop.services.UserDbService;
import de.webshop.services.exceptions.OrderDbServiceException;
import de.webshop.services.exceptions.UserDbServiceException;
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
    private final OrderProductsRepository orderProductsRepository;
    private final UserDbService userDbService;

    @Autowired
    public OrderDbServiceImpl(OrderRepository orderRepository, OrderProductsRepository orderProductsRepository, UserDbService userDbService) {
        this.orderRepository = orderRepository;
        this.orderProductsRepository = orderProductsRepository;
        this.userDbService = userDbService;
    }

    @Override
    public void addOrder(OrderData orderData) throws OrderDbServiceException {
        if (orderData == null) {
            throw new OrderDbServiceException("OrderData was null");
        } else {
            // first the order
            final Order order = Order.from(orderData.getUser(), OrderStatus.OPEN);
            orderRepository.save(order);

            // then the product
            final OrderProducts orderProducts = new OrderProducts(order, orderData.getProduct(), orderData.getProductCount());
            orderProducts.setKey(new OrderProductsCompositeKey(order.getOrderId(), orderData.getProduct().getProductId()));
            orderProductsRepository.save(orderProducts);
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
            try {
                final Optional<User> user = userDbService.getUserById(userId);
                if (user.isPresent()) {
                    return orderRepository.getOrdersByUser(user.get());
                } else {
                    throw new OrderDbServiceException("User not present" + userId);
                }
            } catch (UserDbServiceException e) {
                throw new OrderDbServiceException("user with id " + userId + " could not be found", e);
            }
        }
    }

    @Override
    public void addProductToOrder(final Order order, final Product product, final int productCount) throws OrderDbServiceException {
        if (order == null || product == null || productCount <= 0) {
            throw new OrderDbServiceException("OrderId <= 0 or product was null");
        } else {
            // first save order products
            final OrderProducts newOrderProducts = new OrderProducts();
            newOrderProducts.setProductCount(productCount);
            newOrderProducts.setKey(new OrderProductsCompositeKey(order.getOrderId(), product.getProductId()));
            orderProductsRepository.save(newOrderProducts);

            // then update the orders reference to order products
            final List<OrderProducts> orderProducts = order.getOrderProducts();
            if (orderProducts != null) {
                orderProducts.add(newOrderProducts);
                order.setOrderProducts(orderProducts);
            } else {
                // is this else branch ever used or does hibernate ensure a non-null order product list?
                final List<OrderProducts> newOrderProductsList = new ArrayList<>();
                newOrderProductsList.add(newOrderProducts);
                order.setOrderProducts(newOrderProductsList);
            }
            orderRepository.save(order);
        }
    }
}
