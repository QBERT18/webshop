package de.webshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SuccessController extends BaseController {

    @GetMapping("/registrationSuccess")
    public String registrationSuccess() {
        return "success/registrationSuccess";
    }

    @GetMapping("/verificationSuccess")
    public String verificationSuccess() {
        return "success/verificationSuccess";
    }
}
