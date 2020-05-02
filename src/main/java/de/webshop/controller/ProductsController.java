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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.text.SimpleDateFormat;
import java.util.*;

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
        List<ProductCategory> productCategories = Arrays.asList(ProductCategory.values());
        List<Product> products = new ArrayList<>();
        for(String category : categories) {
            for(Product product : productDbService.getProductByCategory(ProductCategory.valueOf(category))) {
                products.add(product);
            }
        }
        model.addAttribute("products", products);
        model.addAttribute("productCategories", productCategories);
        return "products/products";
    }

    @GetMapping("/products/product-detail")
    public String productDetail(Model model, @RequestParam(value = "id") Long id) throws ProductDbServiceException, UserDbServiceException, OrderDbServiceException {
        String email = securityService.findLoggedInUsername();
        Optional<User> user = userDbService.getUserByEmail(email);
        model.addAttribute("product", productDbService.getProductById(id));
        model.addAttribute("user", securityService.findLoggedInUsername());
        if(user.isPresent())
            model.addAttribute("order", orderDbService.getOrderByUserId(user.get().getUserId()));
        return "products/productDetails/productDetails";
    }

    @PostMapping("/products/addToCart")
    public String addProductToCart(Model model, @ModelAttribute("orderData") OrderData orderData, BindingResult bindingResultOrderData) throws OrderDbServiceException {
        long userId = orderData.getUser().getUserId();
        List<Order> orders = orderDbService.getOrderByUserId(userId);
        if(orders.stream().anyMatch(order -> order.getStatus().equals(OrderStatus.OPEN))) {
            for(Order order : orders) {
                if(order.getStatus().equals(OrderStatus.OPEN)) {
                }
            }
        } else {
            model.addAttribute("message", "Dieses Produkt existiert schon in deinem Cart");
        }
        return "products/productDetails/productDetails";
    }

    public String getDeliveryDate(Calendar calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        return simpleDateFormat.format(calendar.getTime());
    }

}
