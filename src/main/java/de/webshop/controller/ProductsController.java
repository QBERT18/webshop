package de.webshop.controller;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
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
    public String filterProducts(Model model, @RequestParam(value = "category", required = false) List<String> categories) throws ProductDbServiceException {
        final List<ProductCategory> productCategories = Arrays.asList(ProductCategory.values());
        final Iterable<Product> products;
        if (categories == null) {
            // return product page without filtering
            products = productDbService.getAllProducts();
        } else {
            products = categories.stream().map(ProductCategory::valueOf).flatMap(this::getProductByCategory).collect(Collectors.toList());
        }
        model.addAttribute("products", products);
        model.addAttribute("productCategories", productCategories);
        return "products/products";
    }

    private Stream<? extends Product> getProductByCategory(ProductCategory productCategory) {
        try {
            return productDbService.getProductByCategory(productCategory).stream();
        } catch (ProductDbServiceException e) {
            logger.error("productDbService access failed", e);
            return Stream.empty();
        }
    }

    @GetMapping("/products/product-detail")
    public String productDetail(Model model, @RequestParam(value = "id") Long id) throws ProductDbServiceException, UserDbServiceException {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String email = authentication.getName();
        final Optional<User> optionalUser = userDbService.getUserByEmail(email);
        final Optional<Product> optionalProduct = productDbService.getProductById(id);
        if (optionalUser.isPresent() && optionalProduct.isPresent()) {
            model.addAttribute("product", optionalProduct.get());
            model.addAttribute("user", optionalUser.get());
            model.addAttribute("orderData", new OrderData(optionalUser.get(), optionalProduct.get(), 1));
            return "products/productDetails/productDetails";
        } else {
            final String product = "Product" + optionalProduct.map(value -> " is present:" + value.toString()).orElse(" is absent.");
            final String user = "User" + optionalUser.map(value -> " is present:" + value.toString()).orElse(" is absent.");
            throw new ProductDbServiceException("Could not get all required data from db: " + product + ";" + user);
        }
    }

    @PostMapping("/products/addToCart")
    public String addProductToCart(Model model, @ModelAttribute("orderData") OrderData orderData, BindingResult bindingResultOrderData) throws OrderDbServiceException {
        if (bindingResultOrderData.hasErrors()) {
            throw new OrderDbServiceException("Error in order data binding");
        } else {
            final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            final Optional<Order> openOrder = orderDbService.getOpenOrderByUserEmail(authentication.getName());
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
            return redirect("/cart");
        }
    }
}
