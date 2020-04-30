package de.webshop.controller;

import de.webshop.constants.ProductCategory;
import de.webshop.dataTransferObjects.RegistrationData;
import de.webshop.entities.Order;
import de.webshop.entities.Product;
import de.webshop.services.OrderDbService;
import de.webshop.services.ProductDbService;
import de.webshop.services.exceptions.OrderDbServiceException;
import de.webshop.services.exceptions.ProductDbServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class ProductsController {

    private final ProductDbService productDbService;
    private final OrderDbService orderDbService;

    @Autowired
    public ProductsController(ProductDbService productDbService, OrderDbService orderDbService) {
        this.productDbService = productDbService;
        this.orderDbService = orderDbService;
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
    public String productDetail(Model model, @RequestParam(value = "id") Long id) throws ProductDbServiceException {
        model.addAttribute("product", productDbService.getProductById(id));
        return "products/productDetails/productDetails";
    }

    @PostMapping("/products/addToCart")
    public String addProductToCart(Model model, @Valid @ModelAttribute("order") Order order, BindingResult bindingResultOrder) throws OrderDbServiceException {
        orderDbService.addOrder(order);
        return "products/productDetails/productDetails";
    }

}
