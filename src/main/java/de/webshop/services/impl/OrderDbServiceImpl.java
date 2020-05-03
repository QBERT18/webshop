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
import java.util.List;
import java.util.Map;
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
    public Map<Product, Integer> getProductsByOrderId(long orderId) throws OrderDbServiceException {
        if (orderId <= 0) {
            throw new OrderDbServiceException("Illegal orderId" + orderId);
        } else {
            final List<OrderProducts> orderProducts = orderProductsRepository.findByOrderId(orderId);
            return orderProducts.stream().collect(Collectors.toMap(OrderProducts::getProduct, OrderProducts::getProductCount));
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

    @Override
    public Optional<Order> getOpenOrderByUserEmail(String userEmail) throws OrderDbServiceException {
        try {
            final Optional<User> userByEmail = userDbService.getUserByEmail(userEmail);
            if (userByEmail.isPresent()) {
                final List<Order> ordersByUser = orderRepository.getOrdersByUser(userByEmail.get());
                final long count = ordersByUser.stream().filter(order -> OrderStatus.OPEN.equals(order.getStatus())).count();
                if (count > 1) {
                    throw new OrderDbServiceException("More than 1 open order for user " + userEmail);
                } else {
                    return ordersByUser.stream().filter(order -> OrderStatus.OPEN.equals(order.getStatus())).findFirst();
                }
            } else {
                throw new OrderDbServiceException("User with this email was not found: " + userEmail);
            }
        } catch (UserDbServiceException e) {
            throw new OrderDbServiceException("User lookup by email failed", e);
        }
    }
}
