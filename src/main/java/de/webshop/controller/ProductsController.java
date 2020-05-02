package de.webshop.controller;

import de.webshop.constants.OrderStatus;
import de.webshop.constants.ProductCategory;
import de.webshop.dataTransferObjects.OrderData;
import de.webshop.db.dataAccessObjects.OrderRepository;
import de.webshop.entities.Order;
import de.webshop.entities.Product;
import de.webshop.entities.User;
import de.webshop.services.OrderDbService;
import de.webshop.services.ProductDbService;
import de.webshop.services.SecurityService;
import de.webshop.services.UserDbService;
import de.webshop.services.exceptions.OrderDbServiceException;
import de.webshop.services.exceptions.ProductDbServiceException;
import de.webshop.services.exceptions.UserDbServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class ProductsController {

    private final ProductDbService productDbService;
    private final OrderDbService orderDbService;
    private final OrderRepository orderRepository;
    private final SecurityService securityService;
    private final UserDbService userDbService;

    @Autowired
    public ProductsController(ProductDbService productDbService, OrderDbService orderDbService, OrderRepository orderRepository, SecurityService securityService, UserDbService userDbService) {
        this.productDbService = productDbService;
        this.orderDbService = orderDbService;
        this.orderRepository = orderRepository;
        this.securityService = securityService;
        this.userDbService = userDbService;
    }

    @GetMapping("/products")
    public String products(Model model) {
        List<ProductCategory> productCategories = Arrays.asList(ProductCategory.values());
        model.addAttribute("products", productDbService.getAllProducts());
        model.addAttribute("productCategories", productCategories);
        return "products/products";
    }

    @GetMapping("/products/filter")
    public String filterProducts(Model model, @RequestParam(value = "category") List<String> categories) throws ProductDbServiceException {
        final List<ProductCategory> productCategories = Arrays.asList(ProductCategory.values());
        final List<Product> products = categories.stream().map(ProductCategory::valueOf).flatMap(productCategory -> {
            try {
                return productDbService.getProductByCategory(productCategory).stream();
            } catch (ProductDbServiceException e) {
                e.printStackTrace(); // TODO logger
                return Stream.empty();
            }
        }).collect(Collectors.toList());
        for (String category : categories) {
            products.addAll(productDbService.getProductByCategory(ProductCategory.valueOf(category)));
        }
        model.addAttribute("products", products);
        model.addAttribute("productCategories", productCategories);
        return "products/products";
    }

    @GetMapping("/products/product-detail")
    public String productDetail(Model model, @RequestParam(value = "id") Long id) throws ProductDbServiceException, UserDbServiceException, OrderDbServiceException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<User> user = userDbService.getUserByEmail(email);
        model.addAttribute("product", productDbService.getProductById(id));
        model.addAttribute("user", user.get());
        model.addAttribute("orderData", new OrderData(user.get(), productDbService.getProductById(id).get(), 1));
        if (user.isPresent()) {
            model.addAttribute("order", orderDbService.getOrderByUserId(user.get().getUserId()));
        }
        return "products/productDetails/productDetails";
    }

    @PostMapping("/products/addToCart")
    public String addProductToCart(Model model, @ModelAttribute("orderData") OrderData orderData, BindingResult bindingResultOrderData) throws OrderDbServiceException {
        long userId = orderData.getUser().getUserId();
        List<Order> orders = orderDbService.getOrderByUserId(userId);
        long nrOfOpenOrders = orders.stream().filter(order -> order.getStatus().equals(OrderStatus.OPEN)).count();
        if (nrOfOpenOrders > 1) {
            throw new OrderDbServiceException("Found more or less open orders than expected for user " + orderData.getUser());
        } else {
            final Optional<Order> openOrder = orders.stream().filter(order -> OrderStatus.OPEN.equals(order.getStatus())).findFirst();
            if (openOrder.isPresent()) {
                // add product to existing order
                final Order order = openOrder.get();
                orderDbService.addProductToOrder(order, orderData.getProduct(), orderData.getProductCount());
                model.addAttribute("message", "Das Produkt wurde zu Ihrem Cart hinzugefügt");
            } else {
                // add product to new order
                orderDbService.addOrder(orderData);
                model.addAttribute("message", "Das Produkt wurde zu nicht Ihrem Cart hinzugefügt");
            }
        }
        return "products/productDetails/productDetails";
    }
}
