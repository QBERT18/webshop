package de.webshop.controller;

import de.webshop.entities.Order;
import de.webshop.entities.Product;
import de.webshop.services.OrderDbService;
import de.webshop.services.ProductDbService;
import de.webshop.services.exceptions.OrderDbServiceException;
import de.webshop.services.exceptions.ProductDbServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Optional;

@Controller
public class CartController {

    private final ProductDbService productDbService;
    private final OrderDbService orderDbService;

    @Autowired
    public CartController(ProductDbService productDbService, OrderDbService orderDbService) {
        this.productDbService = productDbService;
        this.orderDbService = orderDbService;
    }

    @GetMapping("/cart")
    public String cart(Model model) throws OrderDbServiceException {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String email = authentication.getName();
        final Optional<Order> openOrder = orderDbService.getOpenOrderByUserEmail(email);
        if (openOrder.isPresent()) {
            model.addAttribute("order", openOrder.get());
            final Map<Product, Integer> productMap = orderDbService.getProductsByOrderId(openOrder.get().getOrderId());
            model.addAttribute("productMap", productMap);
        } else {
            model.addAttribute("message", "Noch keine Waren im Warenkorb");
        }
        return "cart/cart";
    }
}
