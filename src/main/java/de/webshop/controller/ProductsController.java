package de.webshop.controller;

import de.webshop.constants.ProductCategory;
import de.webshop.entities.Product;
import de.webshop.services.ProductDbService;
import de.webshop.services.exceptions.ProductDbServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class ProductsController {

    private final ProductDbService productDbService;

    @Autowired
    public ProductsController(ProductDbService productDbService) {
        this.productDbService = productDbService;
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

}
