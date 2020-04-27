package de.webshop.controller;

import org.springframework.stereotype.Controller;

@Controller
public abstract class BaseController {

    public String redirect(String route) {
        return "redirect:/" + route;
    }
}
