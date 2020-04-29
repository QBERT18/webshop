package de.webshop.controller;

import de.webshop.constants.ProductCategory;
import de.webshop.services.ProductDbService;
import de.webshop.services.exceptions.ProductDbServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
public class CartController {

    private final ProductDbService productDbService;

    @Autowired
    public CartController(ProductDbService productDbService) {
        this.productDbService = productDbService;
    }

    @GetMapping("/cart")
    public String cartProduct(Model model, @RequestParam(value = "id") Long id) throws ProductDbServiceException {
        model.addAttribute("product", productDbService.getProductById(id));
        return "cart/cart";
    }
}
