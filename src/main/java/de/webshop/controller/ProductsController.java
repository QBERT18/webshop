package de.webshop.controller;

import de.webshop.constants.OrderStatus;
import de.webshop.constants.ProductCategory;
import de.webshop.dataTransferObjects.OrderData;
import de.webshop.entities.Order;
import de.webshop.entities.Product;
import de.webshop.entities.User;
import de.webshop.services.OrderDbService;
import de.webshop.services.ProductDbService;
import de.webshop.services.UserDbService;
import de.webshop.services.exceptions.OrderDbServiceException;
import de.webshop.services.exceptions.ProductDbServiceException;
import de.webshop.services.exceptions.UserDbServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class ProductsController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(ProductsController.class);

    private final ProductDbService productDbService;
    private final OrderDbService orderDbService;
    private final UserDbService userDbService;

    @Autowired
    public ProductsController(ProductDbService productDbService, OrderDbService orderDbService, UserDbService userDbService) {
        this.productDbService = productDbService;
        this.orderDbService = orderDbService;
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
                logger.error("productDbService access failed", e);
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
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String email = authentication.getName();
        final Optional<User> optionalUser = userDbService.getUserByEmail(email);
        final Optional<Product> optionalProduct = productDbService.getProductById(id);
        if (optionalUser.isPresent() && optionalProduct.isPresent()) {
            model.addAttribute("product", optionalProduct.get());
            model.addAttribute("user", optionalUser.get());
            model.addAttribute("orderData", new OrderData(optionalUser.get(), optionalProduct.get(), 1));
        } else {
            final String product = "Product" + optionalProduct.map(value -> " is present:" + value.toString()).orElse(" is absent.");
            final String user = "User" + optionalUser.map(value -> " is present:" + value.toString()).orElse(" is absent.");
            throw new ProductDbServiceException("Could not get all required data from db: " + product + ";" + user);
        }
        return "products/productDetails/productDetails";
    }

    @PostMapping("/products/addToCart")
    public String addProductToCart(Model model, @ModelAttribute("orderData") OrderData orderData, BindingResult bindingResultOrderData) throws OrderDbServiceException {
        final long userId = orderData.getUser().getUserId();
        final List<Order> orders = orderDbService.getOrderByUserId(userId);
        final long nrOfOpenOrders = orders.stream().filter(order -> OrderStatus.OPEN.equals(order.getStatus())).count();
        if (nrOfOpenOrders > 1) {
            throw new OrderDbServiceException("Found more or less open orders than expected for user " + orderData.getUser());
        } else {
            final Optional<Order> openOrder = orders.stream().filter(order -> OrderStatus.OPEN.equals(order.getStatus())).findFirst();
            model.addAttribute("message", userId);
            if (openOrder.isPresent()) {
                // add product to existing order
                final Order order = openOrder.get();
                orderDbService.addProductToOrder(order, orderData.getProduct(), orderData.getProductCount());
                model.addAttribute("message", "Das Produkt wurde zu ihrem Warenkorb hinzugefügt");
            } else {
                // add product to new order
                orderDbService.addOrder(orderData);
                model.addAttribute("message", "Das Produkt wurde zu einer neuen Bestellung hinzugefügt");
            }
        }
        return redirect("/cart");
    }
}
