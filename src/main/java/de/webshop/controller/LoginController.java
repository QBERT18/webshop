package de.webshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController extends BaseController {

    @GetMapping("/login")
    public static String getLoginPage(Model model) {
        return "login/login";
    }

    @GetMapping("/login/failed")
    public String loginFailed(Model model) {
        model.addAttribute("errorMessage", "Email oder Password ist falsch. Bitte geben sie die richtigen Login Daten ein!");
        return "login/login";
    }

    @GetMapping("/login/success")
    public String loginSuccess(Model model) {
        return redirect("/success");
    }
}
