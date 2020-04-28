package de.webshop.controller;

import de.webshop.db.dataAccessObjects.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController extends BaseController {
    private final String TEMPLATE_LOGIN = "login/login";
    private final String ROUTE_LOGIN = "/login";


    private final UserRepository userRepository;

    @Autowired
    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(ROUTE_LOGIN)
    public String loginPage() {
        return TEMPLATE_LOGIN;
    }

}
