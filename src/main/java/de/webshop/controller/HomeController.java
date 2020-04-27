package de.webshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController extends BaseController {

    @GetMapping("/home")
    public String homepage() {
        return "home";
    }
    @GetMapping("/")
    public String redirectHome() {
        return redirect("home");
    }
}
