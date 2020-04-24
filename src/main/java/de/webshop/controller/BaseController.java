package de.webshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController {

    @GetMapping("/")
    public String redirectSlashToHome() {
        return "redirect:home";
    }
    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
