package de.webshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController extends BaseController {

    @GetMapping("/login")
    public static String getLoginPage() {
        return "login/login";
    }
}
